package com.sparta.myblog.security.jwt;

import com.sparta.myblog.dto.TokenDto;
import com.sparta.myblog.model.RefreshToken;
import com.sparta.myblog.repository.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtProvider.resolveAccessToken(request);
        String refreshToken = jwtProvider.resolveRefreshToken(request);
        log.info("----------------------refreshToken----------------------");
        log.info("refreshToken: {}", refreshToken);
        log.info("---------------------validateToken----------------------");
//        log.info(String.valueOf(!jwtProvider.validateToken(accessToken)));
        // 유효한 토큰이면 콘텍스트에 저장
        if (accessToken != null && jwtProvider.validateToken(accessToken)) {
            Authentication authentication = jwtProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (refreshToken != null && !jwtProvider.validateToken(accessToken)) {
            if (jwtProvider.validateToken(refreshToken)) {
                Optional<RefreshToken> refreshToken1 = refreshTokenRepository.findRefreshTokenByRefreshToken(refreshToken);
                if (refreshToken1.isPresent()) {
                    String keyUsername = refreshToken1.get().getKeyUsername();
                    TokenDto newTokenDto = jwtProvider.createToken(keyUsername);
                    response.addHeader("newAccessToken", newTokenDto.getAccessToken());
                    response.addHeader("newRefreshToken", newTokenDto.getRefreshToken());
                    RefreshToken refreshToken2 = new RefreshToken(newTokenDto.getRefreshToken(), keyUsername);
                    refreshTokenRepository.save(refreshToken2);
                }

            }

        }
        filterChain.doFilter(request, response);
    }
}
