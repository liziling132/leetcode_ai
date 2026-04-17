package com.leetcode.leetcode_ai.submission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateSubmissionRequest {

    @NotNull(message = "problemId is required")
    private Long problemId;

    @NotBlank(message = "language is required")
    @Size(max = 30, message = "language too long")
    private String language;

    @NotBlank(message = "codeContent is required")
    private String codeContent;

    @NotBlank(message = "source is required")
    private String source;
}
