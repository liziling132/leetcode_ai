package com.leetcode.leetcode_ai.recommendation.dto;

import lombok.Data;

@Data
public class RecommendationItemResponse {
    private Long problemId;
    private String title;
    private String difficulty;
    private String reason;
    private Double score;
}
