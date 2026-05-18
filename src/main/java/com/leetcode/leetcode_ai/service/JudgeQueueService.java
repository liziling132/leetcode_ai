package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.vo.JudgeQueueStatsVo;

public interface JudgeQueueService {

    boolean enqueue(Long submissionId);

    JudgeQueueStatsVo stats();
}
