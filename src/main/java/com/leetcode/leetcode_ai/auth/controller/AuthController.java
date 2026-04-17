package com.leetcode.leetcode_ai.auth.controller;

import com.leetcode.leetcode_ai.auth.dto.AuthResponse;
import com.leetcode.leetcode_ai.auth.dto.LoginRequest;
import com.leetcode.leetcode_ai.auth.dto.RegisterRequest;
import com.leetcode.leetcode_ai.auth.service.AuthService;
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
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.success(authService.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }
}
