package com.leetcode.leetcode_ai.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminTagItemVo {
    private Long id;
    private String tagName;
    private String tagType;
    private Long problemCount;
    private LocalDateTime createdAt;
}
