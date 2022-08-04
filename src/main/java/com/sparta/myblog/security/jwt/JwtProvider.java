package com.sparta.myblog.security.jwt;

import com.sparta.myblog.dto.TokenDto;
import com.sparta.myblog.dto.UserRequestDto;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private final UserDetailsService userDetailsService;

    public JwtProvider(@Qualifier("userUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //==토큰 생성 메소드==//
    public TokenDto createToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofHours(2).toMillis()); // 만료기간 1일

        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
                .setIssuedAt(now) // 발급시간(iat)
                .setExpiration(expiration) // 만료시간(exp)
                .setSubject(username) //  토큰 제목(subject)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes())) // 알고리즘, 시크릿 키
                .compact();
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now.getTime() + Duration.ofDays(7).toMillis()))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .compact();
        return new TokenDto(accessToken, refreshToken);
    }

    //==Jwt 토큰의 유효성 체크 메소드==//
    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes())).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
    public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader("REFRESH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes())).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
