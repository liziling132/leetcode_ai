package com.leetcode.leetcode_ai.submission.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.submission.dto.CreateSubmissionRequest;
import com.leetcode.leetcode_ai.submission.dto.CreateSubmissionResponse;
import com.leetcode.leetcode_ai.submission.dto.SubmissionDetailResponse;
import com.leetcode.leetcode_ai.submission.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping
    public ApiResponse<CreateSubmissionResponse> create(@Valid @RequestBody CreateSubmissionRequest request) {
        return ApiResponse.success(submissionService.create(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<SubmissionDetailResponse> detail(@PathVariable("id") Long id) {
        return ApiResponse.success(submissionService.detail(id));
    }
}
