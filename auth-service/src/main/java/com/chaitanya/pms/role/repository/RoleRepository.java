package com.chaitanya.pms.role.repository;


import com.chaitanya.pms.common.enums.RoleType;
import com.chaitanya.pms.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(RoleType name);

    boolean existsByName(RoleType name);


}