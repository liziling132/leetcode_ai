package com.leetcode.leetcode_ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserPasswordUpdateRequestDto {

    @NotBlank(message = "oldPassword is required")
    @Size(min = 6, max = 64, message = "oldPassword length must be 6-64")
    private String oldPassword;

    @NotBlank(message = "newPassword is required")
    @Size(min = 6, max = 64, message = "newPassword length must be 6-64")
    private String newPassword;
}
