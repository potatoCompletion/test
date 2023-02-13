package com.temp.api.common.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(new Date(now + accessTokenValidTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성 (HS256 해싱)
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + refreshTokenValidTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
     * @param accessToken
     * @return UsernamePasswordAuthenticationToken
     */
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) throw new RuntimeException("권한 정보가 없는 토큰입니다.");

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
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
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {

            log.info("Invalid JWT Token", e);

        } catch (ExpiredJwtException e) {

            log.info("Expired JWT Token", e);

        } catch (UnsupportedJwtException e) {

            log.info("Unsupported JWT Token", e);

        } catch (IllegalArgumentException e) {

            log.info("JWT claims string is empty.", e);

        }

        return false;
    }

    private Claims parseClaims(String accessToken) {
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
