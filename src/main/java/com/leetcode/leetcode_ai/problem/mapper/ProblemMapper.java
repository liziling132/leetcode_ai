package com.leetcode.leetcode_ai.problem.mapper;

import com.leetcode.leetcode_ai.problem.entity.Problem;
import com.leetcode.leetcode_ai.problem.mapper.row.ProblemTagRow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProblemMapper {

    long countByCondition(@Param("keyword") String keyword,
                          @Param("difficulty") String difficulty,
                          @Param("tagId") Long tagId);

    List<Problem> findPageByCondition(@Param("keyword") String keyword,
                                      @Param("difficulty") String difficulty,
                                      @Param("tagId") Long tagId,
                                      @Param("offset") int offset,
                                      @Param("size") int size);

    List<ProblemTagRow> findTagsByProblemIds(@Param("problemIds") List<Long> problemIds);

    Problem findOnlineById(@Param("id") Long id);

    List<String> findTagNamesByProblemId(@Param("problemId") Long problemId);
}
