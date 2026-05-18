package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.dto.JudgeCallbackRequestDto;
import com.leetcode.leetcode_ai.dto.JudgeCaseResultItemDto;
import com.leetcode.leetcode_ai.entity.ProblemEntity;
import com.leetcode.leetcode_ai.entity.SubmissionCaseResultEntity;
import com.leetcode.leetcode_ai.entity.SubmissionEntity;
import com.leetcode.leetcode_ai.mapper.ProblemMapper;
import com.leetcode.leetcode_ai.mapper.SubmissionCaseResultMapper;
import com.leetcode.leetcode_ai.mapper.SubmissionMapper;
import com.leetcode.leetcode_ai.mapper.WrongQuestionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class JudgeCallbackServiceImpl implements JudgeCallbackService {

    private static final Set<String> FINAL_STATUSES = Set.of(
            "ACCEPTED",
            "WRONG_ANSWER",
            "COMPILE_ERROR",
            "RUNTIME_ERROR",
            "TIME_LIMIT_EXCEEDED",
            "MEMORY_LIMIT_EXCEEDED",
            "SYSTEM_ERROR"
    );

    private final SubmissionMapper submissionMapper;
    private final SubmissionCaseResultMapper submissionCaseResultMapper;
    private final WrongQuestionMapper wrongQuestionMapper;
    private final ProblemMapper problemMapper;
    private final RecommendationService recommendationService;
    private final AiTextService aiTextService;

    @Override
    @Transactional
    public void handleCallback(JudgeCallbackRequestDto request) {
        String status = normalizeStatus(request.getJudgeStatus());
        if (!FINAL_STATUSES.contains(status)) {
            throw new BizException(400, "Invalid final judge status");
        }

        SubmissionEntity submissionEntity = submissionMapper.findById(request.getSubmissionId());
        if (submissionEntity == null) {
            throw new BizException(404, "Submission not found");
        }

        // Idempotent for submission result; still allow case detail repair.
        if (FINAL_STATUSES.contains(submissionEntity.getJudgeStatus())) {
            persistCaseResultsIfPresent(request.getSubmissionId(), request.getCaseResults());
            return;
        }

        int updated = submissionMapper.updateJudgeResult(
                request.getSubmissionId(),
                status,
                request.getCompileLog(),
                request.getRunTimeMs(),
                request.getMemoryKb(),
                defaultNonNegative(request.getPassedCaseCount()),
                defaultNonNegative(request.getTotalCaseCount()),
                request.getJudgeTraceId()
        );
        if (updated == 0) {
            return;
        }

        if (!"ACCEPTED".equals(status)) {
            wrongQuestionMapper.upsertWrongQuestion(
                    submissionEntity.getUserId(),
                    submissionEntity.getProblemId(),
                    submissionEntity.getId(),
                    status
            );

            ProblemEntity problemEntity = problemMapper.findOnlineById(submissionEntity.getProblemId());
            String knowledge = summarizeKnowledge(problemEntity == null ? null : problemEntity.getKnowledgePoints());
            AiTextResult aiSummaryResult = aiTextService.summarizeWrongQuestion(status, request.getCompileLog(), knowledge);
            String summary = aiSummaryResult != null && StringUtils.hasText(aiSummaryResult.content())
                    ? shortText(aiSummaryResult.content(), 2000)
                    : buildRuleSummary(status, request.getCompileLog(), knowledge);
            String summaryModel = aiSummaryResult != null && StringUtils.hasText(aiSummaryResult.model())
                    ? aiSummaryResult.model()
                    : "rule-mvp-v1";

            wrongQuestionMapper.updateAiSummary(
                    submissionEntity.getUserId(),
                    submissionEntity.getProblemId(),
                    summary,
                    summaryModel
            );

            try {
                recommendationService.generateForWrongQuestion(
                        submissionEntity.getUserId(),
                        submissionEntity.getProblemId(),
                        3
                );
            } catch (Exception ex) {
                log.error("Auto recommendation generate failed, submissionId={}", submissionEntity.getId(), ex);
            }
        }

        persistCaseResultsIfPresent(request.getSubmissionId(), request.getCaseResults());
    }

    private String normalizeStatus(String judgeStatus) {
        if (!StringUtils.hasText(judgeStatus)) {
            return "";
        }
        return judgeStatus.trim().toUpperCase();
    }

    private int defaultNonNegative(Integer value) {
        if (value == null || value < 0) {
            return 0;
        }
        return value;
    }

    private void persistCaseResultsIfPresent(Long submissionId, List<JudgeCaseResultItemDto> caseResults) {
        if (caseResults == null) {
            return;
        }
        submissionCaseResultMapper.deleteBySubmissionId(submissionId);
        List<SubmissionCaseResultEntity> rows = new ArrayList<>(caseResults.size());
        for (int i = 0; i < caseResults.size(); i++) {
            JudgeCaseResultItemDto item = caseResults.get(i);
            if (item == null) {
                continue;
            }
            rows.add(toCaseResultEntity(submissionId, item, i + 1));
        }
        for (SubmissionCaseResultEntity row : rows) {
            submissionCaseResultMapper.insert(row);
        }
    }

    private SubmissionCaseResultEntity toCaseResultEntity(Long submissionId, JudgeCaseResultItemDto item, int fallbackCaseIndex) {
        SubmissionCaseResultEntity row = new SubmissionCaseResultEntity();
        row.setSubmissionId(submissionId);
        row.setTestcaseId(item.getTestcaseId());
        row.setCaseIndex(item.getCaseIndex() == null || item.getCaseIndex() < 1 ? fallbackCaseIndex : item.getCaseIndex());
        row.setJudgeStatus(normalizeCaseStatus(item.getJudgeStatus()));
        row.setRunTimeMs(item.getRunTimeMs() == null || item.getRunTimeMs() < 0 ? null : item.getRunTimeMs());
        row.setMemoryKb(item.getMemoryKb() == null || item.getMemoryKb() < 0 ? null : item.getMemoryKb());
        row.setExpectedOutput(shortText(item.getExpectedOutput(), 2000));
        row.setActualOutput(shortText(item.getActualOutput(), 2000));
        row.setErrorMessage(shortText(item.getErrorMessage(), 2000));
        return row;
    }

    private String normalizeCaseStatus(String status) {
        String normalized = normalizeStatus(status);
        return StringUtils.hasText(normalized) ? normalized : "SYSTEM_ERROR";
    }

    private String buildRuleSummary(String status, String compileLog, String knowledge) {
        StringBuilder summary = new StringBuilder(256);
        summary.append("错误类型：").append(status).append("。");
        summary.append("可能原因：").append(explainReason(status)).append("。");
        summary.append("修改建议：").append(explainSuggestion(status)).append("。");
        if (StringUtils.hasText(knowledge)) {
            summary.append("建议复习知识点：").append(knowledge).append("。");
        }
        if ("COMPILE_ERROR".equals(status) && StringUtils.hasText(compileLog)) {
            summary.append("编译提示：").append(shortText(compileLog, 600));
        }
        return shortText(summary.toString(), 2000);
    }

    private String explainReason(String status) {
        return switch (status) {
            case "WRONG_ANSWER" -> "边界条件、下标处理或输出格式与题目要求不一致";
            case "COMPILE_ERROR" -> "语法、类名或方法签名与题目约定不一致";
            case "RUNTIME_ERROR" -> "空指针、数组越界或异常分支未处理";
            case "TIME_LIMIT_EXCEEDED" -> "算法复杂度偏高或存在重复计算";
            case "MEMORY_LIMIT_EXCEEDED" -> "数据结构占用过大或中间对象过多";
            default -> "代码实现与题目约束之间存在不匹配";
        };
    }

    private String explainSuggestion(String status) {
        return switch (status) {
            case "WRONG_ANSWER" -> "补充极端样例并打印关键变量逐步对比";
            case "COMPILE_ERROR" -> "先修复第一条编译报错并保证类名为 Solution";
            case "RUNTIME_ERROR" -> "增加判空、边界保护和非法输入处理";
            case "TIME_LIMIT_EXCEEDED" -> "改用更优复杂度算法并减少循环内重复操作";
            case "MEMORY_LIMIT_EXCEEDED" -> "减少冗余缓存，优先原地处理";
            default -> "先最小化复现问题，再逐项对照题目约束排查";
        };
    }

    private String summarizeKnowledge(String knowledgePoints) {
        if (!StringUtils.hasText(knowledgePoints)) {
            return null;
        }
        String normalized = knowledgePoints.trim()
                .replace("[", "")
                .replace("]", "")
                .replace("\"", "")
                .replace("，", ",")
                .replaceAll("\\s+", " ");
        if (!StringUtils.hasText(normalized)) {
            return null;
        }
        String[] parts = normalized.split(",");
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            String item = part.trim();
            if (!StringUtils.hasText(item)) {
                continue;
            }
            if (!builder.isEmpty()) {
                builder.append("、");
            }
            builder.append(item);
            if (builder.length() >= 60) {
                break;
            }
        }
        return builder.isEmpty() ? null : builder.toString();
    }

    private String shortText(String text, int maxLength) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        String normalized = text.trim();
        return normalized.length() <= maxLength ? normalized : normalized.substring(0, maxLength);
    }
}
