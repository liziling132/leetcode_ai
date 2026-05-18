package com.leetcode.leetcode_ai.mapper;

import com.leetcode.leetcode_ai.vo.FavoriteItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FavoriteMapper {

    int insertIgnore(@Param("userId") Long userId, @Param("problemId") Long problemId);

    int deleteByUserAndProblem(@Param("userId") Long userId, @Param("problemId") Long problemId);

    long countByUserId(@Param("userId") Long userId);

    List<FavoriteItemVo> findPageByUserId(@Param("userId") Long userId,
                                          @Param("offset") int offset,
                                          @Param("size") int size);
}
