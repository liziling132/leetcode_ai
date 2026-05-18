#!/usr/bin/env python3
"""
抓取 programmercarl 站点索引链接（不抓全文），生成 admin_resources 导入 SQL。

合规策略：
- 仅保存标题、URL、知识点关键词（简单映射）、来源说明。
- 不保存正文内容。

用法：
  python scripts/crawl_programmercarl_index.py
  python scripts/crawl_programmercarl_index.py --max-pages 200 --out docs/sql/seed_programmercarl_index.sql
"""

from __future__ import annotations

import argparse
import datetime as dt
import html
import re
import sys
import urllib.parse
import urllib.request
from collections import deque
from dataclasses import dataclass

BASE = "https://www.programmercarl.com/"
DEFAULT_OUT = "docs/sql/seed_programmercarl_index.sql"

KEYWORD_MAP = {
    "数组": "array",
    "哈希": "hash",
    "双指针": "two-pointers",
    "字符串": "string",
    "栈": "stack",
    "队列": "queue",
    "链表": "linked-list",
    "二叉树": "tree",
    "回溯": "backtracking",
    "贪心": "greedy",
    "动态规划": "dp",
    "图论": "graph",
    "单调栈": "monotonic-stack",
    "滑动窗口": "sliding-window",
    "二分": "binary-search",
}

LINK_RE = re.compile(r"<a\s+[^>]*href=[\"']([^\"']+)[\"'][^>]*>(.*?)</a>", re.IGNORECASE | re.DOTALL)
TITLE_RE = re.compile(r"<title>(.*?)</title>", re.IGNORECASE | re.DOTALL)
TAG_RE = re.compile(r"<[^>]+>")
WS_RE = re.compile(r"\s+")


@dataclass
class Resource:
    title: str
    url: str
    kp_json: str


def fetch(url: str, timeout: int = 12) -> str:
    req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0 (compatible; YititongIndexer/1.0)"})
    with urllib.request.urlopen(req, timeout=timeout) as resp:
        charset = resp.headers.get_content_charset() or "utf-8"
        return resp.read().decode(charset, errors="ignore")


def clean_text(raw: str) -> str:
    no_tag = TAG_RE.sub("", raw)
    unesc = html.unescape(no_tag)
    return WS_RE.sub(" ", unesc).strip()


def to_abs(base: str, href: str) -> str:
    return urllib.parse.urljoin(base, href)


def same_site(url: str) -> bool:
    p = urllib.parse.urlparse(url)
    return p.scheme in ("http", "https") and p.netloc.endswith("programmercarl.com")


def normalize(url: str) -> str:
    p = urllib.parse.urlparse(url)
    path = p.path or "/"
    if path != "/" and path.endswith("/"):
        path = path[:-1]
    return urllib.parse.urlunparse((p.scheme, p.netloc, path, "", "", ""))


def kp_json_from_title(title: str) -> str:
    tags = [v for k, v in KEYWORD_MAP.items() if k in title]
    if not tags:
        tags = ["algorithm"]
    uniq = []
    for t in tags:
        if t not in uniq:
            uniq.append(t)
    arr = ", ".join(f'"{x}"' for x in uniq)
    return f"[{arr}]"


def escape_sql(s: str) -> str:
    return s.replace("\\", "\\\\").replace("'", "''")


def crawl(max_pages: int) -> list[Resource]:
    q = deque([BASE])
    visited: set[str] = set()
    resources: dict[str, Resource] = {}

    while q and len(visited) < max_pages:
        cur = normalize(q.popleft())
        if cur in visited:
            continue
        visited.add(cur)

        try:
            doc = fetch(cur)
        except Exception:
            continue

        title_match = TITLE_RE.search(doc)
        page_title = clean_text(title_match.group(1)) if title_match else ""
        if page_title:
            resources[cur] = Resource(title=page_title, url=cur, kp_json=kp_json_from_title(page_title))

        for href, inner in LINK_RE.findall(doc):
            abs_url = normalize(to_abs(cur, href))
            if not same_site(abs_url):
                continue
            if any(abs_url.endswith(x) for x in (".jpg", ".jpeg", ".png", ".gif", ".svg", ".pdf", ".zip")):
                continue
            if abs_url not in visited and len(visited) + len(q) < max_pages * 3:
                q.append(abs_url)

            anchor_text = clean_text(inner)
            if len(anchor_text) >= 2 and abs_url not in resources:
                resources[abs_url] = Resource(title=anchor_text, url=abs_url, kp_json=kp_json_from_title(anchor_text))

    items = [r for _, r in sorted(resources.items(), key=lambda kv: kv[0])]
    # 过滤太短的标题和无意义入口页
    items = [x for x in items if len(x.title) >= 2 and x.url.startswith(BASE)]
    return items


def build_sql(items: list[Resource]) -> str:
    now = dt.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    lines = [
        "-- 自动生成：programmercarl 索引资源导入 SQL（仅标题+链接，不含正文）",
        f"-- generated_at: {now}",
        "-- 注意：请确认内容使用权限与引用规范",
        "",
        "SET NAMES utf8mb4;",
        "",
    ]

    for r in items:
        title = escape_sql(r.title[:180])
        url = escape_sql(r.url)
        kp = escape_sql(r.kp_json)
        lines.append(
            "INSERT INTO admin_resources(resource_type, title, url, knowledge_points, status, created_by, updated_by, created_at, updated_at) "
            f"VALUES('article', '{title}', '{url}', '{kp}', 1, NULL, NULL, NOW(), NOW()) "
            "ON DUPLICATE KEY UPDATE "
            "title=VALUES(title), knowledge_points=VALUES(knowledge_points), status=VALUES(status), updated_at=NOW();"
        )

    return "\n".join(lines) + "\n"


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--max-pages", type=int, default=120, help="最大抓取页面数")
    parser.add_argument("--out", default=DEFAULT_OUT, help="输出 SQL 文件路径")
    args = parser.parse_args()

    items = crawl(max_pages=max(20, args.max_pages))
    if not items:
        print("[ERROR] 未抓到任何索引数据")
        return 2

    sql = build_sql(items)
    with open(args.out, "w", encoding="utf-8", newline="\n") as f:
        f.write(sql)

    print(f"[OK] 生成完成: {args.out}")
    print(f"[OK] 资源条数: {len(items)}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
