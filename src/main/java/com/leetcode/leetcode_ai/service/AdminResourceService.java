package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.dto.AdminResourceUpsertRequestDto;
import com.leetcode.leetcode_ai.vo.AdminResourceItemVo;
import com.leetcode.leetcode_ai.vo.AdminResourcePageVo;

public interface AdminResourceService {
    AdminResourcePageVo list(int page, int size, String keyword, String resourceType, Integer status);

    AdminResourceItemVo detail(Long id);

    Long create(AdminResourceUpsertRequestDto request);

    void update(Long id, AdminResourceUpsertRequestDto request);

    void delete(Long id);
}
