package com.leetcode.leetcode_ai.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.entity.ProblemEntity;
import com.leetcode.leetcode_ai.mapper.FavoriteMapper;
import com.leetcode.leetcode_ai.mapper.ProblemMapper;
import com.leetcode.leetcode_ai.security.JwtUserPrincipal;
import com.leetcode.leetcode_ai.vo.FavoriteItemVo;
import com.leetcode.leetcode_ai.vo.FavoritePageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private static final int MAX_PAGE_SIZE = 100;

    private final FavoriteMapper favoriteMapper;
    private final ProblemMapper problemMapper;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void favorite(Long problemId) {
        if (problemId == null || problemId <= 0) {
            throw new BizException(400, "Invalid problemId");
        }
        ProblemEntity problemEntity = problemMapper.findOnlineById(problemId);
        if (problemEntity == null) {
            throw new BizException(404, "ProblemEntity not found");
        }
        favoriteMapper.insertIgnore(currentUserId(), problemId);
    }

    @Override
    @Transactional
    public void unfavorite(Long problemId) {
        if (problemId == null || problemId <= 0) {
            throw new BizException(400, "Invalid problemId");
        }
        favoriteMapper.deleteByUserAndProblem(currentUserId(), problemId);
    }

    @Override
    public FavoritePageVo myFavorites(int page, int size) {
        if (page < 1 || size < 1 || size > MAX_PAGE_SIZE) {
            throw new BizException(400, "Invalid pagination params");
        }
        Long userId = currentUserId();
        long total = favoriteMapper.countByUserId(userId);
        if (total == 0) {
            return new FavoritePageVo(0, page, size, List.of());
        }
        int offset = (page - 1) * size;
        List<FavoriteItemVo> list = favoriteMapper.findPageByUserId(userId, offset, size);
        for (FavoriteItemVo item : list) {
            item.setSupportLanguages(parseStringArray(item.getSupportLanguagesJson()));
            item.setSupportLanguagesJson(null);
        }
        return new FavoritePageVo(total, page, size, list);
    }

    private List<String> parseStringArray(String value) {
        if (!StringUtils.hasText(value)) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(value, new TypeReference<List<String>>() {
            });
        } catch (Exception ignored) {
            return List.of(value);
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
