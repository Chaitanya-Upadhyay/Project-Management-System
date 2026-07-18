package com.chaitanya.pms.auth.service;

import com.chaitanya.pms.auth.dto.*;
import com.chaitanya.pms.common.enums.RoleType;
import com.chaitanya.pms.exception.custom.InvalidTokenException;
import com.chaitanya.pms.exception.custom.ResourceAlreadyExistsException;
import com.chaitanya.pms.exception.custom.ResourceNotFoundException;
import com.chaitanya.pms.role.entity.Role;
import com.chaitanya.pms.role.service.RoleService;
import com.chaitanya.pms.security.jwt.JwtProperties;
import com.chaitanya.pms.security.jwt.JwtService;
import com.chaitanya.pms.security.user.CustomUserDetails;
import com.chaitanya.pms.token.entity.RefreshToken;
import com.chaitanya.pms.token.service.TokenService;
import com.chaitanya.pms.user.entity.User;
import com.chaitanya.pms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final JwtProperties jwtProperties;

    private final TokenService tokenService;

    @Transactional
    public UserResponse register(RegisterRequest request) {

        validateEmail(request.getEmail());

        Role defaultRole = getDefaultRole();

        User user = createUser(request, defaultRole);

        User savedUser = userRepository.save(user);

        return mapToUserResponse(savedUser);
    }

    private void validateEmail(String email) {

        if (userRepository.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException(
                    "User already exists with email : " + email
            );
        }
    }

    private Role getDefaultRole() {

        return roleService.getByName(RoleType.ROLE_USER);
    }

    private User createUser(RegisterRequest request, Role role) {

        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .enabled(true)
                .roles(Set.of(role))
                .build();
    }


    @Transactional
    public AuthenticationResponse login(LoginRequest request) {

        authenticate(request);

        User user = getUserByEmail(request.getEmail());

        String accessToken = jwtService.generateAccessToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);

        tokenService.saveRefreshToken(user, refreshToken);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void authenticate(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
    }

    private User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email : " + email
                        )
                );
    }

    private UserResponse mapToUserResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .enabled(user.isEnabled())
                .roles(
                        user.getRoles()
                                .stream()
                                .map(role -> role.getName().name())
                                .collect(Collectors.toSet())
                )
                .build();
    }


    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(Authentication authentication) {

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        User user = userRepository
                .findWithRolesByEmail(userDetails.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        System.out.println(
                "Roles initialized : "
                        + org.hibernate.Hibernate.isInitialized(user.getRoles())
        );

        System.out.println(
                "Roles size : "
                        + user.getRoles().size()
        );
        return mapToUserResponse(user);
    }


    @Transactional
    public AuthenticationResponse refreshToken(
            RefreshTokenRequest request) {

        RefreshToken existingRefreshToken =
                tokenService.getRefreshToken(
                        request.getRefreshToken()
                );

        if (tokenService.isRefreshTokenExpired(
                existingRefreshToken)) {

            tokenService.deleteRefreshToken(
                    existingRefreshToken
            );

            throw new InvalidTokenException(
                    "Refresh token has expired"
            );
        }

        User user = existingRefreshToken.getUser();

        String newAccessToken =
                jwtService.generateAccessToken(user);

        RefreshToken newRefreshToken =
                tokenService.createOrUpdateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .build();
    }
}