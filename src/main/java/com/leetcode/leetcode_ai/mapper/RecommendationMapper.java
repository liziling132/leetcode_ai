package com.leetcode.leetcode_ai.mapper;

import com.leetcode.leetcode_ai.entity.ProblemEntity;
import com.leetcode.leetcode_ai.entity.AdminResourceEntity;
import com.leetcode.leetcode_ai.mapper.row.WrongSeedRow;
import com.leetcode.leetcode_ai.vo.RecommendationRecordItemVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RecommendationMapper {

    List<WrongSeedRow> findWrongSeeds(@Param("userId") Long userId,
                                      @Param("limit") int limit);

    List<Long> findTagIdsByProblemIds(@Param("problemIds") List<Long> problemIds);

    List<ProblemEntity> findCandidatesByTags(@Param("userId") Long userId,
                                             @Param("tagIds") List<Long> tagIds,
                                             @Param("excludeProblemIds") List<Long> excludeProblemIds,
                                             @Param("limit") int limit);

    List<ProblemEntity> findCandidatesByDifficulty(@Param("userId") Long userId,
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
                                   @Param("aiSource") String aiSource,
                                   @Param("aiModel") String aiModel,
                                   @Param("score") BigDecimal score);

    AdminResourceEntity findBestResourceByKnowledgePoints(@Param("knowledgePointsJson") String knowledgePointsJson);

    long countRecordsByUserId(@Param("userId") Long userId);

    List<RecommendationRecordItemVo> findRecordPageByUserId(@Param("userId") Long userId,
                                                            @Param("offset") int offset,
                                                            @Param("size") int size);

    int pruneUserRecordsKeepLatest(@Param("userId") Long userId,
                                   @Param("keep") int keep);
}
