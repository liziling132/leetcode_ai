package com.leetcode.leetcode_ai.user.mapper;

import com.leetcode.leetcode_ai.user.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User findByUsername(@Param("username") String username);

    int insert(User user);
}
