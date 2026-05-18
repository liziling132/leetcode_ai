package com.leetcode.leetcode_ai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserStatsResponseVo {
    private long totalSubmissions;
    private long acceptedSubmissions;
    private double acceptanceRate;
    private List<DailySubmissionPointVo> recent7Days;
}
