package com.leetcode.leetcode_ai.recommendation.mapper;

import com.leetcode.leetcode_ai.problem.entity.Problem;
import com.leetcode.leetcode_ai.recommendation.mapper.row.WrongSeedRow;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RecommendationMapper {

    List<WrongSeedRow> findWrongSeeds(@Param("userId") Long userId,
                                      @Param("limit") int limit);

    List<Long> findTagIdsByProblemIds(@Param("problemIds") List<Long> problemIds);

    List<Problem> findCandidatesByTags(@Param("userId") Long userId,
                                       @Param("tagIds") List<Long> tagIds,
                                       @Param("excludeProblemIds") List<Long> excludeProblemIds,
                                       @Param("limit") int limit);

    List<Problem> findCandidatesByDifficulty(@Param("userId") Long userId,
                                             @Param("difficulty") String difficulty,
                                             @Param("excludeProblemIds") List<Long> excludeProblemIds,
                                             @Param("limit") int limit);

    int insertRecommendationRecord(@Param("userId") Long userId,
                                   @Param("triggerSource") String triggerSource,
                                   @Param("triggerRefId") Long triggerRefId,
                                   @Param("resourceType") String resourceType,
                                   @Param("resourceId") String resourceId,
                                   @Param("problemId") Long problemId,
                                   @Param("reasonText") String reasonText,
                                   @Param("score") BigDecimal score);
}
