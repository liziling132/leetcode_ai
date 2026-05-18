package com.leetcode.leetcode_ai.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminUserStatusUpdateRequestDto {
    @NotNull(message = "status is required")
    private Integer status;
}
