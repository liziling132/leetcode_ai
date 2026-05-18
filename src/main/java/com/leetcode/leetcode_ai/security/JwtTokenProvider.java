package com.leetcode.leetcode_ai.security;

import com.leetcode.leetcode_ai.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    public static final String PRINCIPAL_USER = "USER";
    public static final String PRINCIPAL_ADMIN = "ADMIN";

    private final JwtProperties jwtProperties;

    public String createToken(Long userId, String username) {
        return createToken(userId, username, PRINCIPAL_USER, "USER");
    }

    public String createToken(Long userId, String username, String principalType, String role) {
        Instant now = Instant.now();
        Instant expireAt = now.plusSeconds(jwtProperties.getExpirationSeconds());
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .claim("principalType", principalType)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expireAt))
                .signWith(getSecretKey())
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}
