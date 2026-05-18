package com.leetcode.leetcode_ai.mapper;

import com.leetcode.leetcode_ai.entity.ProblemEntity;
import com.leetcode.leetcode_ai.mapper.row.ProblemTagRow;
import com.leetcode.leetcode_ai.vo.AdminProblemListItemVo;
import com.leetcode.leetcode_ai.vo.AdminProblemTestcaseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminProblemMapper {

    long countByCondition(@Param("keyword") String keyword,
                          @Param("difficulty") String difficulty,
                          @Param("status") Integer status,
                          @Param("tagId") Long tagId);

    List<AdminProblemListItemVo> findPageByCondition(@Param("keyword") String keyword,
                                                     @Param("difficulty") String difficulty,
                                                     @Param("status") Integer status,
                                                     @Param("tagId") Long tagId,
                                                     @Param("offset") int offset,
                                                     @Param("size") int size);

    ProblemEntity findById(@Param("id") Long id);

    int insertProblem(@Param("problem") ProblemEntity problem,
                      @Param("createdBy") Long createdBy,
                      @Param("updatedBy") Long updatedBy);

    int updateProblem(@Param("problem") ProblemEntity problem,
                      @Param("updatedBy") Long updatedBy);

    int softDelete(@Param("id") Long id,
                   @Param("updatedBy") Long updatedBy);

    int deleteProblemTags(@Param("problemId") Long problemId);

    int insertProblemTag(@Param("problemId") Long problemId,
                         @Param("tagId") Long tagId);

    long countTagsByIds(@Param("tagIds") List<Long> tagIds);

    int deleteTestcases(@Param("problemId") Long problemId);

    int insertTestcase(@Param("problemId") Long problemId,
                       @Param("caseType") String caseType,
                       @Param("inputData") String inputData,
                       @Param("outputData") String outputData,
                       @Param("score") Integer score,
                       @Param("sortOrder") Integer sortOrder,
                       @Param("isActive") Integer isActive);

    List<Long> findTagIdsByProblemId(@Param("problemId") Long problemId);

    List<String> findTagNamesByProblemId(@Param("problemId") Long problemId);

    List<AdminProblemTestcaseVo> findTestcasesByProblemId(@Param("problemId") Long problemId);

    List<ProblemTagRow> findTagRowsByProblemIds(@Param("problemIds") List<Long> problemIds);
}
