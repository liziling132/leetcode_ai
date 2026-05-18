-- seed_30_problems_full_text.sql
-- 用途：将首批30题题面升级为“标准可读版”（非原站拷贝）
SET NAMES utf8mb4;

UPDATE problems SET
  title='两数之和',
  content='给定一个整数数组 nums 和整数 target，请你在数组中找出和为 target 的两个不同下标并返回。\n你可以假设每种输入只会对应一个有效答案，且同一个元素不能重复使用。',
  input_desc='输入：整数数组 nums，整数 target。\n约束：2 <= nums.length <= 10^4，-10^9 <= nums[i], target <= 10^9。\n示例：nums=[2,7,11,15], target=9。',
  output_desc='输出：长度为 2 的下标数组，例如 [0,1]。'
WHERE slug='two-sum';

UPDATE problems SET
  title='合并两个有序数组',
  content='给你两个按非递减顺序排列的整数数组 nums1 和 nums2。请将 nums2 合并到 nums1 中，使合并后的数组仍按非递减顺序排列。\n最终结果应存放在 nums1 内。',
  input_desc='输入：nums1, m, nums2, n。\n其中 nums1 的长度为 m+n，后 n 位为占位 0。\n示例：nums1=[1,2,3,0,0,0], m=3, nums2=[2,5,6], n=3。',
  output_desc='输出：修改后的 nums1，例如 [1,2,2,3,5,6]。'
WHERE slug='merge-sorted-array';

UPDATE problems SET
  title='移除元素',
  content='给你一个数组 nums 和一个值 val，请你原地移除所有数值等于 val 的元素，并返回移除后数组的新长度。\n元素顺序可以改变。你无需考虑数组中超出新长度后面的元素。',
  input_desc='输入：nums, val。\n示例：nums=[3,2,2,3], val=3。',
  output_desc='输出：新长度 k，且 nums 前 k 个元素是不等于 val 的结果元素。'
WHERE slug='remove-element';

UPDATE problems SET
  title='有效的括号',
  content='给定一个只包括括号字符 ()[]{} 的字符串 s，判断字符串是否有效。\n有效规则：左括号必须用相同类型右括号闭合，且顺序正确。',
  input_desc='输入：字符串 s。\n约束：1 <= s.length <= 10^4。\n示例：s="()[]{}"。',
  output_desc='输出：true 或 false。'
WHERE slug='valid-parentheses';

UPDATE problems SET
  title='最长公共前缀',
  content='编写一个函数来查找字符串数组中的最长公共前缀。\n如果不存在公共前缀，返回空字符串。',
  input_desc='输入：字符串数组 strs。\n示例：strs=["flower","flow","flight"]。',
  output_desc='输出：最长公共前缀，例如 "fl"。'
WHERE slug='longest-common-prefix';

UPDATE problems SET
  title='验证回文串',
  content='给定一个字符串 s，验证它是否是回文串。\n只考虑字母和数字字符，并忽略大小写。',
  input_desc='输入：字符串 s。\n示例：s="A man, a plan, a canal: Panama"。',
  output_desc='输出：true 或 false。'
WHERE slug='valid-palindrome';

UPDATE problems SET
  title='二分查找',
  content='给定一个升序整数数组 nums 和一个整数 target，若 target 存在返回其下标，否则返回 -1。',
  input_desc='输入：nums（有序）、target。\n示例：nums=[-1,0,3,5,9,12], target=9。',
  output_desc='输出：下标或 -1。'
WHERE slug='binary-search';

UPDATE problems SET
  title='搜索插入位置',
  content='给定有序数组 nums 和目标值 target，若目标存在返回下标；否则返回按顺序应插入的位置。',
  input_desc='输入：nums（升序）、target。\n示例：nums=[1,3,5,6], target=2。',
  output_desc='输出：插入位置下标，例如 1。'
WHERE slug='search-insert-position';

UPDATE problems SET
  title='爬楼梯',
  content='假设你正在爬楼梯。需要 n 阶你才能到达楼顶。每次可以爬 1 或 2 个台阶，求共有多少种不同方法。',
  input_desc='输入：整数 n。\n约束：1 <= n <= 45。',
  output_desc='输出：方案总数。'
