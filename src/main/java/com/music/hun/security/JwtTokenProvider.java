package com.music.hun.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor // 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자 생성
@Component
public class JwtTokenProvider {

    // secretKey
    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    // 토큰 유효 시간 - 50분
    private long tokenValidTime = 50 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    // 의존성 주입 이루어진 후 객체 초기화, secret 을 인코딩
    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPk, List<String> roles) {
        // JWT payload 에 저장되는 정보 단위
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now)  // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // Expire Time
                .signWith(key, SignatureAlgorithm.HS256) // signature 에 들어갈 secret 값 세팅
                .compact();

    }

    // Request 의 Header 에서 token 값 가져오기 - "X-AUTH-TOKEN" : "TOKEN 값"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts
                    .parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }

    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // JWT 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
