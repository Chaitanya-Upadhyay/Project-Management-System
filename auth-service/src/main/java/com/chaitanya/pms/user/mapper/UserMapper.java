package com.chaitanya.pms.user.mapper;

import com.chaitanya.pms.role.entity.Role;
import com.chaitanya.pms.auth.dto.RegisterRequest;
import com.chaitanya.pms.auth.dto.UserResponse;
import com.chaitanya.pms.user.entity.User;

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