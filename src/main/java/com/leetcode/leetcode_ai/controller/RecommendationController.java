package com.leetcode.leetcode_ai.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.vo.RecommendationRecordPageVo;
import com.leetcode.leetcode_ai.vo.RecommendationResponseVo;
import com.leetcode.leetcode_ai.service.RecommendationService;
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
    public ApiResponse<RecommendationResponseVo> recommend(@RequestParam(defaultValue = "5") int size) {
        return ApiResponse.success(recommendationService.recommend(size));
    }

    @GetMapping("/records")
    public ApiResponse<RecommendationRecordPageVo> records(@RequestParam(defaultValue = "1") int page,
                                                           @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.success(recommendationService.records(page, size));
    }
}
