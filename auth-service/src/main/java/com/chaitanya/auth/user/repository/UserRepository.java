package com.chaitanya.auth.user.repository;

import com.chaitanya.auth.common.enums.RoleType;
import com.chaitanya.auth.role.entity.Role;
import com.chaitanya.auth.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}