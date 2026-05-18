package com.leetcode.leetcode_ai.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminResourceEntity {
    private Long id;
    private String resourceType;
    private String title;
    private String url;
    private String knowledgePoints;
    private Integer status;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
