package com.chaitanya.pms.auth.controller;

import com.chaitanya.pms.auth.dto.*;
import com.chaitanya.pms.auth.dto.request.ChangePasswordRequest;
import com.chaitanya.pms.auth.service.AuthService;
import com.chaitanya.pms.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public UserResponse register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }
    @GetMapping("/me")
    public UserResponse me(Authentication authentication) {

        return authService.getCurrentUser(authentication);
    }

    @PostMapping("/refresh")
    public AuthenticationResponse refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {

        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication) {

        authService.logout(authentication);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request, Authentication authentication) {

        authService.changePassword(request, authentication);
        return ResponseEntity.noContent().build();
    }
}