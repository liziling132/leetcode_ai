package com.leetcode.leetcode_ai.mapper;

import com.leetcode.leetcode_ai.vo.AdminUserItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminUserMapper {

    long countByCondition(@Param("keyword") String keyword,
                          @Param("status") Integer status);

    List<AdminUserItemVo> findPageByCondition(@Param("keyword") String keyword,
                                              @Param("status") Integer status,
                                              @Param("offset") int offset,
                                              @Param("size") int size);

    int updateStatus(@Param("userId") Long userId,
                     @Param("status") Integer status);
}
