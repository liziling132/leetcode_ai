package com.leetcode.leetcode_ai.mapper;

import com.leetcode.leetcode_ai.entity.ProblemEntity;
import com.leetcode.leetcode_ai.mapper.row.ProblemTagRow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProblemMapper {

    long countByCondition(@Param("keyword") String keyword,
                          @Param("difficulty") String difficulty,
                          @Param("tagId") Long tagId,
                          @Param("language") String language);

    List<ProblemEntity> findPageByCondition(@Param("keyword") String keyword,
                                            @Param("difficulty") String difficulty,
                                            @Param("tagId") Long tagId,
                                            @Param("language") String language,
                                            @Param("offset") int offset,
                                            @Param("size") int size);

    List<ProblemTagRow> findTagsByProblemIds(@Param("problemIds") List<Long> problemIds);

    ProblemEntity findOnlineById(@Param("id") Long id);

    List<String> findTagNamesByProblemId(@Param("problemId") Long problemId);
}
