package com.leetcode.leetcode_ai.judge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JudgeCallbackRequest {

    @NotNull(message = "submissionId is required")
    private Long submissionId;

    @NotBlank(message = "judgeStatus is required")
    private String judgeStatus;

    private String compileLog;

    private Integer runTimeMs;

    private Integer memoryKb;

    private Integer passedCaseCount;

    private Integer totalCaseCount;

    private String judgeTraceId;
}
