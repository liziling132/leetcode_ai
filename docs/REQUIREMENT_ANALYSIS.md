# 易题通需求分析（基于 PRD V1.0 与 MVP 数据库设计）

## 1. 分析结论总览

一期建议按“先打通闭环，再增强体验”的策略推进：

1. 先实现 P0 的最小学习闭环：`题目浏览 -> 提交判题 -> 错题入库 -> AI总结 -> 推荐返回`
2. 判题与 AI 采用异步化解耦，保证主链路稳定
3. 数据库保持单库，按业务域分包和分层，给后续拆服务留边界

当前项目已具备 Spring Boot 基础骨架，适合直接进入后端域模型与接口开发阶段。

## 2. MVP 范围再确认

### 2.1 一期必须交付（P0）

- 用户注册/登录（最小可用）
- 题目列表、筛选、搜索、详情
- 代码提交与判题结果查询
- 提交记录
- 错题自动归档
- AI 错题总结
- 基于错题知识点推荐题目

### 2.2 建议延期到 P1 的能力

- 收藏题目
- 学习看板可视化（可先做简单统计接口）
- AI 代码解释
- 学习建议推送

原因：这些能力不阻断主闭环，可在 P0 稳定后迭代。

## 3. 关键业务流拆解

### 3.1 判题主链路

1. 用户提交代码，创建 `submissions` 记录，状态 `PENDING`
2. 投递判题任务到队列（或先用本地异步线程池）
3. 判题服务执行编译/运行并写回：
   - `submissions`（最终状态、总耗时、总内存、编译信息）
   - `submission_case_results`（逐测试点明细）
4. 若非 Accepted：
   - upsert `wrong_questions`
   - 触发 AI 总结任务

### 3.2 AI 与推荐链路

1. AI 输入：提交代码 + 判题状态 + 错误日志 + 题目知识点
2. AI 输出：错误原因、知识点、修复建议，回写 `wrong_questions.ai_summary`
3. 推荐服务按 `knowledge_points` 与错题频次匹配题目
4. 写入 `recommendation_records`，返回推荐结果和推荐理由

## 4. 数据模型核查与补充建议

现有数据库设计整体可用，但建议补充以下字段，降低后续返工风险：

1. `submissions`
   - 增加：`source`（run/submit），`error_message`（运行错误摘要）
2. `wrong_questions`
   - 增加：`first_wrong_at`、`wrong_count`（若尚未定义）
3. `problems`
   - 增加：`time_limit_ms`、`memory_limit_mb`（判题配置）
4. `recommendation_records`
   - 增加：`trigger_submission_id`（可追溯到触发提交）
5. 审计字段统一
   - 所有核心表建议统一 `created_at`、`updated_at`、`deleted_at(可选)`

## 5. 后端模块划分建议（单体分层）

建议包结构（先单体、后可拆服务）：

- `auth`：注册登录、鉴权
- `problem`：题目、标签、筛选检索
- `submission`：提交、判题状态、明细
- `wrongbook`：错题本聚合查询
- `ai`：AI总结适配器
- `recommendation`：规则推荐与推荐记录
- `stats`：学习统计
- `admin`：后台管理

## 6. API 最小清单（P0）

### 6.1 用户

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/users/me`

### 6.2 题目

- `GET /api/problems`
- `GET /api/problems/{id}`

### 6.3 提交与评测

- `POST /api/submissions`（正式提交）
- `GET /api/submissions/{id}`（提交结果）
- `GET /api/submissions`（我的提交记录）

### 6.4 错题与推荐

- `GET /api/wrong-questions`
- `GET /api/recommendations`

## 7. 非功能落地建议

1. 安全
- 密码：BCrypt
- 鉴权：JWT（MVP 足够）
- 判题：沙箱隔离（至少进程级隔离与资源限制）

2. 性能
- 题目列表走索引 + 分页
- 热门题目/标签可缓存 Redis
- 提交状态采用轮询接口（后续可升级 WebSocket）

3. 可维护性
- 统一错误码与全局异常处理
- 提交状态机枚举化（PENDING/RUNNING/AC/WA/CE/RE/TLE/MLE）
- AI 调用做超时与降级（失败不阻断判题主流程）

## 8. 开发里程碑（建议）

1. 第 1 阶段（后端基础）
- 数据库 DDL + MyBatis 实体/Mapper
- 认证与题目查询 API

2. 第 2 阶段（判题闭环）
- 提交创建、状态流转、结果回写
- 提交记录与结果详情

3. 第 3 阶段（AI + 推荐）
- 错题归档
- AI 总结接入
- 推荐记录生成与查询

4. 第 4 阶段（稳定性）
- 压测与索引优化
- 异常链路补齐
- 文档和联调

## 9. 当前项目下一步建议（可立即开始）

1. 新增 `sql/001_init_schema.sql`，先落地 MVP DDL
2. 新增 `docs/API_P0.yaml`（OpenAPI 草案）
3. 先实现 4 个最小接口：`register`、`login`、`problems list`、`create submission`

这样可以在 1~2 天内跑通最小后端链路，为前端并行开发提供稳定接口。
