package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.dto.LoginRequestDto;
import com.leetcode.leetcode_ai.dto.RegisterRequestDto;
import com.leetcode.leetcode_ai.entity.UserEntity;
import com.leetcode.leetcode_ai.mapper.UserMapper;
import com.leetcode.leetcode_ai.security.JwtTokenProvider;
import com.leetcode.leetcode_ai.vo.AuthResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final int CODE_USER_EXISTS = 1001;
    private static final int CODE_LOGIN_FAILED = 1002;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public AuthResponseVo register(RegisterRequestDto request) {
        UserEntity existing = userMapper.findByUsername(request.getUsername());
        if (existing != null) {
            throw new BizException(CODE_USER_EXISTS, "Username already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(request.getUsername());
        userEntity.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userEntity.setNickname(StringUtils.hasText(request.getNickname()) ? request.getNickname() : request.getUsername());
        userEntity.setStatus(1);
        userMapper.insert(userEntity);

        String token = jwtTokenProvider.createToken(userEntity.getId(), userEntity.getUsername());
        return new AuthResponseVo(userEntity.getId(), userEntity.getUsername(), userEntity.getNickname(), token);
    }

    @Override
    public AuthResponseVo login(LoginRequestDto request) {
        UserEntity userEntity = userMapper.findByUsername(request.getUsername());
        if (userEntity == null || !passwordEncoder.matches(request.getPassword(), userEntity.getPasswordHash())) {
            throw new BizException(CODE_LOGIN_FAILED, "Invalid username or password");
        }

        String token = jwtTokenProvider.createToken(userEntity.getId(), userEntity.getUsername());
        return new AuthResponseVo(userEntity.getId(), userEntity.getUsername(), userEntity.getNickname(), token);
    }
}
