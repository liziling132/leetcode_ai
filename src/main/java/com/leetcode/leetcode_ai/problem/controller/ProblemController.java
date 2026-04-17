package com.leetcode.leetcode_ai.problem.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.problem.dto.ProblemDetailResponse;
import com.leetcode.leetcode_ai.problem.dto.ProblemPageResponse;
import com.leetcode.leetcode_ai.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping
    public ApiResponse<ProblemPageResponse> list(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "20") int size,
                                                 @RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false) String difficulty,
                                                 @RequestParam(required = false) Long tagId) {
        return ApiResponse.success(problemService.list(page, size, keyword, difficulty, tagId));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProblemDetailResponse> detail(@PathVariable Long id) {
        return ApiResponse.success(problemService.detail(id));
    }
}
