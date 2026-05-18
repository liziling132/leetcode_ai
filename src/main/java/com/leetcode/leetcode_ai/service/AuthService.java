package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.vo.AuthResponseVo;
import com.leetcode.leetcode_ai.dto.LoginRequestDto;
import com.leetcode.leetcode_ai.dto.RegisterRequestDto;

public interface AuthService {

    AuthResponseVo register(RegisterRequestDto request);

    AuthResponseVo login(LoginRequestDto request);
}
