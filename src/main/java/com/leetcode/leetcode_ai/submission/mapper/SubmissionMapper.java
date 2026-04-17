package com.leetcode.leetcode_ai.submission.mapper;

import com.leetcode.leetcode_ai.submission.entity.Submission;
import org.apache.ibatis.annotations.Param;

public interface SubmissionMapper {

    int insert(Submission submission);

    Submission findById(@Param("id") Long id);

    int markJudging(@Param("id") Long id);

    int updateJudgeResult(@Param("id") Long id,
                          @Param("judgeStatus") String judgeStatus,
                          @Param("compileLog") String compileLog,
                          @Param("runTimeMs") Integer runTimeMs,
                          @Param("memoryKb") Integer memoryKb,
                          @Param("passedCaseCount") Integer passedCaseCount,
                          @Param("totalCaseCount") Integer totalCaseCount,
                          @Param("judgeTraceId") String judgeTraceId);
}
