package com.leetcode.leetcode_ai.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserStatsResponse {
    private long totalSubmissions;
    private long acceptedSubmissions;
    private double acceptanceRate;
    private List<DailySubmissionPoint> recent7Days;
}
