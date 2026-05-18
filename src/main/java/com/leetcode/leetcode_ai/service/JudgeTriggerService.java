package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.vo.JudgeTriggerResponseVo;

public interface JudgeTriggerService {

    JudgeTriggerResponseVo trigger(Long submissionId);
}
