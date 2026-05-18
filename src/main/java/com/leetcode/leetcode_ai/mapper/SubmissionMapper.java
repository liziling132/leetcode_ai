package com.leetcode.leetcode_ai.mapper;

import com.leetcode.leetcode_ai.entity.SubmissionEntity;
import com.leetcode.leetcode_ai.vo.SubmissionListItemVo;
import com.leetcode.leetcode_ai.vo.SubmissionVersionItemVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SubmissionMapper {

    int insert(SubmissionEntity SubmissionEntity);

    SubmissionEntity findById(@Param("id") Long id);

    int markJudging(@Param("id") Long id);

    int updateJudgeResult(@Param("id") Long id,
                          @Param("judgeStatus") String judgeStatus,
                          @Param("compileLog") String compileLog,
                          @Param("runTimeMs") Integer runTimeMs,
                          @Param("memoryKb") Integer memoryKb,
                          @Param("passedCaseCount") Integer passedCaseCount,
                          @Param("totalCaseCount") Integer totalCaseCount,
                          @Param("judgeTraceId") String judgeTraceId);

    long countByUserAndCondition(@Param("userId") Long userId,
                                 @Param("problemId") Long problemId,
                                 @Param("judgeStatus") String judgeStatus,
                                 @Param("language") String language,
                                 @Param("submittedFrom") LocalDateTime submittedFrom,
                                 @Param("submittedTo") LocalDateTime submittedTo);

    List<SubmissionListItemVo> findPageByUserAndCondition(@Param("userId") Long userId,
                                                          @Param("problemId") Long problemId,
                                                          @Param("judgeStatus") String judgeStatus,
                                                          @Param("language") String language,
                                                          @Param("submittedFrom") LocalDateTime submittedFrom,
                                                          @Param("submittedTo") LocalDateTime submittedTo,
                                                          @Param("offset") int offset,
                                                          @Param("size") int size);

    long countByUserAndProblem(@Param("userId") Long userId,
                               @Param("problemId") Long problemId);

    List<SubmissionVersionItemVo> findVersionPageByUserAndProblem(@Param("userId") Long userId,
                                                                   @Param("problemId") Long problemId,
                                                                   @Param("offset") int offset,
                                                                   @Param("size") int size);
}
