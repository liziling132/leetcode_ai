package com.leetcode.leetcode_ai.auth.service;

import com.leetcode.leetcode_ai.auth.dto.AuthResponse;
import com.leetcode.leetcode_ai.auth.dto.LoginRequest;
import com.leetcode.leetcode_ai.auth.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
