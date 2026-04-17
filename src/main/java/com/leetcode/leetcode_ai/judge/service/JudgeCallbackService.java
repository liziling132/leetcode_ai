package com.leetcode.leetcode_ai.judge.service;

import com.leetcode.leetcode_ai.judge.dto.JudgeCallbackRequest;

public interface JudgeCallbackService {

    void handleCallback(JudgeCallbackRequest request);
}
