package com.chaitanya.pms.user.service;

import com.chaitanya.pms.auth.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();
}
