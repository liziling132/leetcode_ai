package com.leetcode.leetcode_ai.auth.service;

import com.leetcode.leetcode_ai.auth.dto.AuthResponse;
import com.leetcode.leetcode_ai.auth.dto.LoginRequest;
import com.leetcode.leetcode_ai.auth.dto.RegisterRequest;
import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.security.JwtTokenProvider;
import com.leetcode.leetcode_ai.user.entity.User;
import com.leetcode.leetcode_ai.user.mapper.UserMapper;
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
    public AuthResponse register(RegisterRequest request) {
        User existing = userMapper.findByUsername(request.getUsername());
        if (existing != null) {
            throw new BizException(CODE_USER_EXISTS, "用户名已存在");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setNickname(StringUtils.hasText(request.getNickname()) ? request.getNickname() : request.getUsername());
        userMapper.insert(user);

        String token = jwtTokenProvider.createToken(user.getId(), user.getUsername());
        return new AuthResponse(user.getId(), user.getUsername(), user.getNickname(), token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userMapper.findByUsername(request.getUsername());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BizException(CODE_LOGIN_FAILED, "用户名或密码错误");
        }

        String token = jwtTokenProvider.createToken(user.getId(), user.getUsername());
        return new AuthResponse(user.getId(), user.getUsername(), user.getNickname(), token);
    }
}
