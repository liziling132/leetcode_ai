package com.leetcode.leetcode_ai.judge.service;

import com.leetcode.leetcode_ai.judge.dto.JudgeTriggerResponse;

public interface JudgeTriggerService {

    JudgeTriggerResponse trigger(Long submissionId);
}
