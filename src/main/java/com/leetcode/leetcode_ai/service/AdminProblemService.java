package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.dto.AdminProblemUpsertRequestDto;
import com.leetcode.leetcode_ai.dto.AdminTestcaseBatchUpsertRequestDto;
import com.leetcode.leetcode_ai.vo.AdminProblemDetailVo;
import com.leetcode.leetcode_ai.vo.AdminProblemPageVo;

public interface AdminProblemService {

    AdminProblemPageVo list(int page, int size, String keyword, String difficulty, Integer status, Long tagId);

    AdminProblemDetailVo detail(Long problemId);

    Long create(AdminProblemUpsertRequestDto request);

    void update(Long problemId, AdminProblemUpsertRequestDto request);

    void delete(Long problemId);

    void replaceTestcases(Long problemId, AdminTestcaseBatchUpsertRequestDto request);
}
