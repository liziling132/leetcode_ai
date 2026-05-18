package com.leetcode.leetcode_ai.service;

import com.leetcode.leetcode_ai.common.exception.BizException;
import com.leetcode.leetcode_ai.dto.UserPasswordUpdateRequestDto;
import com.leetcode.leetcode_ai.dto.UserProfileUpdateRequestDto;
import com.leetcode.leetcode_ai.entity.UserEntity;
import com.leetcode.leetcode_ai.mapper.UserMapper;
import com.leetcode.leetcode_ai.security.JwtUserPrincipal;
import com.leetcode.leetcode_ai.vo.UserProfileVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserProfileVo profile() {
        UserEntity userEntity = loadCurrentUser();
        return toProfileVo(userEntity);
    }

    @Override
    @Transactional
    public void updateProfile(UserProfileUpdateRequestDto request) {
        UserEntity userEntity = loadCurrentUser();
        String username = request.getUsername().trim();
        long exists = userMapper.countByUsernameExcludeId(username, userEntity.getId());
        if (exists > 0) {
            throw new BizException(400, "Username already exists");
        }
        userMapper.updateProfile(userEntity.getId(), username, username, request.getAvatarUrl(), request.getBio());
    }

    @Override
    @Transactional
    public void updatePassword(UserPasswordUpdateRequestDto request) {
        UserEntity userEntity = loadCurrentUser();
        if (!passwordEncoder.matches(request.getOldPassword(), userEntity.getPasswordHash())) {
            throw new BizException(400, "Old password is incorrect");
        }
        if (request.getOldPassword().equals(request.getNewPassword())) {
            throw new BizException(400, "New password must be different from old password");
        }
        String newHash = passwordEncoder.encode(request.getNewPassword());
        userMapper.updatePasswordHash(userEntity.getId(), newHash);
    }

    private UserEntity loadCurrentUser() {
        Long userId = currentUserId();
        UserEntity userEntity = userMapper.findById(userId);
        if (userEntity == null) {
            throw new BizException(404, "User not found");
        }
        return userEntity;
    }

    private Long currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof JwtUserPrincipal principal)) {
            throw new BizException(401, "Unauthorized");
        }
        return principal.userId();
    }

    private UserProfileVo toProfileVo(UserEntity userEntity) {
        return new UserProfileVo(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getUsername(),
                userEntity.getAvatarUrl(),
                userEntity.getBio(),
                userEntity.getStatus()
        );
    }
}
