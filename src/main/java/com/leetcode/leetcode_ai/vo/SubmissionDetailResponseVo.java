package com.leetcode.leetcode_ai.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubmissionDetailResponseVo {
    private Long id;
    private Long problemId;
    private String language;
    private String judgeStatus;
    private String judgeTraceId;
    private String compileLog;
    private Integer runTimeMs;
    private Integer memoryKb;
    private Integer passedCaseCount;
    private Integer totalCaseCount;
    private LocalDateTime submittedAt;
    private LocalDateTime judgedAt;
}
