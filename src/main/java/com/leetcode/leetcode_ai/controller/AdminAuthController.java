package com.leetcode.leetcode_ai.controller;

import com.leetcode.leetcode_ai.common.web.ApiResponse;
import com.leetcode.leetcode_ai.dto.LoginRequestDto;
import com.leetcode.leetcode_ai.service.AdminAuthService;
import com.leetcode.leetcode_ai.vo.AuthResponseVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @PostMapping("/login")
    public ApiResponse<AuthResponseVo> login(@Valid @RequestBody LoginRequestDto request) {
        return ApiResponse.success(adminAuthService.login(request));
    }
}
