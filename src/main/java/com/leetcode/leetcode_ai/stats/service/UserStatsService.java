package com.leetcode.leetcode_ai.stats.service;

import com.leetcode.leetcode_ai.stats.dto.UserStatsResponse;
import com.leetcode.leetcode_ai.stats.dto.WeakPointsResponse;

public interface UserStatsService {

    UserStatsResponse userStats();

    WeakPointsResponse weakPoints(int size);
}
