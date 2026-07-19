package com.chaitanya.pms.token.service;

import com.chaitanya.pms.exception.custom.ResourceNotFoundException;
import com.chaitanya.pms.security.jwt.JwtProperties;
import com.chaitanya.pms.security.jwt.JwtService;
import com.chaitanya.pms.token.entity.RefreshToken;
import com.chaitanya.pms.token.repository.RefreshTokenRepository;
import com.chaitanya.pms.user.entity.User;
import com.chaitanya.pms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;



    @Transactional
    public RefreshToken saveRefreshToken(
            User user,
            String token) {

        // Delete existing refresh token for this user
        refreshTokenRepository.deleteByUserId(user.getId());

        // Immediately flush DELETE to database
        refreshTokenRepository.flush();

        // Create new refresh token
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .expiryDate(
                        Instant.now().plusMillis(
                                jwtProperties.getRefreshTokenExpiration()
                        )
                )
                .user(user)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public RefreshToken createOrUpdateRefreshToken(User user) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByUser(user)
                .orElseGet(RefreshToken::new);

        refreshToken.setToken(
                jwtService.generateRefreshToken(user)
        );

        refreshToken.setExpiryDate(
                jwtService.getRefreshTokenExpiry()
        );

        refreshToken.setUser(user);

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public RefreshToken getRefreshToken(String token) {

        return refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found"));
    }

    public boolean isRefreshTokenExpired(
            RefreshToken refreshToken) {

        return refreshToken
                .getExpiryDate()
                .isBefore(Instant.now());
    }

    @Transactional
    public void deleteRefreshToken(RefreshToken refreshToken) {

        refreshTokenRepository.delete(refreshToken);
    }


    @Transactional
    public void deleteRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
    @Transactional
    public void deleteByUser(User user) {

        refreshTokenRepository.deleteByUser(user);
    }
}