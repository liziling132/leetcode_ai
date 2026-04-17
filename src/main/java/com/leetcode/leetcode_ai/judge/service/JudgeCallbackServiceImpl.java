package com.leetcode.leetcode_ai.judge.service;

import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.judge.dto.JudgeCallbackRequest;
import com.leetcode.leetcode_ai.submission.entity.Submission;
import com.leetcode.leetcode_ai.submission.mapper.SubmissionMapper;
import com.leetcode.leetcode_ai.wrongquestion.mapper.WrongQuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Set;

@Service
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
    private final WrongQuestionMapper wrongQuestionMapper;

    @Override
    @Transactional
    public void handleCallback(JudgeCallbackRequest request) {
        String status = normalizeStatus(request.getJudgeStatus());
        if (!FINAL_STATUSES.contains(status)) {
            throw new BizException(400, "Invalid final judge status");
        }

        Submission submission = submissionMapper.findById(request.getSubmissionId());
        if (submission == null) {
            throw new BizException(404, "Submission not found");
        }

        // Idempotent: ignore repeated callback once already in final status.
        if (FINAL_STATUSES.contains(submission.getJudgeStatus())) {
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
                    submission.getUserId(),
                    submission.getProblemId(),
                    submission.getId(),
                    status
            );
        }
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
}
