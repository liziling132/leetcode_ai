package com.leetcode.leetcode_ai.controller;

import com.leetcode.leetcode_ai.vo.AuthResponseVo;
import com.leetcode.leetcode_ai.dto.LoginRequestDto;
import com.leetcode.leetcode_ai.dto.RegisterRequestDto;
import com.leetcode.leetcode_ai.service.AuthService;
import com.leetcode.leetcode_ai.common.web.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<AuthResponseVo> register(@Valid @RequestBody RegisterRequestDto request) {
        return ApiResponse.success(authService.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponseVo> login(@Valid @RequestBody LoginRequestDto request) {
        return ApiResponse.success(authService.login(request));
    }
}

