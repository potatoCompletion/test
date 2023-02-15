package com.temp.api.common.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final String secretKey = "EykEsretciSsEcRetkEyandnomeaningjustforaddbit";  // HS256 알고리즘 쓰려면 256비트 보다 커야됨
    private long accessTokenValidTime = 30 * 60 * 1000L;  // 유효기간 30분
    private long refreshTokenValidTime = 1440 * 60 * 1000L;  // 유효기간 1일

    public JwtTokenProvider() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 토큰생성 메서드
     * @param authentication
     * @return TokenInfo
     */
    public TokenInfo generateToken(Authentication authentication) {

        // 인증정보 생성
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성 (HS256 해싱)
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(new Date(now + accessTokenValidTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Redis 미완성으로 인한 refreshToken 생성 로직 삭제

        // Refresh Token 생성 (HS256 해싱)
//        String refreshToken = Jwts.builder()
//                .setExpiration(new Date(now + refreshTokenValidTime))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    /**
     * 토큰 정보를 검증하는 메서드
     * @param token
     * @return boolean
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException ex) {
            log.info("잘못된 JWT 토큰입니다.", ex);
            throw new SecurityException("잘못된 JWT 토큰입니다.");
        } catch (ExpiredJwtException ex) {
            log.info("만료된 JWT 토큰입니다. (토큰 재발급 로직 미완성)", ex);
            throw new SecurityException("만료된 JWT 토큰입니다. (토큰 재발급 로직 미완성)");
        } catch (UnsupportedJwtException ex) {
            log.info("지원되지 않는 JWT 토큰입니다.", ex);
            throw new SecurityException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException ex) {
            log.info("JWT claims이 비어있습니다.", ex);
            throw new SecurityException("JWT claims이 비어있습니다.");
        }
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            return ex.getClaims();
        }
    }

}
