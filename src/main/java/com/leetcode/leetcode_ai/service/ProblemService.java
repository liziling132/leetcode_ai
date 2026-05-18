package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.vo.ProblemDetailResponseVo;
import com.leetcode.leetcode_ai.vo.ProblemPageResponseVo;

public interface ProblemService {

    ProblemPageResponseVo list(int page, int size, String keyword, String difficulty, Long tagId, String language);

    ProblemDetailResponseVo detail(Long id);
}
