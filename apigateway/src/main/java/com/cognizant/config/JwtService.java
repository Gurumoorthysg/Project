package com.cognizant.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final String SECRET = "my-super-secret-key-that-is-at-least-32-chars";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public Claims extractClaims(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(key)
                .build();
        return parser.parseSignedClaims(token).getPayload();
    }

    public boolean isTokenValid(String token) {
        try { 
            extractClaims(token); // throws exception if invalid
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
