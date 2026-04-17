package com.leetcode.leetcode_ai.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecommendationResponse {
    private int size;
    private List<RecommendationItemResponse> list;
}
