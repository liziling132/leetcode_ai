package com.leetcode.leetcode_ai.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.dto.AdminProblemUpsertRequestDto;
import com.leetcode.leetcode_ai.dto.AdminTestcaseBatchUpsertRequestDto;
import com.leetcode.leetcode_ai.dto.AdminTestcaseItemDto;
import com.leetcode.leetcode_ai.entity.ProblemEntity;
import com.leetcode.leetcode_ai.mapper.AdminProblemMapper;
import com.leetcode.leetcode_ai.mapper.row.ProblemTagRow;
import com.leetcode.leetcode_ai.security.JwtUserPrincipal;
import com.leetcode.leetcode_ai.vo.AdminProblemDetailVo;
import com.leetcode.leetcode_ai.vo.AdminProblemListItemVo;
import com.leetcode.leetcode_ai.vo.AdminProblemPageVo;
import com.leetcode.leetcode_ai.vo.AdminProblemTestcaseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminProblemServiceImpl implements AdminProblemService {

    private static final Set<String> ALLOWED_DIFFICULTY = Set.of("EASY", "MEDIUM", "HARD");
    private static final Set<String> ALLOWED_CASE_TYPE = Set.of("SAMPLE", "HIDDEN");
    private static final int MAX_PAGE_SIZE = 100;

    private final AdminProblemMapper adminProblemMapper;
    private final ObjectMapper objectMapper;

    @Override
    public AdminProblemPageVo list(int page, int size, String keyword, String difficulty, Integer status, Long tagId) {
        if (page < 1 || size < 1 || size > MAX_PAGE_SIZE) {
            throw new BizException(400, "Invalid pagination params");
        }
        String normalizedKeyword = normalizeOptional(keyword);
        String normalizedDifficulty = normalizeOptionalDifficulty(difficulty);
        Integer normalizedStatus = normalizeOptionalStatus(status);
        Long normalizedTagId = normalizeOptionalTagId(tagId);
        long total = adminProblemMapper.countByCondition(normalizedKeyword, normalizedDifficulty, normalizedStatus, normalizedTagId);
        if (total == 0) {
            return new AdminProblemPageVo(0, page, size, List.of());
        }
        int offset = (page - 1) * size;
        List<AdminProblemListItemVo> list = adminProblemMapper.findPageByCondition(
                normalizedKeyword, normalizedDifficulty, normalizedStatus, normalizedTagId, offset, size
        );
        fillTagNames(list);
        return new AdminProblemPageVo(
                total,
                page,
                size,
                list
        );
    }

    @Override
    public AdminProblemDetailVo detail(Long problemId) {
        ProblemEntity existing = adminProblemMapper.findById(problemId);
        if (existing == null) {
            throw new BizException(404, "Problem not found");
        }
        AdminProblemDetailVo detail = new AdminProblemDetailVo();
        detail.setId(existing.getId());
        detail.setTitle(existing.getTitle());
        detail.setContent(existing.getContent());
        detail.setInputDesc(existing.getInputDesc());
        detail.setOutputDesc(existing.getOutputDesc());
        detail.setDifficulty(existing.getDifficulty());
        detail.setKnowledgePoints(parseJsonArray(existing.getKnowledgePoints()));
        detail.setSupportLanguages(parseJsonArray(existing.getSupportLanguages()));
        detail.setStatus(existing.getStatus());
        detail.setTagIds(adminProblemMapper.findTagIdsByProblemId(problemId));
        detail.setTagNames(adminProblemMapper.findTagNamesByProblemId(problemId));
        List<AdminProblemTestcaseVo> testcases = adminProblemMapper.findTestcasesByProblemId(problemId);
        detail.setTestcases(testcases == null ? List.of() : testcases);
        return detail;
    }

    @Override
    @Transactional
    public Long create(AdminProblemUpsertRequestDto request) {
        Long operatorId = currentUserId();
        List<Long> tagIds = normalizeTagIds(request.getTagIds());
        validateTagIds(tagIds);

        ProblemEntity problem = buildProblemEntity(request);
        adminProblemMapper.insertProblem(problem, operatorId, operatorId);
        bindProblemTags(problem.getId(), tagIds);
        return problem.getId();
    }

    @Override
    @Transactional
    public void update(Long problemId, AdminProblemUpsertRequestDto request) {
        ProblemEntity existing = adminProblemMapper.findById(problemId);
        if (existing == null) {
            throw new BizException(404, "Problem not found");
        }

        Long operatorId = currentUserId();
        List<Long> tagIds = normalizeTagIds(request.getTagIds());
        validateTagIds(tagIds);

        ProblemEntity problem = buildProblemEntity(request);
        problem.setId(problemId);
        adminProblemMapper.updateProblem(problem, operatorId);
        bindProblemTags(problemId, tagIds);
    }

    @Override
    @Transactional
    public void delete(Long problemId) {
        ProblemEntity existing = adminProblemMapper.findById(problemId);
        if (existing == null) {
            throw new BizException(404, "Problem not found");
        }
        int updated = adminProblemMapper.softDelete(problemId, currentUserId());
        if (updated == 0) {
            throw new BizException(409, "Problem delete failed");
        }
    }

    @Override
    @Transactional
    public void replaceTestcases(Long problemId, AdminTestcaseBatchUpsertRequestDto request) {
        ProblemEntity existing = adminProblemMapper.findById(problemId);
        if (existing == null) {
            throw new BizException(404, "Problem not found");
        }
        if (request.getTestcases() == null || request.getTestcases().isEmpty()) {
            throw new BizException(400, "testcases is required");
        }

        adminProblemMapper.deleteTestcases(problemId);
        for (int i = 0; i < request.getTestcases().size(); i++) {
            AdminTestcaseItemDto item = request.getTestcases().get(i);
            String caseType = normalizeCaseType(item.getCaseType());
            int score = item.getScore() == null || item.getScore() < 1 ? 1 : item.getScore();
            int sortOrder = item.getSortOrder() == null || item.getSortOrder() < 0 ? i : item.getSortOrder();
            int isActive = item.getIsActive() == null ? 1 : (item.getIsActive() == 0 ? 0 : 1);
            adminProblemMapper.insertTestcase(
                    problemId,
                    caseType,
                    defaultString(item.getInputData()),
                    defaultString(item.getOutputData()),
                    score,
                    sortOrder,
                    isActive
            );
        }
    }

    private ProblemEntity buildProblemEntity(AdminProblemUpsertRequestDto request) {
        String difficulty = normalizeDifficulty(request.getDifficulty());
        List<String> supportLanguages = normalizeStringList(request.getSupportLanguages(), true);
        List<String> knowledgePoints = normalizeStringList(request.getKnowledgePoints(), false);

        ProblemEntity problem = new ProblemEntity();
        problem.setTitle(request.getTitle().trim());
        problem.setContent(request.getContent().trim());
        problem.setInputDesc(defaultString(request.getInputDesc()));
        problem.setOutputDesc(defaultString(request.getOutputDesc()));
        problem.setDifficulty(difficulty);
        problem.setSupportLanguages(writeJson(supportLanguages));
        problem.setKnowledgePoints(knowledgePoints.isEmpty() ? null : writeJson(knowledgePoints));
        int status = request.getStatus() == null ? 1 : request.getStatus();
        if (status != -1 && status != 0 && status != 1) {
            throw new BizException(400, "status must be -1, 0 or 1");
        }
        problem.setStatus(status);
        return problem;
    }

    private void bindProblemTags(Long problemId, List<Long> tagIds) {
        adminProblemMapper.deleteProblemTags(problemId);
        for (Long tagId : tagIds) {
            adminProblemMapper.insertProblemTag(problemId, tagId);
        }
    }

    private void validateTagIds(List<Long> tagIds) {
        if (tagIds.isEmpty()) {
            return;
        }
        long existsCount = adminProblemMapper.countTagsByIds(tagIds);
        if (existsCount != tagIds.size()) {
            throw new BizException(400, "Invalid tag ids");
        }
    }

    private List<Long> normalizeTagIds(List<Long> tagIds) {
        if (tagIds == null) {
            throw new BizException(400, "tagIds is required");
        }
        Set<Long> unique = new LinkedHashSet<>();
        for (Long tagId : tagIds) {
            if (tagId == null || tagId <= 0) {
                throw new BizException(400, "Invalid tag id");
            }
            unique.add(tagId);
        }
        return new ArrayList<>(unique);
    }

    private String normalizeDifficulty(String difficulty) {
        if (!StringUtils.hasText(difficulty)) {
            throw new BizException(400, "difficulty is required");
        }
        String normalized = difficulty.trim().toUpperCase(Locale.ROOT);
        if (!ALLOWED_DIFFICULTY.contains(normalized)) {
            throw new BizException(400, "difficulty must be EASY, MEDIUM or HARD");
        }
        return normalized;
    }

    private String normalizeOptionalDifficulty(String difficulty) {
        if (!StringUtils.hasText(difficulty)) {
            return null;
        }
        return normalizeDifficulty(difficulty);
    }

    private Integer normalizeOptionalStatus(Integer status) {
        if (status == null) {
            return null;
        }
        if (status != -1 && status != 0 && status != 1) {
            throw new BizException(400, "status must be -1, 0 or 1");
        }
        return status;
    }

    private String normalizeOptional(String text) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        return text.trim();
    }

    private Long normalizeOptionalTagId(Long tagId) {
        if (tagId == null) {
            return null;
        }
        if (tagId <= 0) {
            throw new BizException(400, "Invalid tag id");
        }
        return tagId;
    }

    private String normalizeCaseType(String caseType) {
        if (!StringUtils.hasText(caseType)) {
            throw new BizException(400, "caseType is required");
        }
        String normalized = caseType.trim().toUpperCase(Locale.ROOT);
        if (!ALLOWED_CASE_TYPE.contains(normalized)) {
            throw new BizException(400, "caseType must be SAMPLE or HIDDEN");
        }
        return normalized;
    }

    private List<String> normalizeStringList(List<String> source, boolean required) {
        if (source == null || source.isEmpty()) {
            if (required) {
                throw new BizException(400, "required list is empty");
            }
            return List.of();
        }
        List<String> list = new ArrayList<>();
        for (String item : source) {
            if (!StringUtils.hasText(item)) {
                continue;
            }
            list.add(item.trim());
        }
        if (required && list.isEmpty()) {
            throw new BizException(400, "required list is empty");
        }
        return list;
    }

    private String writeJson(List<String> values) {
        try {
            return objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException ex) {
            throw new BizException(500, "JSON serialize failed");
        }
    }

    private List<String> parseJsonArray(String jsonValue) {
        if (!StringUtils.hasText(jsonValue)) {
            return List.of();
        }
        try {
            return objectMapper.readValue(jsonValue, new TypeReference<List<String>>() {
            });
        } catch (Exception ignored) {
            return List.of(jsonValue);
        }
    }

    private String defaultString(String text) {
        return text == null ? "" : text;
    }

    private void fillTagNames(List<AdminProblemListItemVo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Long> problemIds = list.stream().map(AdminProblemListItemVo::getId).toList();
        List<ProblemTagRow> rows = adminProblemMapper.findTagRowsByProblemIds(problemIds);
        Map<Long, List<String>> tagMap = new HashMap<>();
        for (ProblemTagRow row : rows) {
            tagMap.computeIfAbsent(row.getProblemId(), key -> new ArrayList<>()).add(row.getTagName());
        }
        for (AdminProblemListItemVo item : list) {
            item.setTagNames(tagMap.getOrDefault(item.getId(), List.of()));
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
