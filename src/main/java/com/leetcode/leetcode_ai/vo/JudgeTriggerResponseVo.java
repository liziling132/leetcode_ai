package com.leetcode.leetcode_ai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JudgeTriggerResponseVo {
    private Long submissionId;
    private String finalStatus;
    private int passedCaseCount;
    private int totalCaseCount;
}
