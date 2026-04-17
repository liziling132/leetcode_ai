package com.leetcode.leetcode_ai.submission.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.judge.queue.JudgeQueueService;
import com.leetcode.leetcode_ai.problem.entity.Problem;
import com.leetcode.leetcode_ai.problem.mapper.ProblemMapper;
import com.leetcode.leetcode_ai.security.JwtUserPrincipal;
import com.leetcode.leetcode_ai.submission.dto.CreateSubmissionRequest;
import com.leetcode.leetcode_ai.submission.dto.CreateSubmissionResponse;
import com.leetcode.leetcode_ai.submission.dto.SubmissionDetailResponse;
import com.leetcode.leetcode_ai.submission.entity.Submission;
import com.leetcode.leetcode_ai.submission.mapper.SubmissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private static final String STATUS_PENDING = "PENDING";

    private final SubmissionMapper submissionMapper;
    private final ProblemMapper problemMapper;
    private final ObjectMapper objectMapper;
    private final JudgeQueueService judgeQueueService;

    @Override
    @Transactional
    public CreateSubmissionResponse create(CreateSubmissionRequest request) {
        String source = normalizeSource(request.getSource());
        Problem problem = problemMapper.findOnlineById(request.getProblemId());
        if (problem == null) {
            throw new BizException(404, "Problem not found");
        }

        if (!isLanguageSupported(request.getLanguage(), problem.getSupportLanguages())) {
            throw new BizException(400, "Language not supported by this problem");
        }

        Submission submission = new Submission();
        submission.setUserId(currentUserId());
        submission.setProblemId(request.getProblemId());
        submission.setLanguage(request.getLanguage().trim());
        submission.setCodeContent(request.getCodeContent());
        submission.setJudgeStatus(STATUS_PENDING);
        submissionMapper.insert(submission);
        if ("submit".equals(source)) {
            judgeQueueService.enqueue(submission.getId());
        }
        return new CreateSubmissionResponse(submission.getId(), STATUS_PENDING);
    }

    @Override
    public SubmissionDetailResponse detail(Long submissionId) {
        Submission submission = submissionMapper.findById(submissionId);
        if (submission == null) {
            throw new BizException(404, "Submission not found");
        }
        if (!submission.getUserId().equals(currentUserId())) {
            throw new BizException(403, "Forbidden");
        }

        SubmissionDetailResponse response = new SubmissionDetailResponse();
        response.setId(submission.getId());
        response.setProblemId(submission.getProblemId());
        response.setLanguage(submission.getLanguage());
        response.setJudgeStatus(submission.getJudgeStatus());
        response.setCompileLog(submission.getCompileLog());
        response.setRunTimeMs(submission.getRunTimeMs());
        response.setMemoryKb(submission.getMemoryKb());
        response.setPassedCaseCount(submission.getPassedCaseCount());
        response.setTotalCaseCount(submission.getTotalCaseCount());
        response.setSubmittedAt(submission.getSubmittedAt());
        response.setJudgedAt(submission.getJudgedAt());
        return response;
    }

    private String normalizeSource(String source) {
        if (!StringUtils.hasText(source)) {
            throw new BizException(400, "Source is required");
        }
        String normalized = source.trim().toLowerCase(Locale.ROOT);
        if (!"run".equals(normalized) && !"submit".equals(normalized)) {
            throw new BizException(400, "Source must be run or submit");
        }
        return normalized;
    }

    private boolean isLanguageSupported(String language, String supportLanguagesJson) {
        if (!StringUtils.hasText(language)) {
            return false;
        }
        Set<String> supported = parseSupportLanguages(supportLanguagesJson).stream()
                .map(item -> item.toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());
        return supported.contains(language.trim().toLowerCase(Locale.ROOT));
    }

    private List<String> parseSupportLanguages(String jsonValue) {
        if (!StringUtils.hasText(jsonValue)) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(jsonValue, new TypeReference<List<String>>() {
            });
        } catch (Exception ignored) {
            return List.of(jsonValue);
        }
    }

    private Long currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof JwtUserPrincipal principal)) {
            throw new BizException(401, "Unauthorized");
        }
        return principal.userId();
    }
}
