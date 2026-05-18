package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.dto.AdminTagUpsertRequestDto;
import com.leetcode.leetcode_ai.entity.TagEntity;
import com.leetcode.leetcode_ai.mapper.AdminTagMapper;
import com.leetcode.leetcode_ai.vo.AdminTagPageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminTagServiceImpl implements AdminTagService {

    private static final int MAX_PAGE_SIZE = 100;

    private final AdminTagMapper adminTagMapper;

    @Override
    public AdminTagPageVo list(int page, int size, String keyword, String tagType) {
        if (page < 1 || size < 1 || size > MAX_PAGE_SIZE) {
            throw new BizException(400, "Invalid pagination params");
        }
        String normalizedKeyword = normalizeOptional(keyword);
        String normalizedTagType = normalizeOptional(tagType);
        long total = adminTagMapper.countByCondition(normalizedKeyword, normalizedTagType);
        if (total == 0) {
            return new AdminTagPageVo(0, page, size, List.of());
        }
        int offset = (page - 1) * size;
        return new AdminTagPageVo(
                total,
                page,
                size,
                adminTagMapper.findPageByCondition(normalizedKeyword, normalizedTagType, offset, size)
        );
    }

    @Override
    @Transactional
    public Long create(AdminTagUpsertRequestDto request) {
        String tagName = normalizeRequired(request.getTagName(), "tagName is required");
        String tagType = normalizeOptional(request.getTagType());
        TagEntity existing = adminTagMapper.findByName(tagName);
        if (existing != null) {
            throw new BizException(400, "Tag name already exists");
        }
        adminTagMapper.insert(tagName, tagType);
        TagEntity created = adminTagMapper.findByName(tagName);
        if (created == null) {
            throw new BizException(500, "Tag create failed");
        }
        return created.getId();
    }

    @Override
    @Transactional
    public void update(Long tagId, AdminTagUpsertRequestDto request) {
        TagEntity existing = adminTagMapper.findById(tagId);
        if (existing == null) {
            throw new BizException(404, "Tag not found");
        }
        String tagName = normalizeRequired(request.getTagName(), "tagName is required");
        String tagType = normalizeOptional(request.getTagType());
        TagEntity sameName = adminTagMapper.findByName(tagName);
        if (sameName != null && !sameName.getId().equals(tagId)) {
            throw new BizException(400, "Tag name already exists");
        }
        int updated = adminTagMapper.updateById(tagId, tagName, tagType);
        if (updated == 0) {
            throw new BizException(409, "Tag update failed");
        }
    }

    @Override
    @Transactional
    public void delete(Long tagId, boolean force) {
        TagEntity existing = adminTagMapper.findById(tagId);
        if (existing == null) {
            throw new BizException(404, "Tag not found");
        }
        long refCount = adminTagMapper.countProblemReferences(tagId);
        if (refCount > 0) {
            if (!force) {
                throw new BizException(400, "Tag is in use by problems");
            }
            adminTagMapper.deleteProblemTagsByTagId(tagId);
        }
        int deleted = adminTagMapper.deleteById(tagId);
        if (deleted == 0) {
            throw new BizException(409, "Tag delete failed");
        }
    }

    private String normalizeRequired(String value, String message) {
        if (!StringUtils.hasText(value)) {
            throw new BizException(400, message);
        }
        return value.trim();
    }

    private String normalizeOptional(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return value.trim();
    }
}