WHERE slug='climbing-stairs';

UPDATE problems SET
  title='买卖股票的最佳时机',
  content='给定价格数组 prices，最多只允许完成一笔交易（先买后卖），计算你能获得的最大利润。\n若无法获利，返回 0。',
  input_desc='输入：prices。\n示例：prices=[7,1,5,3,6,4]。',
  output_desc='输出：最大利润，例如 5。'
WHERE slug='best-time-buy-sell-stock';

UPDATE problems SET
  title='长度最小的子数组',
  content='给定含有 n 个正整数的数组和一个正整数 target，找出总和大于等于 target 的长度最小连续子数组并返回长度。\n如果不存在，返回 0。',
  input_desc='输入：target, nums。\n示例：target=7, nums=[2,3,1,2,4,3]。',
  output_desc='输出：最小长度，例如 2。'
WHERE slug='min-size-subarray-sum';

UPDATE problems SET
  title='无重复字符的最长子串',
  content='给定一个字符串 s，请找出其中不含重复字符的最长子串长度。',
  input_desc='输入：字符串 s。\n示例：s="abcabcbb"。',
  output_desc='输出：最长长度，例如 3。'
WHERE slug='longest-substring-no-repeat';

UPDATE problems SET
  title='三数之和',
  content='给你一个整数数组 nums，判断是否存在三个元素 a,b,c 使得 a+b+c=0。\n请返回所有和为 0 且不重复的三元组。',
  input_desc='输入：nums。\n示例：nums=[-1,0,1,2,-1,-4]。',
  output_desc='输出：三元组列表，例如 [[-1,-1,2],[-1,0,1]]。'
WHERE slug='3sum';

UPDATE problems SET
  title='旋转数组',
  content='给定整数数组 nums，将数组中的元素向右轮转 k 个位置。',
  input_desc='输入：nums, k。\n示例：nums=[1,2,3,4,5,6,7], k=3。',
  output_desc='输出：旋转后的数组，例如 [5,6,7,1,2,3,4]。'
WHERE slug='rotate-array';

UPDATE problems SET
  title='螺旋矩阵',
  content='给你一个 m x n 矩阵 matrix，请按顺时针螺旋顺序返回矩阵中的所有元素。',
  input_desc='输入：二维矩阵 matrix。',
  output_desc='输出：螺旋遍历序列。'
WHERE slug='spiral-matrix';

UPDATE problems SET
  title='矩阵置零',
  content='给定一个 m x n 矩阵，若某个元素为 0，则将其所在行和列的所有元素都设为 0。\n请使用原地算法。',
  input_desc='输入：二维矩阵 matrix。',
  output_desc='输出：原地修改后的矩阵。'
WHERE slug='set-matrix-zeroes';

UPDATE problems SET
  title='岛屿数量',
  content='给你一个由 1（陆地）和 0（水）组成的二维网格，计算网格中岛屿的数量。\n岛屿只能由水平或垂直方向相邻的陆地连接而成。',
  input_desc='输入：字符矩阵 grid。',
  output_desc='输出：岛屿数量。'
WHERE slug='number-of-islands';

UPDATE problems SET
  title='最小路径和',
  content='给定一个包含非负整数的 m x n 网格 grid，请找出一条从左上角到右下角的路径，使路径上的数字总和最小。\n每次只能向下或向右移动一步。',
  input_desc='输入：grid。',
  output_desc='输出：路径最小和。'
WHERE slug='minimum-path-sum';

UPDATE problems SET
  title='打家劫舍',
  content='你是一个专业小偷，计划偷窃沿街房屋。相邻房屋有联动报警系统，不能在同一晚偷相邻两家。\n给定每间房屋金额，返回不触发警报下的最大金额。',
  input_desc='输入：nums，表示每间房屋金额。',
  output_desc='输出：可偷窃最大金额。'
WHERE slug='house-robber';

