-- seed_30_problems.sql
-- 目的：初始化 30 道种子题（tags + problems + problem_tags + testcases）
-- 说明：题面为可运行模板，建议后续替换为你的正式题面与更完整测试数据

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 1) 标签
INSERT INTO tags(tag_name, tag_type) VALUES
('array', 'knowledge'),
('hash', 'knowledge'),
('two-pointers', 'knowledge'),
('string', 'knowledge'),
('stack', 'knowledge'),
('binary-search', 'knowledge'),
('dp', 'knowledge'),
('greedy', 'knowledge'),
('sliding-window', 'knowledge'),
('sort', 'knowledge'),
('matrix', 'knowledge'),
('dfs', 'knowledge'),
('bfs', 'knowledge'),
('graph', 'knowledge'),
('topological-sort', 'knowledge'),
('design', 'knowledge'),
('linked-list', 'knowledge'),
('backtracking', 'knowledge'),
('heap', 'knowledge'),
('divide-conquer', 'knowledge')
ON DUPLICATE KEY UPDATE tag_type = VALUES(tag_type);

-- 2) 题目
INSERT INTO problems(
  title, slug, content, input_desc, output_desc,
  difficulty, knowledge_points, support_languages, status, created_by, updated_by
)
VALUES
('两数之和', 'two-sum', '给定整数数组 nums 和目标值 target，返回两个下标。', '第一行 nums，第二行 target。', '两个下标（升序）。', 'EASY', JSON_ARRAY('array','hash'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('合并两个有序数组', 'merge-sorted-array', '将 nums2 合并到 nums1，并保持有序。', '数组与长度参数。', '合并后的 nums1。', 'EASY', JSON_ARRAY('array','two-pointers'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('移除元素', 'remove-element', '原地移除数组中等于 val 的元素。', '数组 nums 与 val。', '新长度。', 'EASY', JSON_ARRAY('array','two-pointers'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('有效括号', 'valid-parentheses', '判断括号字符串是否有效。', '字符串 s。', 'true/false。', 'EASY', JSON_ARRAY('stack','string'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('最长公共前缀', 'longest-common-prefix', '求字符串数组的最长公共前缀。', '字符串数组。', '最长公共前缀。', 'EASY', JSON_ARRAY('string'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('回文字符串判定', 'valid-palindrome', '忽略非字母数字并忽略大小写，判断是否回文。', '字符串 s。', 'true/false。', 'EASY', JSON_ARRAY('two-pointers','string'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('二分查找', 'binary-search', '在有序数组中查找 target 的下标。', '有序数组 nums 与 target。', '下标或 -1。', 'EASY', JSON_ARRAY('binary-search'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('搜索插入位置', 'search-insert-position', '返回 target 应插入位置。', '有序数组 nums 与 target。', '插入下标。', 'EASY', JSON_ARRAY('binary-search'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('爬楼梯', 'climbing-stairs', '每次爬 1 或 2 阶，求到 n 阶方案数。', '整数 n。', '方案数。', 'EASY', JSON_ARRAY('dp'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('股票最大利润 I', 'best-time-buy-sell-stock', '一次买卖求最大利润。', '价格数组 prices。', '最大利润。', 'EASY', JSON_ARRAY('greedy','array'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),

('长度最小子数组', 'min-size-subarray-sum', '和 >= target 的最短连续子数组长度。', 'target 与数组 nums。', '最短长度，不存在返回 0。', 'MEDIUM', JSON_ARRAY('sliding-window','two-pointers'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('无重复字符最长子串', 'longest-substring-no-repeat', '求不含重复字符的最长子串长度。', '字符串 s。', '最大长度。', 'MEDIUM', JSON_ARRAY('sliding-window','hash'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('三数之和', '3sum', '找出和为 0 且不重复的三元组。', '整数数组 nums。', '三元组集合。', 'MEDIUM', JSON_ARRAY('two-pointers','sort'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('旋转数组', 'rotate-array', '将数组向右轮转 k 步。', '数组 nums 与 k。', '轮转后数组。', 'MEDIUM', JSON_ARRAY('array'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('螺旋矩阵', 'spiral-matrix', '按螺旋顺序返回矩阵元素。', '矩阵 matrix。', '螺旋序列。', 'MEDIUM', JSON_ARRAY('matrix'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('矩阵置零', 'set-matrix-zeroes', '若元素为 0，则其所在行列置 0。', '矩阵 matrix。', '原地修改后矩阵。', 'MEDIUM', JSON_ARRAY('matrix'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('岛屿数量', 'number-of-islands', '统计网格中岛屿数量。', '字符网格 grid。', '岛屿个数。', 'MEDIUM', JSON_ARRAY('dfs','bfs'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('最小路径和', 'minimum-path-sum', '从左上到右下最小路径和。', '网格 grid。', '最小和。', 'MEDIUM', JSON_ARRAY('dp','matrix'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('打家劫舍', 'house-robber', '相邻房屋不能同时偷，求最大金额。', '数组 nums。', '最大金额。', 'MEDIUM', JSON_ARRAY('dp'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('最长递增子序列', 'longest-increasing-subsequence', '求 LIS 长度。', '数组 nums。', 'LIS 长度。', 'MEDIUM', JSON_ARRAY('dp','binary-search'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),

('课程表可行性', 'course-schedule', '判断是否能完成所有课程。', '课程数 n 与先修关系。', 'true/false。', 'MEDIUM', JSON_ARRAY('graph','topological-sort'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('LRU 缓存', 'lru-cache', '实现 LRU 缓存结构。', '一系列操作。', '查询结果序列。', 'MEDIUM', JSON_ARRAY('design','hash','linked-list'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('合并区间', 'merge-intervals', '合并重叠区间。', '区间数组 intervals。', '合并结果。', 'MEDIUM', JSON_ARRAY('sort','greedy'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('括号生成', 'generate-parentheses', '生成 n 对括号的所有合法组合。', '整数 n。', '所有组合。', 'MEDIUM', JSON_ARRAY('backtracking'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('电话号码字母组合', 'letter-combinations-phone-number', '返回数字串对应字母组合。', '数字字符串 digits。', '所有组合。', 'MEDIUM', JSON_ARRAY('backtracking'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),

('接雨水', 'trapping-rain-water', '给定柱高数组，求可接雨水总量。', '数组 height。', '总雨水量。', 'HARD', JSON_ARRAY('two-pointers','stack'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('最小覆盖子串', 'minimum-window-substring', '求包含 t 全部字符的最小子串。', '字符串 s 与 t。', '最小子串。', 'HARD', JSON_ARRAY('sliding-window','hash'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('编辑距离', 'edit-distance', '求两个字符串最小编辑距离。', 'word1, word2。', '最小编辑次数。', 'HARD', JSON_ARRAY('dp','string'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('单词拆分 II', 'word-break-ii', '返回所有可行拆分句子。', '字符串 s 与词典。', '所有句子。', 'HARD', JSON_ARRAY('dp','backtracking'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL),
('合并K个有序链表', 'merge-k-sorted-lists', '合并 k 个有序链表。', '链表数组 lists。', '合并后链表。', 'HARD', JSON_ARRAY('heap','divide-conquer','linked-list'), JSON_ARRAY('java','python','cpp'), 1, NULL, NULL)
ON DUPLICATE KEY UPDATE
  title = VALUES(title),
  content = VALUES(content),
  input_desc = VALUES(input_desc),
  output_desc = VALUES(output_desc),
  difficulty = VALUES(difficulty),
  knowledge_points = VALUES(knowledge_points),
  support_languages = VALUES(support_languages),
  status = VALUES(status),
  updated_at = NOW();

-- 3) 题目-标签关系
INSERT IGNORE INTO problem_tags(problem_id, tag_id)
SELECT p.id, t.id
FROM (
  SELECT 'two-sum' slug, 'array' tag_name UNION ALL
  SELECT 'two-sum', 'hash' UNION ALL
  SELECT 'merge-sorted-array', 'array' UNION ALL
  SELECT 'merge-sorted-array', 'two-pointers' UNION ALL
  SELECT 'remove-element', 'array' UNION ALL
  SELECT 'remove-element', 'two-pointers' UNION ALL
  SELECT 'valid-parentheses', 'stack' UNION ALL
  SELECT 'valid-parentheses', 'string' UNION ALL
  SELECT 'longest-common-prefix', 'string' UNION ALL
  SELECT 'valid-palindrome', 'two-pointers' UNION ALL
  SELECT 'valid-palindrome', 'string' UNION ALL
  SELECT 'binary-search', 'binary-search' UNION ALL
  SELECT 'search-insert-position', 'binary-search' UNION ALL
  SELECT 'climbing-stairs', 'dp' UNION ALL
  SELECT 'best-time-buy-sell-stock', 'greedy' UNION ALL
  SELECT 'best-time-buy-sell-stock', 'array' UNION ALL

  SELECT 'min-size-subarray-sum', 'sliding-window' UNION ALL
  SELECT 'min-size-subarray-sum', 'two-pointers' UNION ALL
  SELECT 'longest-substring-no-repeat', 'sliding-window' UNION ALL
  SELECT 'longest-substring-no-repeat', 'hash' UNION ALL
  SELECT '3sum', 'two-pointers' UNION ALL
  SELECT '3sum', 'sort' UNION ALL
  SELECT 'rotate-array', 'array' UNION ALL
  SELECT 'spiral-matrix', 'matrix' UNION ALL
  SELECT 'set-matrix-zeroes', 'matrix' UNION ALL
  SELECT 'number-of-islands', 'dfs' UNION ALL
  SELECT 'number-of-islands', 'bfs' UNION ALL
  SELECT 'minimum-path-sum', 'dp' UNION ALL
  SELECT 'minimum-path-sum', 'matrix' UNION ALL
  SELECT 'house-robber', 'dp' UNION ALL
  SELECT 'longest-increasing-subsequence', 'dp' UNION ALL
  SELECT 'longest-increasing-subsequence', 'binary-search' UNION ALL

  SELECT 'course-schedule', 'graph' UNION ALL
  SELECT 'course-schedule', 'topological-sort' UNION ALL
  SELECT 'lru-cache', 'design' UNION ALL
  SELECT 'lru-cache', 'hash' UNION ALL
  SELECT 'lru-cache', 'linked-list' UNION ALL
  SELECT 'merge-intervals', 'sort' UNION ALL
  SELECT 'merge-intervals', 'greedy' UNION ALL
  SELECT 'generate-parentheses', 'backtracking' UNION ALL
  SELECT 'letter-combinations-phone-number', 'backtracking' UNION ALL

  SELECT 'trapping-rain-water', 'two-pointers' UNION ALL
  SELECT 'trapping-rain-water', 'stack' UNION ALL
  SELECT 'minimum-window-substring', 'sliding-window' UNION ALL
  SELECT 'minimum-window-substring', 'hash' UNION ALL
  SELECT 'edit-distance', 'dp' UNION ALL
  SELECT 'edit-distance', 'string' UNION ALL
  SELECT 'word-break-ii', 'dp' UNION ALL
  SELECT 'word-break-ii', 'backtracking' UNION ALL
  SELECT 'merge-k-sorted-lists', 'heap' UNION ALL
  SELECT 'merge-k-sorted-lists', 'divide-conquer' UNION ALL
  SELECT 'merge-k-sorted-lists', 'linked-list'
) x
JOIN problems p ON p.slug = x.slug
JOIN tags t ON t.tag_name = x.tag_name;

-- 4) 测试用例（每题 2 条，SAMPLE + HIDDEN；后续请扩充边界 case）
INSERT INTO testcases(problem_id, case_type, input_data, output_data, score, sort_order, is_active)
SELECT p.id, 'SAMPLE', 'sample_input_1', 'sample_output_1', 1, 1, 1
FROM problems p
WHERE p.slug IN (
'two-sum','merge-sorted-array','remove-element','valid-parentheses','longest-common-prefix',
'valid-palindrome','binary-search','search-insert-position','climbing-stairs','best-time-buy-sell-stock',
'min-size-subarray-sum','longest-substring-no-repeat','3sum','rotate-array','spiral-matrix',
'set-matrix-zeroes','number-of-islands','minimum-path-sum','house-robber','longest-increasing-subsequence',
'course-schedule','lru-cache','merge-intervals','generate-parentheses','letter-combinations-phone-number',
'trapping-rain-water','minimum-window-substring','edit-distance','word-break-ii','merge-k-sorted-lists'
)
ON DUPLICATE KEY UPDATE updated_at = NOW();

INSERT INTO testcases(problem_id, case_type, input_data, output_data, score, sort_order, is_active)
SELECT p.id, 'HIDDEN', 'hidden_input_1', 'hidden_output_1', 1, 2, 1
FROM problems p
WHERE p.slug IN (
'two-sum','merge-sorted-array','remove-element','valid-parentheses','longest-common-prefix',
'valid-palindrome','binary-search','search-insert-position','climbing-stairs','best-time-buy-sell-stock',
'min-size-subarray-sum','longest-substring-no-repeat','3sum','rotate-array','spiral-matrix',
'set-matrix-zeroes','number-of-islands','minimum-path-sum','house-robber','longest-increasing-subsequence',
'course-schedule','lru-cache','merge-intervals','generate-parentheses','letter-combinations-phone-number',
'trapping-rain-water','minimum-window-substring','edit-distance','word-break-ii','merge-k-sorted-lists'
)
ON DUPLICATE KEY UPDATE updated_at = NOW();

SET FOREIGN_KEY_CHECKS = 1;

-- 5) 验证
SELECT 'problems' AS table_name, COUNT(*) AS cnt FROM problems
UNION ALL SELECT 'tags', COUNT(*) FROM tags
UNION ALL SELECT 'problem_tags', COUNT(*) FROM problem_tags
UNION ALL SELECT 'testcases', COUNT(*) FROM testcases;
