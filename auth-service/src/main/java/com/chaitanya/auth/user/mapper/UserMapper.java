package com.chaitanya.auth.user.mapper;

import com.chaitanya.auth.role.entity.Role;
import com.chaitanya.auth.user.dto.request.RegisterRequest;
import com.chaitanya.auth.user.dto.response.UserResponse;
import com.chaitanya.auth.user.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public final class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(RegisterRequest request) {

        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail().toLowerCase())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .enabled(false)
                .build();
    }

    public static UserResponse toResponse(User user) {

        Set<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .map(Enum::name)
                .collect(Collectors.toSet());

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .enabled(user.isEnabled())
                .roles(roles)
                .build();
    }
}