package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.dto.AdminTagUpsertRequestDto;
import com.leetcode.leetcode_ai.vo.AdminTagPageVo;

public interface AdminTagService {

    AdminTagPageVo list(int page, int size, String keyword, String tagType);

    Long create(AdminTagUpsertRequestDto request);

    void update(Long tagId, AdminTagUpsertRequestDto request);

    void delete(Long tagId, boolean force);
}
