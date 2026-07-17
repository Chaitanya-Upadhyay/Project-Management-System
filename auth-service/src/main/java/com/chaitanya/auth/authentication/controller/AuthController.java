package com.chaitanya.auth.authentication.controller;

import com.chaitanya.auth.authentication.service.AuthService;
import com.chaitanya.auth.user.dto.request.RegisterRequest;
import com.chaitanya.auth.user.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }
}