-- reset_db.sql
-- 用途：清空 leetcode_ai 库的业务数据（保留表结构）
-- 执行前请确认当前连接数据库：leetcode_ai

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE submission_case_results;
TRUNCATE TABLE submissions;
TRUNCATE TABLE wrong_questions;
TRUNCATE TABLE recommendation_records;
TRUNCATE TABLE favorites;
TRUNCATE TABLE testcases;
TRUNCATE TABLE problem_tags;
TRUNCATE TABLE tags;
TRUNCATE TABLE problems;
TRUNCATE TABLE user_daily_stats;
TRUNCATE TABLE admin_resources;
TRUNCATE TABLE admins;
TRUNCATE TABLE users;

SET FOREIGN_KEY_CHECKS = 1;

-- 验证（可选）
SELECT 'users' AS table_name, COUNT(*) AS cnt FROM users
UNION ALL SELECT 'admins', COUNT(*) FROM admins
UNION ALL SELECT 'problems', COUNT(*) FROM problems
UNION ALL SELECT 'tags', COUNT(*) FROM tags
UNION ALL SELECT 'problem_tags', COUNT(*) FROM problem_tags
UNION ALL SELECT 'testcases', COUNT(*) FROM testcases
UNION ALL SELECT 'submissions', COUNT(*) FROM submissions
UNION ALL SELECT 'submission_case_results', COUNT(*) FROM submission_case_results
UNION ALL SELECT 'wrong_questions', COUNT(*) FROM wrong_questions
UNION ALL SELECT 'favorites', COUNT(*) FROM favorites
UNION ALL SELECT 'recommendation_records', COUNT(*) FROM recommendation_records
UNION ALL SELECT 'user_daily_stats', COUNT(*) FROM user_daily_stats
UNION ALL SELECT 'admin_resources', COUNT(*) FROM admin_resources;
