package com.chaitanya.auth.authentication.service;

import com.chaitanya.auth.common.enums.RoleType;
import com.chaitanya.auth.exception.custom.EmailAlreadyExistsException;
import com.chaitanya.auth.exception.custom.RoleNotFoundException;
import com.chaitanya.auth.role.entity.Role;
import com.chaitanya.auth.role.repository.RoleRepository;
import com.chaitanya.auth.user.dto.request.RegisterRequest;
import com.chaitanya.auth.user.dto.response.UserResponse;
import com.chaitanya.auth.user.entity.User;
import com.chaitanya.auth.user.mapper.UserMapper;
import com.chaitanya.auth.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        User user = UserMapper.toEntity(request);

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        Role role = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() ->
                        new RoleNotFoundException("Default role not found."));

        user.getRoles().add(role);

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }
}