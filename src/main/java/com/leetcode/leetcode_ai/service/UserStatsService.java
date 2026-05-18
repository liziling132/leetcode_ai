package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.vo.UserStatsResponseVo;
import com.leetcode.leetcode_ai.vo.WeakPointsResponseVo;

public interface UserStatsService {

    UserStatsResponseVo userStats();

    WeakPointsResponseVo weakPoints(int size);
}
