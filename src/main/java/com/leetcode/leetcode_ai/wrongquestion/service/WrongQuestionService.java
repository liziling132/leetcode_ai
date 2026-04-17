package com.leetcode.leetcode_ai.wrongquestion.service;

import com.leetcode.leetcode_ai.wrongquestion.dto.WrongQuestionPageResponse;

public interface WrongQuestionService {

    WrongQuestionPageResponse list(int page, int size);
}
