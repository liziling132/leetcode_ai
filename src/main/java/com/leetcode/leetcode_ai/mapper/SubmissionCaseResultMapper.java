package com.leetcode.leetcode_ai.mapper;

import com.leetcode.leetcode_ai.entity.SubmissionCaseResultEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubmissionCaseResultMapper {

    int deleteBySubmissionId(@Param("submissionId") Long submissionId);

    int insert(SubmissionCaseResultEntity submissionCaseResultEntity);

    long countBySubmissionId(@Param("submissionId") Long submissionId,
                             @Param("onlyFailed") boolean onlyFailed);

    List<SubmissionCaseResultEntity> findPageBySubmissionId(@Param("submissionId") Long submissionId,
                                                            @Param("onlyFailed") boolean onlyFailed,
                                                            @Param("offset") int offset,
                                                            @Param("size") int size);
}
