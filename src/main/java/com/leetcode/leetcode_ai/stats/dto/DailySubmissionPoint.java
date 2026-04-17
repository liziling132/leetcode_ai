package com.leetcode.leetcode_ai.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailySubmissionPoint {
    private String date;
    private long submissions;
}
