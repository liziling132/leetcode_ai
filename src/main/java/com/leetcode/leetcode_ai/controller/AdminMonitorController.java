package com.leetcode.leetcode_ai.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.service.AdminMonitorService;
import com.leetcode.leetcode_ai.vo.AdminMonitorOverviewVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/monitor")
@RequiredArgsConstructor
public class AdminMonitorController {

    private final AdminMonitorService adminMonitorService;

    @GetMapping("/overview")
    public ApiResponse<AdminMonitorOverviewVo> overview() {
        return ApiResponse.success(adminMonitorService.overview());
    }
}
