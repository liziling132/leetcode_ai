package com.leetcode.leetcode_ai.vo;

import lombok.Data;

@Data
public class RecommendationItemResponseVo {
    private Long problemId;
    private String title;
    private String difficulty;
    private String reason;
    private Double score;
    private String resourceType;
    private String resourceTitle;
    private String resourceUrl;
}
