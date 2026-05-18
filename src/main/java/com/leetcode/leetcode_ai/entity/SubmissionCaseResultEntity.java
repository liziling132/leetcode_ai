package com.leetcode.leetcode_ai.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubmissionCaseResultEntity {
    private Long id;
    private Long submissionId;
    private Long testcaseId;
    private Integer caseIndex;
    private String judgeStatus;
    private Integer runTimeMs;
    private Integer memoryKb;
    private String expectedOutput;
    private String actualOutput;
    private String errorMessage;
    private LocalDateTime createdAt;
}
