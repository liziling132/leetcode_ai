package com.leetcode.leetcode_ai.security;

public record JwtUserPrincipal(Long userId, String username, String principalType, String role) {

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(principalType);
    }
}
