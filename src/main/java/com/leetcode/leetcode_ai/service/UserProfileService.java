package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.dto.UserPasswordUpdateRequestDto;
import com.leetcode.leetcode_ai.dto.UserProfileUpdateRequestDto;
import com.leetcode.leetcode_ai.vo.UserProfileVo;

public interface UserProfileService {

    UserProfileVo profile();

    void updateProfile(UserProfileUpdateRequestDto request);

    void updatePassword(UserPasswordUpdateRequestDto request);
}
