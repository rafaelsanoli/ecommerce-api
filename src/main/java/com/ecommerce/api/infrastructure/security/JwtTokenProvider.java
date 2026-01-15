package com.ecommerce.api.infrastructure.security;
}
    }
        }
            return false;
            log.error("Token JWT inválido: {}", e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            return true;

                    .parseSignedClaims(token);
                    .build()
                    .verifyWith(key)
            Jwts.parser()

            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        try {
    public boolean validateToken(String token) {

    }
        return claims.getSubject();

                .getPayload();
                .parseSignedClaims(token)
                .build()
                .verifyWith(key)
        Claims claims = Jwts.parser()

        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    public String getUsernameFromToken(String token) {

    }
                .compact();
                .signWith(key)
                .expiration(expiryDate)
                .issuedAt(now)
                .subject(username)
        return Jwts.builder()

        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        Date now = new Date();
        String username = authentication.getName();
    public String generateToken(Authentication authentication) {

    private long jwtExpiration;
    @Value("${jwt.expiration}")

    private String jwtSecret;
    @Value("${jwt.secret}")

public class JwtTokenProvider {
@Component
@Slf4j

import java.util.Date;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.*;


