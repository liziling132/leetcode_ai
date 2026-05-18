package com.leetcode.leetcode_ai.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.dto.CreateSubmissionRequestDto;
import com.leetcode.leetcode_ai.dto.RunTestRequestDto;
import com.leetcode.leetcode_ai.vo.CreateSubmissionResponseVo;
import com.leetcode.leetcode_ai.vo.RunTestResponseVo;
import com.leetcode.leetcode_ai.vo.AiCodeExplainVo;
import com.leetcode.leetcode_ai.vo.SubmissionCaseResultItemVo;
import com.leetcode.leetcode_ai.vo.SubmissionCaseResultPageVo;
import com.leetcode.leetcode_ai.vo.SubmissionDetailResponseVo;
import com.leetcode.leetcode_ai.vo.SubmissionPageVo;
import com.leetcode.leetcode_ai.vo.SubmissionVersionPageVo;
import com.leetcode.leetcode_ai.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping
    public ApiResponse<CreateSubmissionResponseVo> create(@Valid @RequestBody CreateSubmissionRequestDto request) {
        return ApiResponse.success(submissionService.create(request));
    }

    @PostMapping("/run-test")
    public ApiResponse<RunTestResponseVo> runTest(@Valid @RequestBody RunTestRequestDto request) {
        return ApiResponse.success(submissionService.runTest(request));
    }

    @GetMapping
    public ApiResponse<SubmissionPageVo> list(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "20") int size,
                                              @RequestParam(required = false) Long problemId,
                                              @RequestParam(required = false) String judgeStatus,
                                              @RequestParam(required = false) String language,
                                              @RequestParam(required = false) String submittedFrom,
                                              @RequestParam(required = false) String submittedTo) {
        return ApiResponse.success(submissionService.list(page, size, problemId, judgeStatus, language, submittedFrom, submittedTo));
    }

    @GetMapping("/problem/{problemId}/versions")
    public ApiResponse<SubmissionVersionPageVo> versionHistory(@PathVariable Long problemId,
                                                               @RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.success(submissionService.versionHistory(problemId, page, size));
    }

    @GetMapping("/{id}")
    public ApiResponse<SubmissionDetailResponseVo> detail(@PathVariable("id") Long id) {
        return ApiResponse.success(submissionService.detail(id));
    }

    @GetMapping("/{id}/case-results")
    public ApiResponse<SubmissionCaseResultPageVo> caseResults(@PathVariable("id") Long id,
                                                               @RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "20") int size,
                                                               @RequestParam(defaultValue = "false") boolean onlyFailed) {
        return ApiResponse.success(submissionService.caseResults(id, page, size, onlyFailed));
    }

    @GetMapping("/{id}/ai-explain")
    public ApiResponse<AiCodeExplainVo> explain(@PathVariable("id") Long id) {
        return ApiResponse.success(submissionService.explainCode(id));
    }
}
