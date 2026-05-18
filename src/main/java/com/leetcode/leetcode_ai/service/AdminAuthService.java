package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.dto.LoginRequestDto;
import com.leetcode.leetcode_ai.vo.AuthResponseVo;

public interface AdminAuthService {

    AuthResponseVo login(LoginRequestDto request);
}
