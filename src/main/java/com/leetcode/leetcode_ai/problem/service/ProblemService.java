package com.leetcode.leetcode_ai.problem.service;

import com.leetcode.leetcode_ai.problem.dto.ProblemDetailResponse;
import com.leetcode.leetcode_ai.problem.dto.ProblemPageResponse;

public interface ProblemService {

    ProblemPageResponse list(int page, int size, String keyword, String difficulty, Long tagId);

    ProblemDetailResponse detail(Long id);
}
