package com.leetcode.leetcode_ai.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AdminProblemListItemVo {
    private Long id;
    private String title;
    private String difficulty;
    private Integer status;
    private List<String> tagNames;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
