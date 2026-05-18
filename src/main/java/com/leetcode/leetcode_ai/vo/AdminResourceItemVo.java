package com.leetcode.leetcode_ai.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AdminResourceItemVo {
    private Long id;
    private String resourceType;
    private String title;
    private String url;
    private List<String> knowledgePoints;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
