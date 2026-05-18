package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.vo.RecommendationResponseVo;
import com.leetcode.leetcode_ai.vo.RecommendationRecordPageVo;

public interface RecommendationService {

    RecommendationResponseVo recommend(int size);

    int generateForWrongQuestion(Long userId, Long problemId, int size);

    RecommendationRecordPageVo records(int page, int size);
}
