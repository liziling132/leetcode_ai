package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.vo.WrongQuestionPageResponseVo;

public interface WrongQuestionService {

    WrongQuestionPageResponseVo list(int page, int size);
}
