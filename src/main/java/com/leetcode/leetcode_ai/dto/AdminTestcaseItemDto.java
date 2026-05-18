package com.leetcode.leetcode_ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminTestcaseItemDto {

    @NotBlank(message = "caseType is required")
    private String caseType;

    @NotNull(message = "inputData is required")
    private String inputData;

    @NotNull(message = "outputData is required")
    private String outputData;

    private Integer score;

    private Integer sortOrder;

    private Integer isActive;
}
