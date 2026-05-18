package com.leetcode.leetcode_ai.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.vo.ProblemDetailResponseVo;
import com.leetcode.leetcode_ai.vo.ProblemListItemResponseVo;
import com.leetcode.leetcode_ai.vo.ProblemPageResponseVo;
import com.leetcode.leetcode_ai.entity.ProblemEntity;
import com.leetcode.leetcode_ai.mapper.ProblemMapper;
import com.leetcode.leetcode_ai.mapper.row.ProblemTagRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    private static final int MAX_PAGE_SIZE = 100;

    private final ProblemMapper problemMapper;
    private final ObjectMapper objectMapper;

    @Override
    public ProblemPageResponseVo list(int page, int size, String keyword, String difficulty, Long tagId, String language) {
        if (page < 1 || size < 1 || size > MAX_PAGE_SIZE) {
            throw new BizException(400, "Invalid pagination params");
        }
        String normalizedLanguage = normalizeLanguage(language);
        int offset = (page - 1) * size;
        long total = problemMapper.countByCondition(keyword, difficulty, tagId, normalizedLanguage);
        List<ProblemEntity> problems = problemMapper.findPageByCondition(keyword, difficulty, tagId, normalizedLanguage, offset, size);
        if (problems.isEmpty()) {
            return new ProblemPageResponseVo(total, page, size, Collections.emptyList());
        }

        List<Long> problemIds = problems.stream().map(ProblemEntity::getId).toList();
        Map<Long, List<String>> tagsMap = toTagMap(problemMapper.findTagsByProblemIds(problemIds));
        List<ProblemListItemResponseVo> list = new ArrayList<>(problems.size());
        for (ProblemEntity ProblemEntity : problems) {
            ProblemListItemResponseVo item = new ProblemListItemResponseVo();
            item.setId(ProblemEntity.getId());
            item.setTitle(ProblemEntity.getTitle());
            item.setDifficulty(ProblemEntity.getDifficulty());
            item.setSupportLanguages(parseStringArray(ProblemEntity.getSupportLanguages()));
            item.setTags(tagsMap.getOrDefault(ProblemEntity.getId(), Collections.emptyList()));
            list.add(item);
        }
        return new ProblemPageResponseVo(total, page, size, list);
    }

    @Override
    public ProblemDetailResponseVo detail(Long id) {
        ProblemEntity ProblemEntity = problemMapper.findOnlineById(id);
        if (ProblemEntity == null) {
            throw new BizException(404, "ProblemEntity not found");
        }

        ProblemDetailResponseVo response = new ProblemDetailResponseVo();
        response.setId(ProblemEntity.getId());
        response.setTitle(ProblemEntity.getTitle());
        response.setContent(ProblemEntity.getContent());
        response.setInputDesc(ProblemEntity.getInputDesc());
        response.setOutputDesc(ProblemEntity.getOutputDesc());
        response.setDifficulty(ProblemEntity.getDifficulty());
        response.setKnowledgePoints(parseStringArray(ProblemEntity.getKnowledgePoints()));
        response.setSupportLanguages(parseStringArray(ProblemEntity.getSupportLanguages()));
        response.setTags(problemMapper.findTagNamesByProblemId(ProblemEntity.getId()));
        return response;
    }

    private Map<Long, List<String>> toTagMap(List<ProblemTagRow> rows) {
        Map<Long, List<String>> map = new HashMap<>();
        for (ProblemTagRow row : rows) {
            if (!StringUtils.hasText(row.getTagName())) {
                continue;
            }
            map.computeIfAbsent(row.getProblemId(), key -> new ArrayList<>()).add(row.getTagName());
        }
        return map;
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

    private String normalizeLanguage(String language) {
        if (!StringUtils.hasText(language)) {
            return null;
        }
        return language.trim().toLowerCase(Locale.ROOT);
    }
}
