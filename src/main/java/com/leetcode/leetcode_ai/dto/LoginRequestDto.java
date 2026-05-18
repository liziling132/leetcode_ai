package com.leetcode.leetcode_ai.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class LoginRequestDto {
    @NotBlank(message = "???????")
    private String username;
    @NotBlank(message = "??????")
    private String password;
}