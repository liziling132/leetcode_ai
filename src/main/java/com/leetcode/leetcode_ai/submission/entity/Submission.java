package com.leetcode.leetcode_ai.submission.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Submission {
    private Long id;
    private Long userId;
    private Long problemId;
    private String language;
    private String codeContent;
    private String judgeStatus;
    private String compileLog;
    private Integer runTimeMs;
    private Integer memoryKb;
    private Integer passedCaseCount;
    private Integer totalCaseCount;
    private String judgeTraceId;
    private LocalDateTime submittedAt;
    private LocalDateTime judgedAt;
}
