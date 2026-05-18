package com.leetcode.leetcode_ai.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TagEntity {
    private Long id;
    private String tagName;
    private String tagType;
    private LocalDateTime createdAt;
}
