package com.leetcode.leetcode_ai.dto;

import lombok.Data;

@Data
public class JudgeCaseResultItemDto {
    private Long testcaseId;
    private Integer caseIndex;
    private String judgeStatus;
    private Integer runTimeMs;
    private Integer memoryKb;
    private String expectedOutput;
    private String actualOutput;
    private String errorMessage;
}
