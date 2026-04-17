package com.leetcode.leetcode_ai.recommendation.service;

import com.leetcode.leetcode_ai.recommendation.dto.RecommendationResponse;

public interface RecommendationService {

    RecommendationResponse recommend(int size);
}
