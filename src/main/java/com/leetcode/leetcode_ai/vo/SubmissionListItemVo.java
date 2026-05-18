package com.leetcode.leetcode_ai.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubmissionListItemVo {
    private Long id;
    private Long problemId;
    private String problemTitle;
    private String language;
    private String judgeStatus;
    private Integer runTimeMs;
    private Integer memoryKb;
    private Integer passedCaseCount;
    private Integer totalCaseCount;
    private LocalDateTime submittedAt;
    private LocalDateTime judgedAt;
}
