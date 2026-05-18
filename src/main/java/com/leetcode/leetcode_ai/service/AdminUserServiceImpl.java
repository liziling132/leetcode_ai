package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.mapper.AdminUserMapper;
import com.leetcode.leetcode_ai.vo.AdminUserPageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private static final int MAX_PAGE_SIZE = 100;
    private final AdminUserMapper adminUserMapper;

    @Override
    public AdminUserPageVo list(int page, int size, String keyword, Integer status) {
        if (page < 1 || size < 1 || size > MAX_PAGE_SIZE) {
            throw new BizException(400, "Invalid pagination params");
        }
        if (status != null && status != 0 && status != 1) {
            throw new BizException(400, "status must be 0 or 1");
        }
        String normalizedKeyword = StringUtils.hasText(keyword) ? keyword.trim() : null;
        long total = adminUserMapper.countByCondition(normalizedKeyword, status);
        if (total == 0) {
            return new AdminUserPageVo(0, page, size, List.of());
        }
        int offset = (page - 1) * size;
        return new AdminUserPageVo(total, page, size, adminUserMapper.findPageByCondition(normalizedKeyword, status, offset, size));
    }

    @Override
    public void updateStatus(Long userId, Integer status) {
        if (userId == null || userId <= 0) {
            throw new BizException(400, "Invalid user id");
        }
        if (status == null || (status != 0 && status != 1)) {
            throw new BizException(400, "status must be 0 or 1");
        }
        int updated = adminUserMapper.updateStatus(userId, status);
        if (updated == 0) {
            throw new BizException(404, "User not found");
        }
    }
}
