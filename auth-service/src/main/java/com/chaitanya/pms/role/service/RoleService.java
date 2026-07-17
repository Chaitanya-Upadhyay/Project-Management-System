package com.chaitanya.pms.role.service;

import com.chaitanya.pms.common.enums.RoleType;
import com.chaitanya.pms.exception.custom.ResourceNotFoundException;
import com.chaitanya.pms.role.entity.Role;
import com.chaitanya.pms.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getByName(RoleType roleType) {

        return roleRepository.findByName(roleType)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Role not found : " + roleType
                        ));
    }
}