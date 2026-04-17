package com.leetcode.leetcode_ai.judge.queue;

public interface JudgeQueueService {

    boolean enqueue(Long submissionId);

    JudgeQueueStats stats();
}
