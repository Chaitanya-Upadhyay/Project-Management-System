package com.chaitanya.pms.auth.controller;

import com.chaitanya.pms.auth.dto.AuthenticationResponse;
import com.chaitanya.pms.auth.dto.LoginRequest;
import com.chaitanya.pms.auth.dto.RegisterRequest;
import com.chaitanya.pms.auth.dto.UserResponse;
import com.chaitanya.pms.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
}