package com.leetcode.leetcode_ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserProfileUpdateRequestDto {

    @NotBlank(message = "username is required")
    @Size(min = 4, max = 32, message = "username length must be 4-32")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "username can only contain letters, numbers and underscore")
    private String username;

    @Size(max = 3_000_000, message = "avatarUrl too long")
    private String avatarUrl;

    @Size(max = 500, message = "bio too long")
    private String bio;
}
