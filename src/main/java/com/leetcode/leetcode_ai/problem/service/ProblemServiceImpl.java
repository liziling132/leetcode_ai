package com.leetcode.leetcode_ai.problem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.problem.dto.ProblemDetailResponse;
import com.leetcode.leetcode_ai.problem.dto.ProblemListItemResponse;
import com.leetcode.leetcode_ai.problem.dto.ProblemPageResponse;
import com.leetcode.leetcode_ai.problem.entity.Problem;
import com.leetcode.leetcode_ai.problem.mapper.ProblemMapper;
import com.leetcode.leetcode_ai.problem.mapper.row.ProblemTagRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    private static final int MAX_PAGE_SIZE = 100;

    private final ProblemMapper problemMapper;
    private final ObjectMapper objectMapper;

    @Override
    public ProblemPageResponse list(int page, int size, String keyword, String difficulty, Long tagId) {
        if (page < 1 || size < 1 || size > MAX_PAGE_SIZE) {
            throw new BizException(400, "Invalid pagination params");
        }
        int offset = (page - 1) * size;
        long total = problemMapper.countByCondition(keyword, difficulty, tagId);
        List<Problem> problems = problemMapper.findPageByCondition(keyword, difficulty, tagId, offset, size);
        if (problems.isEmpty()) {
            return new ProblemPageResponse(total, page, size, Collections.emptyList());
        }

        List<Long> problemIds = problems.stream().map(Problem::getId).toList();
        Map<Long, List<String>> tagsMap = toTagMap(problemMapper.findTagsByProblemIds(problemIds));
        List<ProblemListItemResponse> list = new ArrayList<>(problems.size());
        for (Problem problem : problems) {
            ProblemListItemResponse item = new ProblemListItemResponse();
            item.setId(problem.getId());
            item.setTitle(problem.getTitle());
            item.setDifficulty(problem.getDifficulty());
            item.setSupportLanguages(parseStringArray(problem.getSupportLanguages()));
            item.setTags(tagsMap.getOrDefault(problem.getId(), Collections.emptyList()));
            list.add(item);
        }
        return new ProblemPageResponse(total, page, size, list);
    }

    @Override
    public ProblemDetailResponse detail(Long id) {
        Problem problem = problemMapper.findOnlineById(id);
        if (problem == null) {
            throw new BizException(404, "Problem not found");
        }

        ProblemDetailResponse response = new ProblemDetailResponse();
        response.setId(problem.getId());
        response.setTitle(problem.getTitle());
        response.setContent(problem.getContent());
        response.setInputDesc(problem.getInputDesc());
        response.setOutputDesc(problem.getOutputDesc());
        response.setDifficulty(problem.getDifficulty());
        response.setKnowledgePoints(parseStringArray(problem.getKnowledgePoints()));
        response.setSupportLanguages(parseStringArray(problem.getSupportLanguages()));
        response.setTags(problemMapper.findTagNamesByProblemId(problem.getId()));
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
}
