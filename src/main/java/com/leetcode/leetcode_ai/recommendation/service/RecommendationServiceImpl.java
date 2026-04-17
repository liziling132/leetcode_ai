package com.leetcode.leetcode_ai.recommendation.service;

import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.problem.entity.Problem;
import com.leetcode.leetcode_ai.recommendation.dto.RecommendationItemResponse;
import com.leetcode.leetcode_ai.recommendation.dto.RecommendationResponse;
import com.leetcode.leetcode_ai.recommendation.mapper.RecommendationMapper;
import com.leetcode.leetcode_ai.recommendation.mapper.row.WrongSeedRow;
import com.leetcode.leetcode_ai.security.JwtUserPrincipal;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private static final int MAX_SIZE = 20;
    private static final int DEFAULT_SEED_LIMIT = 5;

    private final RecommendationMapper recommendationMapper;

    @Override
    public RecommendationResponse recommend(int size) {
        if (size < 1 || size > MAX_SIZE) {
            throw new BizException(400, "Invalid size");
        }
        Long userId = currentUserId();
        List<WrongSeedRow> seeds = recommendationMapper.findWrongSeeds(userId, DEFAULT_SEED_LIMIT);
        if (seeds.isEmpty()) {
            return new RecommendationResponse(size, Collections.emptyList());
        }

        List<Long> wrongProblemIds = seeds.stream().map(WrongSeedRow::getProblemId).toList();
        Set<Long> tagIds = new LinkedHashSet<>(recommendationMapper.findTagIdsByProblemIds(wrongProblemIds));

        List<Problem> candidates = new ArrayList<>();
        if (!tagIds.isEmpty()) {
            candidates.addAll(recommendationMapper.findCandidatesByTags(userId, new ArrayList<>(tagIds), wrongProblemIds, size));
        }

        if (candidates.size() < size) {
            String fallbackDifficulty = seeds.get(0).getDifficulty();
            List<Problem> fallback = recommendationMapper.findCandidatesByDifficulty(
                    userId, fallbackDifficulty, wrongProblemIds, size - candidates.size());
            Set<Long> exists = candidates.stream().map(Problem::getId).collect(LinkedHashSet::new, Set::add, Set::addAll);
            for (Problem problem : fallback) {
                if (!exists.contains(problem.getId())) {
                    candidates.add(problem);
                }
                if (candidates.size() >= size) {
                    break;
                }
            }
        }

        List<RecommendationItemResponse> result = new ArrayList<>();
        for (int i = 0; i < candidates.size() && i < size; i++) {
            Problem problem = candidates.get(i);
            double rawScore = Math.max(0.5, 1.0 - i * 0.1);
            BigDecimal score = BigDecimal.valueOf(rawScore).setScale(2, RoundingMode.HALF_UP);
            String reason = "Based on your recent wrong questions, this problem helps reinforce similar knowledge points.";

            recommendationMapper.insertRecommendationRecord(
                    userId,
                    "WRONG_QUESTION",
                    seeds.get(0).getProblemId(),
                    "PROBLEM",
                    String.valueOf(problem.getId()),
                    problem.getId(),
                    reason,
                    score
            );

            RecommendationItemResponse item = new RecommendationItemResponse();
            item.setProblemId(problem.getId());
            item.setTitle(problem.getTitle());
            item.setDifficulty(problem.getDifficulty());
            item.setReason(reason);
            item.setScore(score.doubleValue());
            result.add(item);
        }

        return new RecommendationResponse(size, result);
    }

    private Long currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof JwtUserPrincipal principal)) {
            throw new BizException(401, "Unauthorized");
        }
        return principal.userId();
    }
}
