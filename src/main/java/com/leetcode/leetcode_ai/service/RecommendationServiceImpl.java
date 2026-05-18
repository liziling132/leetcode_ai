package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.entity.AdminResourceEntity;
import com.leetcode.leetcode_ai.entity.ProblemEntity;
import com.leetcode.leetcode_ai.vo.RecommendationItemResponseVo;
import com.leetcode.leetcode_ai.vo.RecommendationRecordPageVo;
import com.leetcode.leetcode_ai.vo.RecommendationResponseVo;
import com.leetcode.leetcode_ai.vo.RecommendationRecordItemVo;
import com.leetcode.leetcode_ai.mapper.RecommendationMapper;
import com.leetcode.leetcode_ai.mapper.row.WrongSeedRow;
import com.leetcode.leetcode_ai.security.JwtUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private static final int MAX_SIZE = 20;
    private static final int DEFAULT_SEED_LIMIT = 5;
    private static final int MAX_PAGE_SIZE = 100;
    private static final int MAX_RECORDS_PER_USER = 30;

    private final RecommendationMapper recommendationMapper;
    private final AiTextService aiTextService;

    @Override
    public RecommendationResponseVo recommend(int size) {
        if (size < 1 || size > MAX_SIZE) {
            throw new BizException(400, "Invalid size");
        }
        Long userId = currentUserId();
        List<RecommendationItemResponseVo> result = buildAndPersistRecommendations(userId, null, size);
        return new RecommendationResponseVo(size, result);
    }

    @Override
    public int generateForWrongQuestion(Long userId, Long problemId, int size) {
        if (userId == null || userId <= 0 || problemId == null || problemId <= 0) {
            return 0;
        }
        int normalizedSize = size < 1 ? 1 : Math.min(size, MAX_SIZE);
        List<RecommendationItemResponseVo> result = buildAndPersistRecommendations(userId, problemId, normalizedSize);
        return result.size();
    }

    @Override
    public RecommendationRecordPageVo records(int page, int size) {
        if (page < 1 || size < 1 || size > MAX_PAGE_SIZE) {
            throw new BizException(400, "Invalid pagination params");
        }
        Long userId = currentUserId();
        long total = recommendationMapper.countRecordsByUserId(userId);
        if (total == 0) {
            return new RecommendationRecordPageVo(0, page, size, Collections.emptyList());
        }
        int offset = (page - 1) * size;
        List<RecommendationRecordItemVo> list = recommendationMapper.findRecordPageByUserId(userId, offset, size);
        return new RecommendationRecordPageVo(total, page, size, list);
    }

    private List<RecommendationItemResponseVo> buildAndPersistRecommendations(Long userId, Long triggerProblemId, int size) {
        List<WrongSeedRow> seeds = recommendationMapper.findWrongSeeds(userId, DEFAULT_SEED_LIMIT);
        if (seeds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> wrongProblemIds = seeds.stream().map(WrongSeedRow::getProblemId).toList();
        Set<Long> tagIds = new LinkedHashSet<>(recommendationMapper.findTagIdsByProblemIds(wrongProblemIds));

        List<ProblemEntity> candidates = new ArrayList<>();
        if (!tagIds.isEmpty()) {
            candidates.addAll(recommendationMapper.findCandidatesByTags(userId, new ArrayList<>(tagIds), wrongProblemIds, size));
        }

        if (candidates.size() < size) {
            String fallbackDifficulty = seeds.get(0).getDifficulty();
            List<ProblemEntity> fallback = recommendationMapper.findCandidatesByDifficulty(
                    userId, fallbackDifficulty, wrongProblemIds, size - candidates.size());
            Set<Long> exists = candidates.stream().map(ProblemEntity::getId).collect(LinkedHashSet::new, Set::add, Set::addAll);
            for (ProblemEntity ProblemEntity : fallback) {
                if (!exists.contains(ProblemEntity.getId())) {
                    candidates.add(ProblemEntity);
                }
                if (candidates.size() >= size) {
                    break;
                }
            }
        }

        List<RecommendationItemResponseVo> result = new ArrayList<>();
        for (int i = 0; i < candidates.size() && i < size; i++) {
            ProblemEntity ProblemEntity = candidates.get(i);
            double rawScore = Math.max(0.5, 1.0 - i * 0.1);
            BigDecimal score = BigDecimal.valueOf(rawScore).setScale(2, RoundingMode.HALF_UP);
            String wrongContext = buildWrongContext(seeds);
            ReasonPayload reasonPayload = buildReason(wrongContext, ProblemEntity.getTitle(), ProblemEntity.getDifficulty());
            Long refId = triggerProblemId == null ? seeds.get(0).getProblemId() : triggerProblemId;
            AdminResourceEntity linkedResource = findLinkedResource(ProblemEntity.getKnowledgePoints());

            recommendationMapper.insertRecommendationRecord(
                    userId,
                    "WRONG_QUESTION",
                    refId,
                    linkedResource == null ? "PROBLEM" : linkedResource.getResourceType(),
                    linkedResource == null ? String.valueOf(ProblemEntity.getId()) : String.valueOf(linkedResource.getId()),
                    ProblemEntity.getId(),
                    reasonPayload.reasonText(),
                    reasonPayload.aiSource(),
                    reasonPayload.aiModel(),
                    score
            );

            RecommendationItemResponseVo item = new RecommendationItemResponseVo();
            item.setProblemId(ProblemEntity.getId());
            item.setTitle(ProblemEntity.getTitle());
            item.setDifficulty(ProblemEntity.getDifficulty());
            item.setReason(reasonPayload.reasonText());
            item.setScore(score.doubleValue());
            if (linkedResource != null) {
                item.setResourceType(linkedResource.getResourceType());
                item.setResourceTitle(linkedResource.getTitle());
                item.setResourceUrl(linkedResource.getUrl());
            }
            result.add(item);
        }
        try {
            recommendationMapper.pruneUserRecordsKeepLatest(userId, MAX_RECORDS_PER_USER);
        } catch (Exception e) {
            log.warn("Failed to prune recommendation records, userId={}", userId, e);
        }
        return result;
    }

    private ReasonPayload buildReason(String wrongContext, String title, String difficulty) {
        AiTextResult aiResult = aiTextService.recommendReason(wrongContext, title, difficulty);
        if (aiResult != null && aiResult.content() != null && !aiResult.content().isBlank()) {
            String model = aiResult.model() == null || aiResult.model().isBlank() ? "deepseek-chat" : aiResult.model().trim();
            return new ReasonPayload(trimToLength(aiResult.content(), 200), "AI", model);
        }
        return new ReasonPayload(
                "Based on your recent wrong questions, this problem helps reinforce related knowledge points.",
                "RULE",
                "rule-mvp-v1"
        );
    }

    private String buildWrongContext(List<WrongSeedRow> seeds) {
        if (seeds == null || seeds.isEmpty()) {
            return "recent wrong questions";
        }
        WrongSeedRow top = seeds.get(0);
        return "difficulty=" + top.getDifficulty() + ", wrongType=" + top.getLastWrongType() + ", wrongCount=" + top.getWrongCount();
    }

    private String trimToLength(String text, int maxLength) {
        String normalized = text.trim();
        return normalized.length() <= maxLength ? normalized : normalized.substring(0, maxLength);
    }

    private AdminResourceEntity findLinkedResource(String knowledgePointsJson) {
        if (knowledgePointsJson == null || knowledgePointsJson.isBlank()) {
            return null;
        }
        try {
            return recommendationMapper.findBestResourceByKnowledgePoints(knowledgePointsJson);
        } catch (Exception e) {
            log.warn("Find linked resource failed, knowledgePoints={}", knowledgePointsJson, e);
            return null;
        }
    }

    private record ReasonPayload(String reasonText, String aiSource, String aiModel) {
    }

    private Long currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof JwtUserPrincipal principal)) {
            throw new BizException(401, "Unauthorized");
        }
        return principal.userId();
    }
}
