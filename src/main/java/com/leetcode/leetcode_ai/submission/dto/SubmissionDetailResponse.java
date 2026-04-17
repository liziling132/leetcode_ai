package com.leetcode.leetcode_ai.submission.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubmissionDetailResponse {
    private Long id;
    private Long problemId;
    private String language;
    private String judgeStatus;
    private String compileLog;
    private Integer runTimeMs;
    private Integer memoryKb;
    private Integer passedCaseCount;
    private Integer totalCaseCount;
    private LocalDateTime submittedAt;
    private LocalDateTime judgedAt;
}
