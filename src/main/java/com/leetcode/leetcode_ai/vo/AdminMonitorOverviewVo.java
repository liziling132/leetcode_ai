package com.leetcode.leetcode_ai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminMonitorOverviewVo {
    private long totalUsers;
    private long totalProblems;
    private long totalSubmissions;
    private long pendingSubmissions;
    private long failedSubmissions24h;
    private long recommendationCount24h;
}
