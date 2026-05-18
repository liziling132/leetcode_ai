package com.leetcode.leetcode_ai.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProblemEntity {
    private Long id;
    private String title;
    private String content;
    private String inputDesc;
    private String outputDesc;
    private String difficulty;
    private String knowledgePoints;
    private String supportLanguages;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
