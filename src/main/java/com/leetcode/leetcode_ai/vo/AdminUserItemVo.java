package com.leetcode.leetcode_ai.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminUserItemVo {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private Integer status;
    private LocalDateTime createdAt;
    private Long submissionCount;
    private Long acceptedCount;
}
