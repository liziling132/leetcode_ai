package com.leetcode.leetcode_ai.judge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JudgeTriggerResponse {
    private Long submissionId;
    private String finalStatus;
    private int passedCaseCount;
    private int totalCaseCount;
}
