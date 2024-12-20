package org.zerock.cleanaido_customer_back.auth.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {


    private final Key secretKey; // Base64 디코딩 후 Key로 변환
    private final long accessExpiration = 1000 * 60 * 60* 24; // 1시간
    private final long refreshExpiration = 1000 * 60 * 60 * 24 * 7; // 7일

    public JWTUtil(@Value("${jwt.secret}") String secretKey) {
        // Base64로 인코딩된 키를 디코딩 후 Key 객체 생성
        if (secretKey == null || secretKey.length() < 64) {
            throw new IllegalArgumentException("The secret key must be at least 64 characters long.");
        }
        this.secretKey = Keys.hmacShaKeyFor(io.jsonwebtoken.io.Decoders.BASE64.decode(secretKey));
    }

    // Access Token 생성
    public String generateAccessToken(String customerId) {
        return Jwts.builder()
                .setSubject(customerId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS512) // Key 객체 사용
                .compact();
    }

    // Refresh Token 생성
    public String generateRefreshToken(String customerId) {
        return Jwts.builder()
                .setSubject(customerId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS512) // Key 객체 사용
                .compact();
    }

    // 토큰 검증 및 고객 ID 추출
    public String validateAndExtract(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // Key 객체 사용
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject(); // 토큰에서 Subject(Customer ID) 추출
        } catch (ExpiredJwtException e) {
            throw new JwtException("The token has expired.", e);
        } catch (MalformedJwtException e) {
            throw new JwtException("The token is malformed.", e);
        } catch (JwtException e) {
            throw new JwtException("The token is invalid.", e);
        }
    }
}