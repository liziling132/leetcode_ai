package com.leetcode.leetcode_ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class JudgeCallbackRequestDto {

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

    // Optional case-level details from external judge service.
    private List<JudgeCaseResultItemDto> caseResults;
}
