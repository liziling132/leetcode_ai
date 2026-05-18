# 易题通字段口径基线（MVP）

## 1. 目标

统一「数据库字段」与「Java实体字段」与「接口返回字段」的命名口径，避免联调时出现字段误读、SQL误写。

## 2. 全局约定

- 数据库：`snake_case`
- Java 实体/DTO/VO：`camelCase`
- API 响应优先字段：
  - 用户主键对外统一使用 `userId`
  - 兼容字段：`id`（仅用于兼容旧调用，值与 `userId` 相同）

## 3. 核心链路字段映射

### 3.1 用户与认证

| 语义 | 表.字段 | Java字段 | API字段 |
|---|---|---|---|
| 用户ID | `users.id` | `UserEntity.id` | `AuthResponseVo.userId`（兼容：`id`） |
| 用户名 | `users.username` | `UserEntity.username` | `username` |
| 密码哈希 | `users.password_hash` | `UserEntity.passwordHash` | 不出参 |
| 昵称 | `users.nickname` | `UserEntity.nickname` | `nickname` |
| 邮箱 | `users.email` | `UserEntity.email` | 当前未出参 |
| 手机号 | `users.phone` | `UserEntity.phone` | 当前未出参 |
| 头像 | `users.avatar_url` | `UserEntity.avatarUrl` | 当前未出参 |
| 简介 | `users.bio` | `UserEntity.bio` | 当前未出参 |
| 状态 | `users.status` | `UserEntity.status` | 当前未出参 |

### 3.2 题目

| 语义 | 表.字段 | Java字段 | API字段 |
|---|---|---|---|
| 题目ID | `problems.id` | `ProblemEntity.id` | `id` |
| 标题 | `problems.title` | `ProblemEntity.title` | `title` |
| 难度 | `problems.difficulty` | `ProblemEntity.difficulty` | `difficulty` |
| 题面 | `problems.content` | `ProblemEntity.content` | `content` |
| 输入说明 | `problems.input_desc` | `ProblemEntity.inputDesc` | `inputDesc` |
| 输出说明 | `problems.output_desc` | `ProblemEntity.outputDesc` | `outputDesc` |
| 知识点JSON | `problems.knowledge_points` | `ProblemEntity.knowledgePoints` | `knowledgePoints`（解析为数组） |
| 支持语言JSON | `problems.support_languages` | `ProblemEntity.supportLanguages` | `supportLanguages`（解析为数组） |
| 题目状态 | `problems.status` | `ProblemEntity.status (Integer)` | 不直接出参 |

### 3.3 测试用例

| 语义 | 表.字段 | Java字段 | 说明 |
|---|---|---|---|
| 用例排序 | `testcases.sort_order` | `TestcaseEntity.sortOrder` | 不是 `case_order` |
| 启用状态 | `testcases.is_active` | `TestcaseEntity.isActive` | 不是 `status` |
| 用例类型 | `testcases.case_type` | `TestcaseEntity.caseType` | `SAMPLE/HIDDEN` |
| 输入 | `testcases.input_data` | `TestcaseEntity.inputData` |  |
| 输出 | `testcases.output_data` | `TestcaseEntity.outputData` |  |

### 3.4 提交与判题回写

| 语义 | 表.字段 | Java字段 | API字段 |
|---|---|---|---|
| 提交ID | `submissions.id` | `SubmissionEntity.id` | `submissionId` / `id` |
| 判题状态 | `submissions.judge_status` | `SubmissionEntity.judgeStatus` | `judgeStatus` |
| 通过用例数 | `submissions.passed_case_count` | `SubmissionEntity.passedCaseCount` | `passedCaseCount` |
| 总用例数 | `submissions.total_case_count` | `SubmissionEntity.totalCaseCount` | `totalCaseCount` |
| 判题追踪ID | `submissions.judge_trace_id` | `SubmissionEntity.judgeTraceId` | `judgeTraceId` |

### 3.5 错题本

| 语义 | 表.字段 | Java字段 | API字段 |
|---|---|---|---|
| 错误类型 | `wrong_questions.last_wrong_type` | mapper参数 `wrongType` | `lastWrongType` |
| 最近错误提交 | `wrong_questions.last_submission_id` | `lastSubmissionId` | `lastSubmissionId` |
| 错误次数 | `wrong_questions.wrong_count` | `wrongCount` | `wrongCount` |

> 注意：这里是 `last_wrong_type`，不是 `last_wrong_status`。

### 3.6 推荐记录

| 语义 | 表.字段 | Java字段 | API字段 |
|---|---|---|---|
| 推荐触发来源 | `recommendation_records.trigger_source` | `triggerSource` | 不直接出参 |
| 推荐资源ID | `recommendation_records.resource_id` | `resourceId` | 不直接出参 |
| 推荐题目ID | `recommendation_records.problem_id` | `problemId` | `problemId` |
| 推荐分数 | `recommendation_records.score` | `score` | `score` |

## 4. 已知兼容策略

- `AuthResponseVo` 同时返回 `userId` 与 `id`（兼容旧前端）。
- 新开发接口一律读取 `userId`，不再依赖兼容字段 `id`。

## 5. 开发检查清单

- SQL 写字段时优先对照本文件，不凭记忆写旧字段名。
- 新增字段必须同步三处：`DDL -> Entity -> Mapper/XML`。
- 对外接口字段变更必须标明是否向后兼容。
