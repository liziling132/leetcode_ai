package com.leetcode.leetcode_ai.wrongquestion.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.wrongquestion.dto.WrongQuestionPageResponse;
import com.leetcode.leetcode_ai.wrongquestion.service.WrongQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wrong-questions")
@RequiredArgsConstructor
public class WrongQuestionController {

    private final WrongQuestionService wrongQuestionService;

    @GetMapping
    public ApiResponse<WrongQuestionPageResponse> list(@RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.success(wrongQuestionService.list(page, size));
    }
}
