package com.leetcode.leetcode_ai.mapper;

import com.leetcode.leetcode_ai.vo.WrongQuestionItemResponseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WrongQuestionMapper {

    int upsertWrongQuestion(@Param("userId") Long userId,
                            @Param("problemId") Long problemId,
                            @Param("submissionId") Long submissionId,
                            @Param("wrongType") String wrongType);

    int updateAiSummary(@Param("userId") Long userId,
                        @Param("problemId") Long problemId,
                        @Param("aiSummary") String aiSummary,
                        @Param("aiModel") String aiModel);

    long countByUserId(@Param("userId") Long userId);

    List<WrongQuestionItemResponseVo> findPageByUserId(@Param("userId") Long userId,
                                                     @Param("offset") int offset,
                                                     @Param("size") int size);
}
