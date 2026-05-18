package com.leetcode.leetcode_ai.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.dto.AdminResourceUpsertRequestDto;
import com.leetcode.leetcode_ai.entity.AdminResourceEntity;
import com.leetcode.leetcode_ai.mapper.AdminResourceMapper;
import com.leetcode.leetcode_ai.security.JwtUserPrincipal;
import com.leetcode.leetcode_ai.vo.AdminResourceItemVo;
import com.leetcode.leetcode_ai.vo.AdminResourcePageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminResourceServiceImpl implements AdminResourceService {

    private static final int MAX_PAGE_SIZE = 100;
    private static final Set<String> ALLOWED_RESOURCE_TYPES = Set.of("ARTICLE", "VIDEO", "PROBLEM");
    private final AdminResourceMapper adminResourceMapper;
    private final ObjectMapper objectMapper;

    @Override
    public AdminResourcePageVo list(int page, int size, String keyword, String resourceType, Integer status) {
        if (page < 1 || size < 1 || size > MAX_PAGE_SIZE) {
            throw new BizException(400, "Invalid pagination params");
        }
        String normalizedType = normalizeOptionalType(resourceType);
        Integer normalizedStatus = normalizeStatus(status, true);
        String normalizedKeyword = StringUtils.hasText(keyword) ? keyword.trim() : null;
        long total = adminResourceMapper.countByCondition(normalizedKeyword, normalizedType, normalizedStatus);
        if (total == 0) {
            return new AdminResourcePageVo(0, page, size, List.of());
        }
        int offset = (page - 1) * size;
        List<AdminResourceEntity> rows = adminResourceMapper.findPageByCondition(normalizedKeyword, normalizedType, normalizedStatus, offset, size);
        List<AdminResourceItemVo> list = rows.stream().map(this::toVo).toList();
        return new AdminResourcePageVo(total, page, size, list);
    }

    @Override
    public AdminResourceItemVo detail(Long id) {
        AdminResourceEntity entity = adminResourceMapper.findById(id);
        if (entity == null) {
            throw new BizException(404, "Resource not found");
        }
        return toVo(entity);
    }

    @Override
    public Long create(AdminResourceUpsertRequestDto request) {
        AdminResourceEntity entity = buildEntity(request);
        Long operatorId = currentUserId();
        entity.setCreatedBy(operatorId);
        entity.setUpdatedBy(operatorId);
        adminResourceMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void update(Long id, AdminResourceUpsertRequestDto request) {
        AdminResourceEntity old = adminResourceMapper.findById(id);
        if (old == null) {
            throw new BizException(404, "Resource not found");
        }
        AdminResourceEntity entity = buildEntity(request);
        entity.setId(id);
        entity.setUpdatedBy(currentUserId());
        int updated = adminResourceMapper.updateById(entity);
        if (updated == 0) {
            throw new BizException(409, "Resource update failed");
        }
    }

    @Override
    public void delete(Long id) {
        int deleted = adminResourceMapper.deleteById(id);
        if (deleted == 0) {
            throw new BizException(404, "Resource not found");
        }
    }

    private AdminResourceEntity buildEntity(AdminResourceUpsertRequestDto request) {
        AdminResourceEntity entity = new AdminResourceEntity();
        entity.setResourceType(normalizeRequiredType(request.getResourceType()));
        entity.setTitle(request.getTitle().trim());
        entity.setUrl(request.getUrl().trim());
        entity.setKnowledgePoints(writeJsonList(request.getKnowledgePoints()));
        entity.setStatus(normalizeStatus(request.getStatus(), false));
        return entity;
    }

    private AdminResourceItemVo toVo(AdminResourceEntity entity) {
        AdminResourceItemVo vo = new AdminResourceItemVo();
        vo.setId(entity.getId());
        vo.setResourceType(entity.getResourceType());
        vo.setTitle(entity.getTitle());
        vo.setUrl(entity.getUrl());
        vo.setKnowledgePoints(readJsonList(entity.getKnowledgePoints()));
        vo.setStatus(entity.getStatus());
        vo.setCreatedAt(entity.getCreatedAt());
        vo.setUpdatedAt(entity.getUpdatedAt());
        return vo;
    }

    private String normalizeRequiredType(String resourceType) {
        if (!StringUtils.hasText(resourceType)) {
            throw new BizException(400, "resourceType is required");
        }
        String normalized = resourceType.trim().toUpperCase(Locale.ROOT);
        if (!ALLOWED_RESOURCE_TYPES.contains(normalized)) {
            throw new BizException(400, "resourceType must be ARTICLE, VIDEO or PROBLEM");
        }
        return normalized;
    }

    private String normalizeOptionalType(String resourceType) {
        if (!StringUtils.hasText(resourceType)) {
            return null;
        }
        return normalizeRequiredType(resourceType);
    }

    private Integer normalizeStatus(Integer status, boolean allowNull) {
        if (status == null) {
            if (allowNull) {
                return null;
            }
            throw new BizException(400, "status is required");
        }
        if (status != 0 && status != 1) {
            throw new BizException(400, "status must be 0 or 1");
        }
        return status;
    }

    private String writeJsonList(List<String> source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(source.stream().filter(StringUtils::hasText).map(String::trim).toList());
        } catch (Exception ex) {
            throw new BizException(500, "knowledgePoints serialize failed");
        }
    }

    private List<String> readJsonList(String json) {
        if (!StringUtils.hasText(json)) {
            return List.of();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {
            });
        } catch (Exception ignored) {
            return List.of(json);
        }
    }

    private Long currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof JwtUserPrincipal principal)) {
            throw new BizException(401, "Unauthorized");
        }
        return principal.userId();
    }
}
