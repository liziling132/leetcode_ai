package com.leetcode.leetcode_ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AdminResourceUpsertRequestDto {
    @NotBlank(message = "resourceType is required")
    private String resourceType;

    @NotBlank(message = "title is required")
    @Size(max = 200, message = "title too long")
    private String title;

    @NotBlank(message = "url is required")
    @Size(max = 1000, message = "url too long")
    private String url;

    private List<String> knowledgePoints;

    @NotNull(message = "status is required")
    private Integer status;
}
