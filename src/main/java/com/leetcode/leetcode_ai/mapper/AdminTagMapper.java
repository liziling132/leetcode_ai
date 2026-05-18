package com.leetcode.leetcode_ai.mapper;

import com.leetcode.leetcode_ai.entity.TagEntity;
import com.leetcode.leetcode_ai.vo.AdminTagItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminTagMapper {

    long countByCondition(@Param("keyword") String keyword,
                          @Param("tagType") String tagType);

    List<AdminTagItemVo> findPageByCondition(@Param("keyword") String keyword,
                                             @Param("tagType") String tagType,
                                             @Param("offset") int offset,
                                             @Param("size") int size);

    TagEntity findById(@Param("id") Long id);

    TagEntity findByName(@Param("tagName") String tagName);

    int insert(@Param("tagName") String tagName,
               @Param("tagType") String tagType);

    int updateById(@Param("id") Long id,
                   @Param("tagName") String tagName,
                   @Param("tagType") String tagType);

    long countProblemReferences(@Param("tagId") Long tagId);

    int deleteProblemTagsByTagId(@Param("tagId") Long tagId);

    int deleteById(@Param("id") Long id);
}
