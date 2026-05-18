#!/usr/bin/env python3
"""
授权模式：抓取 programmercarl 算法页的标题、正文文本、视频外链并生成入库 SQL。

注意：仅在你已获得内容授权时使用。

用法：
  python scripts/crawl_programmercarl_full_authorized.py \
    --out docs/sql/seed_programmercarl_full.sql --limit 300
"""

from __future__ import annotations

import argparse
import datetime as dt
import html
import json
import re
import sys
import urllib.parse
import urllib.request

BASE = "https://programmercarl.com/"
DEFAULT_OUT = "docs/sql/seed_programmercarl_full.sql"

BLOCKED_PATH_PREFIXES = (
    "/about", "/ke", "/other", "/xunlian", "/qita",
)

KEYWORD_MAP = {
    "数组": "array", "哈希": "hash", "双指针": "two-pointers", "字符串": "string",
    "栈": "stack", "队列": "queue", "链表": "linked-list", "二叉树": "tree",
    "回溯": "backtracking", "贪心": "greedy", "动态规划": "dp", "图": "graph",
    "单调栈": "monotonic-stack", "滑动窗口": "sliding-window", "二分": "binary-search",
}

LOC_RE = re.compile(r"<loc>(.*?)</loc>", re.IGNORECASE | re.DOTALL)
TITLE_RE = re.compile(r"<title>(.*?)</title>", re.IGNORECASE | re.DOTALL)
TAG_RE = re.compile(r"<[^>]+>")
SCRIPT_RE = re.compile(r"<script.*?</script>", re.IGNORECASE | re.DOTALL)
STYLE_RE = re.compile(r"<style.*?</style>", re.IGNORECASE | re.DOTALL)
WS_RE = re.compile(r"\s+")
META_CHARSET_RE = re.compile(r"<meta[^>]+charset=['\"]?([a-zA-Z0-9_-]+)['\"]?", re.IGNORECASE)
A_HREF_RE = re.compile(r"<a\s+[^>]*href=[\"']([^\"']+)[\"'][^>]*>", re.IGNORECASE)
IFRAME_SRC_RE = re.compile(r"<iframe\s+[^>]*src=[\"']([^\"']+)[\"'][^>]*>", re.IGNORECASE)


VIDEO_HOSTS = (
    "bilibili.com", "youtube.com", "youtu.be", "v.qq.com", "youku.com"
)


def fetch(url: str, timeout: int = 15) -> str:
    req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0 (compatible; YititongFullCrawler/1.0)"})
    with urllib.request.urlopen(req, timeout=timeout) as resp:
        raw = resp.read()
        charset = (resp.headers.get_content_charset() or "").strip().lower()
        # 有些页面 header 声明不准，优先按 header 解，再按 meta 和常见编码回退
        for enc in [charset, "utf-8", "utf-8-sig", "gb18030", "gbk"]:
            if not enc:
                continue
            try:
                text = raw.decode(enc, errors="strict")
                if "乱码" not in text:
                    return text
                return text
            except Exception:
                continue
        text = raw.decode("utf-8", errors="ignore")
        m = META_CHARSET_RE.search(text)
        if m:
            enc = m.group(1).lower()
            try:
                return raw.decode(enc, errors="ignore")
            except Exception:
                pass
        return text


def normalize(url: str) -> str:
    p = urllib.parse.urlparse(url)
    scheme = p.scheme or "https"
    netloc = p.netloc
    path = p.path or "/"
    if path != "/" and path.endswith("/"):
        path = path[:-1]
    return urllib.parse.urlunparse((scheme, netloc, path, "", "", ""))


def same_site(url: str) -> bool:
    p = urllib.parse.urlparse(url)
    return p.scheme in ("http", "https") and p.netloc.endswith("programmercarl.com")


