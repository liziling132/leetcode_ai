package com.leetcode.leetcode_ai.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.config.InternalApiProperties;
import com.leetcode.leetcode_ai.dto.JudgeCallbackRequestDto;
import com.leetcode.leetcode_ai.vo.JudgeTriggerResponseVo;
import com.leetcode.leetcode_ai.vo.JudgeQueueStatsVo;
import com.leetcode.leetcode_ai.service.JudgeQueueService;
import com.leetcode.leetcode_ai.service.JudgeCallbackService;
import com.leetcode.leetcode_ai.service.JudgeTriggerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.leetcode.leetcode_ai.common.exception.BizException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/internal/judge")
@RequiredArgsConstructor
public class JudgeController {

    private final JudgeCallbackService judgeCallbackService;
    private final JudgeTriggerService judgeTriggerService;
    private final JudgeQueueService judgeQueueService;
    private final InternalApiProperties internalApiProperties;

    @PostMapping("/callback")
    public ApiResponse<Void> callback(@RequestHeader(value = "X-Internal-Token", required = false) String token,
                                      @Valid @RequestBody JudgeCallbackRequestDto request) {
        validateInternalToken(token);
        judgeCallbackService.handleCallback(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/trigger/{submissionId}")
    public ApiResponse<JudgeTriggerResponseVo> trigger(@RequestHeader(value = "X-Internal-Token", required = false) String token,
                                                     @PathVariable Long submissionId) {
        validateInternalToken(token);
        return ApiResponse.success(judgeTriggerService.trigger(submissionId));
    }

    @GetMapping("/queue/stats")
    public ApiResponse<JudgeQueueStatsVo> queueStats(@RequestHeader(value = "X-Internal-Token", required = false) String token) {
        validateInternalToken(token);
        return ApiResponse.success(judgeQueueService.stats());
    }

    private void validateInternalToken(String token) {
        if (!internalApiProperties.getJudgeCallbackToken().equals(token)) {
            throw new BizException(401, "Invalid internal token");
        }
    }
}
