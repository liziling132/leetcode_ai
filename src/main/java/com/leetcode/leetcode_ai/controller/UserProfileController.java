package com.leetcode.leetcode_ai.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.dto.UserPasswordUpdateRequestDto;
import com.leetcode.leetcode_ai.dto.UserProfileUpdateRequestDto;
import com.leetcode.leetcode_ai.service.UserProfileService;
import com.leetcode.leetcode_ai.vo.UserProfileVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/profile")
    public ApiResponse<UserProfileVo> profile() {
        return ApiResponse.success(userProfileService.profile());
    }

    @PutMapping("/profile")
    public ApiResponse<Void> updateProfile(@Valid @RequestBody UserProfileUpdateRequestDto request) {
        userProfileService.updateProfile(request);
        return ApiResponse.success(null);
    }

    @PutMapping("/password")
    public ApiResponse<Void> updatePassword(@Valid @RequestBody UserPasswordUpdateRequestDto request) {
        userProfileService.updatePassword(request);
        return ApiResponse.success(null);
    }
}
