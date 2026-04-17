package com.leetcode.leetcode_ai.stats.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.stats.dto.UserStatsResponse;
import com.leetcode.leetcode_ai.stats.dto.WeakPointsResponse;
import com.leetcode.leetcode_ai.stats.service.UserStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserStatsController {

    private final UserStatsService userStatsService;

    @GetMapping("/stats")
    public ApiResponse<UserStatsResponse> stats() {
        return ApiResponse.success(userStatsService.userStats());
    }

    @GetMapping("/weak-points")
    public ApiResponse<WeakPointsResponse> weakPoints(@RequestParam(defaultValue = "5") int size) {
        return ApiResponse.success(userStatsService.weakPoints(size));
    }
}
