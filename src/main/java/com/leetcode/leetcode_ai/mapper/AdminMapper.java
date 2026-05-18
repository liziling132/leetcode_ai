package com.leetcode.leetcode_ai.mapper;

import com.leetcode.leetcode_ai.entity.AdminEntity;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {

    AdminEntity findByUsername(@Param("username") String username);
}
