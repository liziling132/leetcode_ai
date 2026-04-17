# 易题通 MVP 数据库设计（MySQL 8）

## 1. 设计范围

本设计覆盖 MVP 核心闭环：

1. 浏览题目
2. 在线编写并提交代码
3. 判题并返回结果详情
4. 错题自动归档
5. AI 错题总结
6. 生成推荐记录

## 2. 核心实体

MVP 阶段核心表如下：

- users（用户表）
- admins（管理员表）
- problems（题目表）
- tags（标签表）
- problem_tags（题目标签关联表）
- testcases（测试用例表）
- submissions（提交记录表）
- submission_case_results（提交测试点明细表）
- wrong_questions（错题本表）
- favorites（收藏表）
- recommendation_records（推荐记录表）
- user_daily_stats（用户日统计表）

## 3. 关系说明

- 一个用户对应多条提交记录（users -> submissions）。
- 一个用户对应多条错题记录（users -> wrong_questions）。
- 一道题目对应多条测试用例（problems -> testcases）。
- 一道题目对应多条提交记录（problems -> submissions）。
- 题目与标签是多对多关系，通过 problem_tags 关联。
- 一次提交对应多个测试点结果（submissions -> submission_case_results）。
- 一条推荐记录归属于一个用户，并指向一个推荐资源。

## 4. 关键设计说明

- 密码仅保存为 `password_hash`，不保存明文密码。
- `support_languages`、`knowledge_points` 采用 JSON 字段，便于 MVP 快速交付。
- 判题结果拆两层：
  - 提交层（submissions）：最终状态、总耗时/内存、编译信息
  - 测试点层（submission_case_results）：逐测试点状态与差异信息
- `wrong_questions` 保存最新 AI 总结和累计错误次数，便于错题本快速展示。
- `recommendation_records` 保留推荐理由与触发来源，便于追溯和分析效果。
- `user_daily_stats` 预聚合每日学习数据，降低看板统计查询压力。

## 5. 索引策略（MVP）

- 题目列表/筛选：
  - `problems(status, difficulty, created_at)`
  - `problem_tags(tag_id, problem_id)`
- 提交记录查询：
  - `submissions(user_id, submitted_at)`
  - `submissions(problem_id, submitted_at)`
- 错题本查询：
  - 唯一键 `wrong_questions(user_id, problem_id)`
  - 索引 `wrong_questions(user_id, last_wrong_at)`
- 收藏查询：
  - 唯一键 `favorites(user_id, problem_id)`
- 推荐流查询：
  - 索引 `recommendation_records(user_id, recommended_at)`

## 6. 后续可演进方向

当数据量增长后，可按业务拆库：

- 账号域：users / admins / auth
- 题库域：problems / tags / testcases
- 训练域：submissions / wrong_questions / recommendations

MVP 阶段建议先保持单库，优先保证交付速度与实现复杂度可控。
