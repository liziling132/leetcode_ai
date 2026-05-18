package com.leetcode.leetcode_ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminTagUpsertRequestDto {

    @NotBlank(message = "tagName is required")
    @Size(max = 50, message = "tagName too long")
    private String tagName;

    @Size(max = 30, message = "tagType too long")
    private String tagType;
}
