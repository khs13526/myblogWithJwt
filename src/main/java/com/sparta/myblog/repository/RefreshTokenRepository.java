package com.sparta.myblog.repository;

import com.sparta.myblog.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findRefreshTokenByRefreshToken(String refreshToken);
}
