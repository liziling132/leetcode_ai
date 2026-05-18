package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.dto.LoginRequestDto;
import com.leetcode.leetcode_ai.entity.AdminEntity;
import com.leetcode.leetcode_ai.mapper.AdminMapper;
import com.leetcode.leetcode_ai.security.JwtTokenProvider;
import com.leetcode.leetcode_ai.vo.AuthResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private static final int CODE_LOGIN_FAILED = 1002;

    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponseVo login(LoginRequestDto request) {
        AdminEntity adminEntity = adminMapper.findByUsername(request.getUsername());
        if (adminEntity == null || adminEntity.getStatus() == null || adminEntity.getStatus() != 1) {
            throw new BizException(CODE_LOGIN_FAILED, "Invalid username or password");
        }
        if (!passwordEncoder.matches(request.getPassword(), adminEntity.getPasswordHash())) {
            throw new BizException(CODE_LOGIN_FAILED, "Invalid username or password");
        }
        String role = "ADMIN";
        String token = jwtTokenProvider.createToken(
                adminEntity.getId(),
                adminEntity.getUsername(),
                JwtTokenProvider.PRINCIPAL_ADMIN,
                role
        );
        return new AuthResponseVo(adminEntity.getId(), adminEntity.getUsername(), adminEntity.getNickname(), token);
    }
}
