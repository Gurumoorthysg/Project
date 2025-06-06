package com.cognizant.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;

@Service
@Data
public class JwtService {

    private final String secretKey = "my-super-secret-key-that-is-at-least-32-chars";
    private final SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String username, String roles) {
        return Jwts.builder()
            .subject(username)
            .claim("roles", List.of("ROLE_"+roles))
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
            .signWith(key) // ? only the key is required; algorithm inferred
            .compact();
    }
}