def is_algo_page(url: str) -> bool:
    p = urllib.parse.urlparse(url)
    path = p.path or "/"
    if not path.endswith(".html"):
        return False
    for prefix in BLOCKED_PATH_PREFIXES:
        if path.startswith(prefix):
            return False
    return path.count("/") == 1


def clean_text(raw: str) -> str:
    raw = SCRIPT_RE.sub(" ", raw)
    raw = STYLE_RE.sub(" ", raw)
    raw = TAG_RE.sub(" ", raw)
    raw = html.unescape(raw)
    return WS_RE.sub(" ", raw).strip()


def extract_main_html(doc: str) -> str:
    # 优先抓 article，失败再抓 main，最后退化为 body
    for pattern in (
        r"<article[^>]*>(.*?)</article>",
        r"<main[^>]*>(.*?)</main>",
        r"<div[^>]+class=['\"][^'\"]*(?:content|post|article)[^'\"]*['\"][^>]*>(.*?)</div>",
        r"<body[^>]*>(.*?)</body>",
    ):
        m = re.search(pattern, doc, re.IGNORECASE | re.DOTALL)
        if m:
            return m.group(1)
    return doc


def escape_sql(s: str) -> str:
    return s.replace("\\", "\\\\").replace("'", "''")


def kp_json_from_text(text: str) -> str:
    tags = [v for k, v in KEYWORD_MAP.items() if k in text]
    if not tags:
        tags = ["algorithm"]
    uniq = []
    for t in tags:
        if t not in uniq:
            uniq.append(t)
    return json.dumps(uniq, ensure_ascii=False)


def fetch_sitemap_urls() -> list[str]:
    candidates = [
        "https://programmercarl.com/sitemap.xml",
        "https://programmercarl.com/sitemap_index.xml",
        "https://www.programmercarl.com/sitemap.xml",
        "https://www.programmercarl.com/sitemap_index.xml",
    ]
    urls: list[str] = []
    for sm in candidates:
        try:
            xml = fetch(sm)
        except Exception:
            continue
        for loc in LOC_RE.findall(xml):
            u = normalize(html.unescape(loc).strip())
            if same_site(u):
                urls.append(u)

    expanded: list[str] = []
    for u in urls:
        if "sitemap" not in u:
            expanded.append(u)
            continue
        try:
            xml = fetch(u)
        except Exception:
            continue
        subs = [normalize(html.unescape(x).strip()) for x in LOC_RE.findall(xml)]
        if subs:
            expanded.extend(subs)
        else:
            expanded.append(u)

    out = []
    seen = set()
    for u in expanded:
        if u not in seen:
            seen.add(u)
            out.append(u)
    return out


def extract_video_links(page_url: str, doc: str) -> list[str]:
    links = []
    for href in A_HREF_RE.findall(doc):
        abs_u = urllib.parse.urljoin(page_url, href)
        host = urllib.parse.urlparse(abs_u).netloc.lower()
        if any(vh in host for vh in VIDEO_HOSTS):
            links.append(abs_u)
    for src in IFRAME_SRC_RE.findall(doc):
        abs_u = urllib.parse.urljoin(page_url, src)
        host = urllib.parse.urlparse(abs_u).netloc.lower()
        if any(vh in host for vh in VIDEO_HOSTS):
            links.append(abs_u)
    uniq = []
    seen = set()
    for u in links:
        if u not in seen:
            seen.add(u)
            uniq.append(u)
    return uniq


def clip_title(title: str) -> str:
    # 仅保留题目名部分，去掉站点宣传后缀
    parts = [x.strip() for x in title.split("|") if x.strip()]
    return parts[0] if parts else title.strip()


def extract_between(text: str, start_tokens: list[str], end_tokens: list[str]) -> str:
    start = -1
    for token in start_tokens:
        idx = text.find(token)
        if idx != -1:
            start = idx + len(token)
            break
    if start == -1:
        return ""
    end = len(text)
    for token in end_tokens:
        idx = text.find(token, start)
        if idx != -1 and idx < end:
            end = idx
    return text[start:end].strip()


