package com.sparta.myblog.security.jwt;

import com.sparta.myblog.repository.RefreshTokenRepository;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtTokenFilterConfigurer(JwtProvider jwtTokenProvider, RefreshTokenRepository refreshTokenRepository) {
        this.jwtProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void configure(HttpSecurity http) {
        JwtAuthenticationFilter customFilter = new JwtAuthenticationFilter(jwtProvider,refreshTokenRepository );
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

}