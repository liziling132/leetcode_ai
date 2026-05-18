package com.leetcode.leetcode_ai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseVo {
    private Long userId;
    private String username;
    private String nickname;
    private String token;

    // Backward-compatible alias for clients reading `data.id`
    public Long getId() {
        return userId;
    }
}
