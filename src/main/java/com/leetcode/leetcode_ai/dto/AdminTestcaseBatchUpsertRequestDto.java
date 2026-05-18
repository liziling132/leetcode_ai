package com.leetcode.leetcode_ai.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class AdminTestcaseBatchUpsertRequestDto {

    @Valid
    @NotEmpty(message = "testcases is required")
    private List<AdminTestcaseItemDto> testcases;
}
