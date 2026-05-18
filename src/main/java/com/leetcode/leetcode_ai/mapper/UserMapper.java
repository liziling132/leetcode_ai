package com.leetcode.leetcode_ai.mapper;

import com.leetcode.leetcode_ai.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    UserEntity findByUsername(@Param("username") String username);

    int insert(UserEntity UserEntity);
}
