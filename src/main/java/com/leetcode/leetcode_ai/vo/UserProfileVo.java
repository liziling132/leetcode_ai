package com.leetcode.leetcode_ai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileVo {
    private Long id;
    private String username;
    private String nickname;
    private String avatarUrl;
    private String bio;
    private Integer status;
}
