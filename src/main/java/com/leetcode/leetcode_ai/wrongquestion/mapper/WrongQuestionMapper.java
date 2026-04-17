package com.leetcode.leetcode_ai.wrongquestion.mapper;

import com.leetcode.leetcode_ai.wrongquestion.dto.WrongQuestionItemResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WrongQuestionMapper {

    int upsertWrongQuestion(@Param("userId") Long userId,
                            @Param("problemId") Long problemId,
                            @Param("submissionId") Long submissionId,
                            @Param("wrongType") String wrongType);

    long countByUserId(@Param("userId") Long userId);

    List<WrongQuestionItemResponse> findPageByUserId(@Param("userId") Long userId,
                                                     @Param("offset") int offset,
                                                     @Param("size") int size);
}