UPDATE problems SET
  title='最长递增子序列',
  content='给你一个整数数组 nums，找到其中最长严格递增子序列的长度。\n子序列不要求连续。',
  input_desc='输入：nums。',
  output_desc='输出：LIS 长度。'
WHERE slug='longest-increasing-subsequence';

UPDATE problems SET
  title='课程表',
  content='你需要完成 numCourses 门课程，部分课程有先修关系 prerequisites。\n判断是否可能完成所有课程。',
  input_desc='输入：numCourses, prerequisites。\nprerequisites[i]=[a,b] 表示学 a 前要先学 b。',
  output_desc='输出：true 或 false。'
WHERE slug='course-schedule';

UPDATE problems SET
  title='LRU 缓存',
  content='实现 LRUCache 类：\n- LRUCache(capacity) 初始化缓存容量\n- get(key) 返回 key 对应值，不存在返回 -1\n- put(key,value) 插入或更新键值，当容量超限时淘汰最近最少使用项\n要求 get 和 put 的平均时间复杂度为 O(1)。',
  input_desc='输入：一系列 LRU 操作及参数。',
  output_desc='输出：各次 get 操作返回值。'
WHERE slug='lru-cache';

UPDATE problems SET
  title='合并区间',
  content='以数组 intervals 表示若干区间，请合并所有重叠区间，返回不重叠区间数组。',
  input_desc='输入：intervals，如 [[1,3],[2,6],[8,10],[15,18]]。',
  output_desc='输出：合并后的区间集合。'
WHERE slug='merge-intervals';

UPDATE problems SET
  title='括号生成',
  content='数字 n 代表生成括号的对数，请设计函数用于生成所有可能且有效的括号组合。',
  input_desc='输入：整数 n。',
  output_desc='输出：所有有效括号组合字符串数组。'
WHERE slug='generate-parentheses';

UPDATE problems SET
  title='电话号码的字母组合',
  content='给定一个仅包含数字 2-9 的字符串，返回它能表示的所有字母组合。\n映射与电话按键一致，不包含数字 1。',
  input_desc='输入：digits。\n示例："23"。',
  output_desc='输出：字母组合列表，例如 ["ad","ae","af","bd","be","bf","cd","ce","cf"]。'
WHERE slug='letter-combinations-phone-number';

UPDATE problems SET
  title='接雨水',
  content='给定 n 个非负整数表示每个宽度为 1 的柱子高度图，计算下雨后能接多少雨水。',
  input_desc='输入：height 数组。',
  output_desc='输出：可接雨水总量。'
WHERE slug='trapping-rain-water';

UPDATE problems SET
  title='最小覆盖子串',
  content='给你两个字符串 s 和 t，返回 s 中涵盖 t 所有字符的最小子串。\n若不存在，返回空字符串。',
  input_desc='输入：s, t。\n示例：s="ADOBECODEBANC", t="ABC"。',
  output_desc='输出：最小覆盖子串，例如 "BANC"。'
WHERE slug='minimum-window-substring';

UPDATE problems SET
  title='编辑距离',
  content='给你两个单词 word1 和 word2，请返回将 word1 转换成 word2 所使用的最少操作数。\n可进行操作：插入、删除、替换一个字符。',
  input_desc='输入：word1, word2。',
  output_desc='输出：最少操作数。'
WHERE slug='edit-distance';

UPDATE problems SET
  title='单词拆分 II',
  content='给定字符串 s 和字符串字典 wordDict，在 s 中增加空格构成一个句子，使句子中每个单词都在字典中。\n返回所有可能句子，顺序不限。',
  input_desc='输入：s, wordDict。',
  output_desc='输出：所有可行句子字符串数组。'
WHERE slug='word-break-ii';

UPDATE problems SET
  title='合并 K 个升序链表',
  content='给你一个链表数组，每个链表都已经按升序排列。\n请将所有链表合并到一个升序链表中并返回。',
  input_desc='输入：链表数组 lists。',
  output_desc='输出：合并后的升序链表。'
WHERE slug='merge-k-sorted-lists';
