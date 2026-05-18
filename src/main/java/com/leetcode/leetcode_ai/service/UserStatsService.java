package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.vo.UserStatsResponseVo;
import com.leetcode.leetcode_ai.vo.WeakPointsResponseVo;
import com.leetcode.leetcode_ai.vo.LearningAdviceVo;

public interface UserStatsService {

    UserStatsResponseVo userStats();

    WeakPointsResponseVo weakPoints(int size);

    LearningAdviceVo learningAdvice();
}
