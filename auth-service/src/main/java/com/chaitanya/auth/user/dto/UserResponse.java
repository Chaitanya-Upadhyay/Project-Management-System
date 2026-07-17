package com.chaitanya.auth.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UserResponse {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private boolean enabled;

    private Set<String> roles;
}