package com.leetcode.leetcode_ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AdminProblemUpsertRequestDto {

    @NotBlank(message = "title is required")
    @Size(max = 200, message = "title too long")
    private String title;

    @NotBlank(message = "content is required")
    private String content;

    private String inputDesc;

    private String outputDesc;

    @NotBlank(message = "difficulty is required")
    private String difficulty;

    private List<String> knowledgePoints;

    @NotEmpty(message = "supportLanguages is required")
    private List<String> supportLanguages;

    @NotNull(message = "tagIds is required")
    private List<Long> tagIds;

    private Integer status;
}
