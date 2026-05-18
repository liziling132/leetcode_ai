package com.leetcode.leetcode_ai.mapper;

import com.leetcode.leetcode_ai.entity.AdminResourceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminResourceMapper {

    long countByCondition(@Param("keyword") String keyword,
                          @Param("resourceType") String resourceType,
                          @Param("status") Integer status);

    List<AdminResourceEntity> findPageByCondition(@Param("keyword") String keyword,
                                                  @Param("resourceType") String resourceType,
                                                  @Param("status") Integer status,
                                                  @Param("offset") int offset,
                                                  @Param("size") int size);

    AdminResourceEntity findById(@Param("id") Long id);

    int insert(@Param("entity") AdminResourceEntity entity);

    int updateById(@Param("entity") AdminResourceEntity entity);

    int deleteById(@Param("id") Long id);
}
