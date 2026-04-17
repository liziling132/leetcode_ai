package com.leetcode.leetcode_ai.recommendation.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.recommendation.dto.RecommendationResponse;
import com.leetcode.leetcode_ai.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public ApiResponse<RecommendationResponse> recommend(@RequestParam(defaultValue = "5") int size) {
        return ApiResponse.success(recommendationService.recommend(size));
    }
}
