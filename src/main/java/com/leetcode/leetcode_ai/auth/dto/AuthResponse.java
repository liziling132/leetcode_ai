package com.leetcode.leetcode_ai.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long userId;
    private String username;
    private String nickname;
    private String token;
}
