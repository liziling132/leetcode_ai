package com.leetcode.leetcode_ai.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class RegisterRequestDto {
    @NotBlank(message = "???????")
    @Size(min = 4, max = 32, message = "??????? 4-32 ??")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "???????????????")
    private String username;
    @NotBlank(message = "??????")
    @Size(min = 6, max = 64, message = "?????? 6-64 ??")
    private String password;
    @Size(max = 32, message = "???????? 32")
    private String nickname;
}