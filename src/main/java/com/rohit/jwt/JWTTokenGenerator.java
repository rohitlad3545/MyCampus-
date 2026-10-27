package com.rohit.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTTokenGenerator {

    // Keep this key identical across token generation and validation.
    // In production, store it in an environment variable.
    private static final String SECRET =
            "GDF7acJj3fHj48U4/rtgWkSb31XKkqv0cHpP9yJJ2kU=";

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(
                                SECRET.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);

        String username = claims.get("username", String.class);

        // Support tokens that store username only as the subject.
        if (username == null || username.isBlank()) {
            username = claims.getSubject();
        }

        return username;
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public Boolean validateToken(
            String token, UserDetails userDetails) {

        try {
            Claims claims = extractAllClaims(token);

            String username = claims.get("username", String.class);

            if (username == null || username.isBlank()) {
                username = claims.getSubject();
            }

            Date expiration = claims.getExpiration();

            return username != null
                    && username.equals(userDetails.getUsername())
                    && expiration != null
                    && expiration.after(new Date());

        } catch (Exception exception) {
            return false;
        }
    }

    public String generateToken(
            UserDetails userDetails, String role) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("role", normalizeRole(role));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + 1000L * 60 * 60 * 10))
                .signWith(
                        Keys.hmacShaKeyFor(
                                SECRET.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256)
                .compact();
    }

    public String normalizeRole(String role) {
        if (role == null || role.isBlank()) {
            return null;
        }

        String normalizedRole = role.trim();

        if (normalizedRole.startsWith("ROLE_")) {
            normalizedRole = normalizedRole.substring(5);
        }

        return normalizedRole;
    }
}