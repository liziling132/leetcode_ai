-- seed_30_problems_real_testcases.sql
-- 作用：为首批30题替换为真实样例测试点

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 清理这30题旧测试点
DELETE tc FROM testcases tc
JOIN problems p ON p.id = tc.problem_id
WHERE p.slug IN (
'two-sum','merge-sorted-array','remove-element','valid-parentheses','longest-common-prefix',
'valid-palindrome','binary-search','search-insert-position','climbing-stairs','best-time-buy-sell-stock',
'min-size-subarray-sum','longest-substring-no-repeat','3sum','rotate-array','spiral-matrix',
'set-matrix-zeroes','number-of-islands','minimum-path-sum','house-robber','longest-increasing-subsequence',
'course-schedule','lru-cache','merge-intervals','generate-parentheses','letter-combinations-phone-number',
'trapping-rain-water','minimum-window-substring','edit-distance','word-break-ii','merge-k-sorted-lists'
);

-- 插入真实测试点（每题 3 条）
INSERT INTO testcases(problem_id, case_type, input_data, output_data, score, sort_order, is_active)
SELECT p.id, x.case_type, x.input_data, x.output_data, 1, x.sort_order, 1
FROM (
  SELECT 'two-sum' slug, 'SAMPLE' case_type, 'nums=[2,7,11,15]\ntarget=9' input_data, '[0,1]' output_data, 1 sort_order UNION ALL
  SELECT 'two-sum','SAMPLE','nums=[3,2,4]\ntarget=6','[1,2]',2 UNION ALL
  SELECT 'two-sum','HIDDEN','nums=[3,3]\ntarget=6','[0,1]',3 UNION ALL

  SELECT 'merge-sorted-array','SAMPLE','nums1=[1,2,3,0,0,0],m=3\nnums2=[2,5,6],n=3','[1,2,2,3,5,6]',1 UNION ALL
  SELECT 'merge-sorted-array','SAMPLE','nums1=[1],m=1\nnums2=[],n=0','[1]',2 UNION ALL
  SELECT 'merge-sorted-array','HIDDEN','nums1=[0],m=0\nnums2=[1],n=1','[1]',3 UNION ALL

  SELECT 'remove-element','SAMPLE','nums=[3,2,2,3],val=3','2\nnums=[2,2]',1 UNION ALL
  SELECT 'remove-element','SAMPLE','nums=[0,1,2,2,3,0,4,2],val=2','5\nnums=[0,1,4,0,3]',2 UNION ALL
  SELECT 'remove-element','HIDDEN','nums=[1],val=1','0\nnums=[]',3 UNION ALL

  SELECT 'valid-parentheses','SAMPLE','s=()','true',1 UNION ALL
  SELECT 'valid-parentheses','SAMPLE','s=()[]{}','true',2 UNION ALL
  SELECT 'valid-parentheses','HIDDEN','s=(]','false',3 UNION ALL

  SELECT 'longest-common-prefix','SAMPLE','strs=[flower,flow,flight]','fl',1 UNION ALL
  SELECT 'longest-common-prefix','SAMPLE','strs=[dog,racecar,car]','',2 UNION ALL
  SELECT 'longest-common-prefix','HIDDEN','strs=[interspace,interstellar,interstate]','inters',3 UNION ALL

  SELECT 'valid-palindrome','SAMPLE','s=A man, a plan, a canal: Panama','true',1 UNION ALL
  SELECT 'valid-palindrome','SAMPLE','s=race a car','false',2 UNION ALL
  SELECT 'valid-palindrome','HIDDEN','s= ','true',3 UNION ALL

  SELECT 'binary-search','SAMPLE','nums=[-1,0,3,5,9,12]\ntarget=9','4',1 UNION ALL
  SELECT 'binary-search','SAMPLE','nums=[-1,0,3,5,9,12]\ntarget=2','-1',2 UNION ALL
  SELECT 'binary-search','HIDDEN','nums=[5]\ntarget=5','0',3 UNION ALL

  SELECT 'search-insert-position','SAMPLE','nums=[1,3,5,6]\ntarget=5','2',1 UNION ALL
  SELECT 'search-insert-position','SAMPLE','nums=[1,3,5,6]\ntarget=2','1',2 UNION ALL
  SELECT 'search-insert-position','HIDDEN','nums=[1,3,5,6]\ntarget=7','4',3 UNION ALL

  SELECT 'climbing-stairs','SAMPLE','n=2','2',1 UNION ALL
  SELECT 'climbing-stairs','SAMPLE','n=3','3',2 UNION ALL
  SELECT 'climbing-stairs','HIDDEN','n=5','8',3 UNION ALL

  SELECT 'best-time-buy-sell-stock','SAMPLE','prices=[7,1,5,3,6,4]','5',1 UNION ALL
  SELECT 'best-time-buy-sell-stock','SAMPLE','prices=[7,6,4,3,1]','0',2 UNION ALL
  SELECT 'best-time-buy-sell-stock','HIDDEN','prices=[2,4,1]','2',3 UNION ALL

  SELECT 'min-size-subarray-sum','SAMPLE','target=7\nnums=[2,3,1,2,4,3]','2',1 UNION ALL
  SELECT 'min-size-subarray-sum','SAMPLE','target=4\nnums=[1,4,4]','1',2 UNION ALL
  SELECT 'min-size-subarray-sum','HIDDEN','target=11\nnums=[1,1,1,1,1,1,1,1]','0',3 UNION ALL

  SELECT 'longest-substring-no-repeat','SAMPLE','s=abcabcbb','3',1 UNION ALL
  SELECT 'longest-substring-no-repeat','SAMPLE','s=bbbbb','1',2 UNION ALL
  SELECT 'longest-substring-no-repeat','HIDDEN','s=pwwkew','3',3 UNION ALL

  SELECT '3sum','SAMPLE','nums=[-1,0,1,2,-1,-4]','[[-1,-1,2],[-1,0,1]]',1 UNION ALL
  SELECT '3sum','SAMPLE','nums=[]','[]',2 UNION ALL
  SELECT '3sum','HIDDEN','nums=[0,0,0]','[[0,0,0]]',3 UNION ALL

  SELECT 'rotate-array','SAMPLE','nums=[1,2,3,4,5,6,7]\nk=3','[5,6,7,1,2,3,4]',1 UNION ALL
  SELECT 'rotate-array','SAMPLE','nums=[-1,-100,3,99]\nk=2','[3,99,-1,-100]',2 UNION ALL
  SELECT 'rotate-array','HIDDEN','nums=[1,2]\nk=3','[2,1]',3 UNION ALL

  SELECT 'spiral-matrix','SAMPLE','matrix=[[1,2,3],[4,5,6],[7,8,9]]','[1,2,3,6,9,8,7,4,5]',1 UNION ALL
  SELECT 'spiral-matrix','SAMPLE','matrix=[[1,2,3,4],[5,6,7,8],[9,10,11,12]]','[1,2,3,4,8,12,11,10,9,5,6,7]',2 UNION ALL
  SELECT 'spiral-matrix','HIDDEN','matrix=[[1]]','[1]',3 UNION ALL

  SELECT 'set-matrix-zeroes','SAMPLE','matrix=[[1,1,1],[1,0,1],[1,1,1]]','[[1,0,1],[0,0,0],[1,0,1]]',1 UNION ALL
  SELECT 'set-matrix-zeroes','SAMPLE','matrix=[[0,1,2,0],[3,4,5,2],[1,3,1,5]]','[[0,0,0,0],[0,4,5,0],[0,3,1,0]]',2 UNION ALL
  SELECT 'set-matrix-zeroes','HIDDEN','matrix=[[1,0]]','[[0,0]]',3 UNION ALL

  SELECT 'number-of-islands','SAMPLE','grid=[[1,1,1,1,0],[1,1,0,1,0],[1,1,0,0,0],[0,0,0,0,0]]','1',1 UNION ALL
  SELECT 'number-of-islands','SAMPLE','grid=[[1,1,0,0,0],[1,1,0,0,0],[0,0,1,0,0],[0,0,0,1,1]]','3',2 UNION ALL
  SELECT 'number-of-islands','HIDDEN','grid=[[0,0,0],[0,0,0]]','0',3 UNION ALL

  SELECT 'minimum-path-sum','SAMPLE','grid=[[1,3,1],[1,5,1],[4,2,1]]','7',1 UNION ALL
  SELECT 'minimum-path-sum','SAMPLE','grid=[[1,2,3],[4,5,6]]','12',2 UNION ALL
  SELECT 'minimum-path-sum','HIDDEN','grid=[[5]]','5',3 UNION ALL

  SELECT 'house-robber','SAMPLE','nums=[1,2,3,1]','4',1 UNION ALL
  SELECT 'house-robber','SAMPLE','nums=[2,7,9,3,1]','12',2 UNION ALL
  SELECT 'house-robber','HIDDEN','nums=[2,1,1,2]','4',3 UNION ALL

  SELECT 'longest-increasing-subsequence','SAMPLE','nums=[10,9,2,5,3,7,101,18]','4',1 UNION ALL
  SELECT 'longest-increasing-subsequence','SAMPLE','nums=[0,1,0,3,2,3]','4',2 UNION ALL
  SELECT 'longest-increasing-subsequence','HIDDEN','nums=[7,7,7,7,7]','1',3 UNION ALL

  SELECT 'course-schedule','SAMPLE','numCourses=2\nprerequisites=[[1,0]]','true',1 UNION ALL
  SELECT 'course-schedule','SAMPLE','numCourses=2\nprerequisites=[[1,0],[0,1]]','false',2 UNION ALL
  SELECT 'course-schedule','HIDDEN','numCourses=3\nprerequisites=[[1,0],[2,1]]','true',3 UNION ALL

  SELECT 'lru-cache','SAMPLE','ops=[LRUCache(2),put(1,1),put(2,2),get(1),put(3,3),get(2),put(4,4),get(1),get(3),get(4)]','[null,null,null,1,null,-1,null,-1,3,4]',1 UNION ALL
  SELECT 'lru-cache','SAMPLE','ops=[LRUCache(1),put(2,1),get(2)]','[null,null,1]',2 UNION ALL
  SELECT 'lru-cache','HIDDEN','ops=[LRUCache(2),put(2,1),put(2,2),get(2)]','[null,null,null,2]',3 UNION ALL

  SELECT 'merge-intervals','SAMPLE','intervals=[[1,3],[2,6],[8,10],[15,18]]','[[1,6],[8,10],[15,18]]',1 UNION ALL
  SELECT 'merge-intervals','SAMPLE','intervals=[[1,4],[4,5]]','[[1,5]]',2 UNION ALL
  SELECT 'merge-intervals','HIDDEN','intervals=[[1,4],[0,0]]','[[0,0],[1,4]]',3 UNION ALL

  SELECT 'generate-parentheses','SAMPLE','n=3','["((()))","(()())","(())()","()(())","()()()"]',1 UNION ALL
  SELECT 'generate-parentheses','SAMPLE','n=1','["()"]',2 UNION ALL
  SELECT 'generate-parentheses','HIDDEN','n=2','["(())","()()"]',3 UNION ALL

  SELECT 'letter-combinations-phone-number','SAMPLE','digits=23','["ad","ae","af","bd","be","bf","cd","ce","cf"]',1 UNION ALL
  SELECT 'letter-combinations-phone-number','SAMPLE','digits=','[]',2 UNION ALL
  SELECT 'letter-combinations-phone-number','HIDDEN','digits=7','["p","q","r","s"]',3 UNION ALL

  SELECT 'trapping-rain-water','SAMPLE','height=[0,1,0,2,1,0,1,3,2,1,2,1]','6',1 UNION ALL
  SELECT 'trapping-rain-water','SAMPLE','height=[4,2,0,3,2,5]','9',2 UNION ALL
  SELECT 'trapping-rain-water','HIDDEN','height=[1,0,2]','1',3 UNION ALL

  SELECT 'minimum-window-substring','SAMPLE','s=ADOBECODEBANC\nt=ABC','BANC',1 UNION ALL
  SELECT 'minimum-window-substring','SAMPLE','s=a\nt=a','a',2 UNION ALL
  SELECT 'minimum-window-substring','HIDDEN','s=a\nt=aa','',3 UNION ALL

  SELECT 'edit-distance','SAMPLE','word1=horse\nword2=ros','3',1 UNION ALL
  SELECT 'edit-distance','SAMPLE','word1=intention\nword2=execution','5',2 UNION ALL
  SELECT 'edit-distance','HIDDEN','word1=\nword2=abc','3',3 UNION ALL

  SELECT 'word-break-ii','SAMPLE','s=catsanddog\nwordDict=[cat,cats,and,sand,dog]','["cats and dog","cat sand dog"]',1 UNION ALL
  SELECT 'word-break-ii','SAMPLE','s=pineapplepenapple\nwordDict=[apple,pen,applepen,pine,pineapple]','["pine apple pen apple","pineapple pen apple","pine applepen apple"]',2 UNION ALL
  SELECT 'word-break-ii','HIDDEN','s=catsandog\nwordDict=[cats,dog,sand,and,cat]','[]',3 UNION ALL

  SELECT 'merge-k-sorted-lists','SAMPLE','lists=[[1,4,5],[1,3,4],[2,6]]','[1,1,2,3,4,4,5,6]',1 UNION ALL
  SELECT 'merge-k-sorted-lists','SAMPLE','lists=[]','[]',2 UNION ALL
  SELECT 'merge-k-sorted-lists','HIDDEN','lists=[[]]','[]',3
) x
JOIN problems p ON p.slug = x.slug;

SET FOREIGN_KEY_CHECKS = 1;

-- 校验
SELECT p.slug, COUNT(*) AS testcase_count
FROM problems p JOIN testcases t ON p.id=t.problem_id
WHERE p.slug IN (
'two-sum','merge-sorted-array','remove-element','valid-parentheses','longest-common-prefix',
'valid-palindrome','binary-search','search-insert-position','climbing-stairs','best-time-buy-sell-stock',
'min-size-subarray-sum','longest-substring-no-repeat','3sum','rotate-array','spiral-matrix',
'set-matrix-zeroes','number-of-islands','minimum-path-sum','house-robber','longest-increasing-subsequence',
'course-schedule','lru-cache','merge-intervals','generate-parentheses','letter-combinations-phone-number',
'trapping-rain-water','minimum-window-substring','edit-distance','word-break-ii','merge-k-sorted-lists'
)
GROUP BY p.slug
ORDER BY p.slug;
