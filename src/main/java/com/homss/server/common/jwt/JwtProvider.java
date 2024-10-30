package com.homss.server.common.jwt;

import com.homss.server.common.exception.ApplicationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static com.homss.server.common.exception.ExceptionCode.EXPIRED_TOKEN_ERROR;
import static com.homss.server.common.exception.ExceptionCode.INVALIDATE_TOKEN_ERROR;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY = "secretKey";

    @Value("${jwt.access-expiration-seconds}")
    private Long ACCESS_TOKEN_EXPIRATION_TIME = 0L;

    @Value("${jwt.refresh-expiration-seconds}")
    private Long REFRESH_TOKEN_EXPIRATION_TIME = 0L;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
    private Key getSigninKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Long userId) {
        Claims claims = Jwts.claims().setSubject(userId.toString());
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(getSigninKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(Long memberId) {
        Claims claims = Jwts.claims().setSubject(memberId.toString());
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME * 1000))
                .signWith(getSigninKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .claim("type", "REFRESH")
                .compact();
    }

    public Long getUserId(String token) {
        String info = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes()).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return Long.valueOf(info);
    }

    public boolean validateToken(String _token) {
        try {
            String token = _token.split("Bearer" + " ")[1];
            Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) { // 만료된 토큰
            throw ApplicationException.create(EXPIRED_TOKEN_ERROR);
        } catch (Exception e) { // 나머지 예외
            throw ApplicationException.create(INVALIDATE_TOKEN_ERROR);
        }
    }

}