def extract_outline_and_java(text: str) -> tuple[str, str]:
    # 思路区间：# 思路 到 # 总结/# 其他语言版本/# Java
    outline = extract_between(
        text,
        ["# 思路", "思路"],
        ["# 总结", "# 其他语言版本", "# Java", "其他语言版本", "评论", "@2021-"],
    )
    # Java区间：# Java 到下一个语言标题
    java = extract_between(
        text,
        ["# Java", " Java "],
        ["# Python", "# Go", "# JavaScript", "# TypeScript", "# Rust", "# C", "# Swift", "# Scala", "# Ruby", "# C#", "上次更新", "评论", "@2021-"],
    )
    return outline, java


def build_sql(rows: list[dict]) -> str:
    now = dt.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    lines = [
        "-- 自动生成：programmercarl 授权全文资源导入 SQL",
        f"-- generated_at: {now}",
        "-- 注意：仅在你已获得内容授权的前提下使用",
        "SET NAMES utf8mb4;",
        "",
    ]
    for r in rows:
        title = escape_sql(r["title"][:190])
        url = escape_sql(r["url"])
        kp = escape_sql(r["knowledge_points"])
        summary = escape_sql(r["summary_text"])
        content = escape_sql(r["content_text"])
        videos = escape_sql(r["video_links_json"])
        lines.append(
            "INSERT INTO admin_resources(resource_type, title, url, knowledge_points, summary_text, content_text, video_links_json, source_site, copyright_note, status, created_by, updated_by, created_at, updated_at) "
            f"VALUES('ARTICLE', '{title}', '{url}', '{kp}', '{summary}', '{content}', '{videos}', 'programmercarl.com', 'Authorized import by project owner', 1, NULL, NULL, NOW(), NOW()) "
            "ON DUPLICATE KEY UPDATE "
            "title=VALUES(title), knowledge_points=VALUES(knowledge_points), summary_text=VALUES(summary_text), content_text=VALUES(content_text), "
            "video_links_json=VALUES(video_links_json), source_site=VALUES(source_site), copyright_note=VALUES(copyright_note), status=VALUES(status), updated_at=NOW();"
        )
    return "\n".join(lines) + "\n"


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--out", default=DEFAULT_OUT)
    parser.add_argument("--limit", type=int, default=300)
    args = parser.parse_args()

    urls = [u for u in fetch_sitemap_urls() if is_algo_page(u)]
    urls = urls[: max(1, args.limit)]

    rows = []
    for i, u in enumerate(urls, start=1):
        try:
            doc = fetch(u)
        except Exception:
            continue

        m = TITLE_RE.search(doc)
        title = clean_text(m.group(1)) if m else urllib.parse.unquote(urllib.parse.urlparse(u).path.rsplit("/", 1)[-1].replace(".html", ""))
        title = clip_title(title)
        main_html = extract_main_html(doc)
        full_text = clean_text(main_html)
        if not full_text:
            continue
        outline_text, java_text = extract_outline_and_java(full_text)
        summary = (outline_text or full_text[:500])[:4000]
        java_content = java_text[:60000] if java_text else ""
        videos = extract_video_links(u, main_html)

        rows.append({
            "title": title,
            "url": u,
            "knowledge_points": kp_json_from_text(title + " " + summary + " " + java_content),
            "summary_text": summary,
            "content_text": java_content,
            "video_links_json": json.dumps(videos, ensure_ascii=False),
        })

        if i % 20 == 0:
            print(f"[INFO] fetched: {i}/{len(urls)}")

    if not rows:
        print("[ERROR] no rows crawled")
        return 2

    with open(args.out, "w", encoding="utf-8", newline="\n") as f:
        f.write(build_sql(rows))

    print(f"[OK] sql generated: {args.out}")
    print(f"[OK] rows: {len(rows)}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
