package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.dto.JudgeCallbackRequestDto;

public interface JudgeCallbackService {

    void handleCallback(JudgeCallbackRequestDto request);
}
