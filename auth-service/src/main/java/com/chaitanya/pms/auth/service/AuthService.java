package com.chaitanya.pms.auth.service;

import com.chaitanya.pms.auth.dto.AuthenticationResponse;
import com.chaitanya.pms.auth.dto.LoginRequest;
import com.chaitanya.pms.auth.dto.RegisterRequest;
import com.chaitanya.pms.auth.dto.UserResponse;
import com.chaitanya.pms.common.enums.RoleType;
import com.chaitanya.pms.exception.custom.ResourceAlreadyExistsException;
import com.chaitanya.pms.exception.custom.ResourceNotFoundException;
import com.chaitanya.pms.role.entity.Role;
import com.chaitanya.pms.role.service.RoleService;
import com.chaitanya.pms.security.jwt.JwtProperties;
import com.chaitanya.pms.security.jwt.JwtService;
import com.chaitanya.pms.token.service.TokenService;
import com.chaitanya.pms.user.entity.User;
import com.chaitanya.pms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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
                                .collect(java.util.stream.Collectors.toSet())
                )
                .build();
    }

}