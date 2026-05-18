package com.leetcode.leetcode_ai.mapper;

import com.leetcode.leetcode_ai.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    UserEntity findById(@Param("id") Long id);

    UserEntity findByUsername(@Param("username") String username);

    int insert(UserEntity UserEntity);

    long countByUsernameExcludeId(@Param("username") String username,
                                  @Param("id") Long id);

    int updateProfile(@Param("id") Long id,
                      @Param("username") String username,
                      @Param("nickname") String nickname,
                      @Param("avatarUrl") String avatarUrl,
                      @Param("bio") String bio);

    int updatePasswordHash(@Param("id") Long id,
                           @Param("passwordHash") String passwordHash);
}
