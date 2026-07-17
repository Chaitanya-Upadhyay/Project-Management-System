package com.chaitanya.pms.token.service;

import com.chaitanya.pms.security.jwt.JwtProperties;
import com.chaitanya.pms.token.entity.RefreshToken;
import com.chaitanya.pms.token.repository.RefreshTokenRepository;
import com.chaitanya.pms.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    /**
     * Save or replace refresh token for a user.
     * We allow only one active refresh token per user.
     */
    public RefreshToken save(User user,
                             String token,
                             Instant expiryDate) {

        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .expiryDate(expiryDate)
                .user(user)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }



    @Transactional
    public RefreshToken saveRefreshToken(User user, String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByUser(user)
                .orElseGet(RefreshToken::new);

        refreshToken.setUser(user);
        refreshToken.setToken(token);

        refreshToken.setExpiryDate(
                Instant.now().plusMillis(
                        jwtProperties.getRefreshTokenExpiration()
                )
        );

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Find refresh token by token value.
     */
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Find refresh token by user.
     */
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByUser(User user) {
        return refreshTokenRepository.findByUser(user);
    }

    /**
     * Delete refresh token of a user.
     * Used during logout.
     */
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    /**
     * Delete a refresh token.
     */
    public void delete(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

    /**
     * Check if refresh token has expired.
     */
    public boolean isExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().isBefore(Instant.now());
    }

}