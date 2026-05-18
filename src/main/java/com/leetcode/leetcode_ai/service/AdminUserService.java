package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.vo.AdminUserPageVo;

public interface AdminUserService {
    AdminUserPageVo list(int page, int size, String keyword, Integer status);

    void updateStatus(Long userId, Integer status);
}
