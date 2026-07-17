package com.chaitanya.pms.config;

import com.chaitanya.pms.common.enums.RoleType;
import com.chaitanya.pms.role.entity.Role;
import com.chaitanya.pms.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        createRole(RoleType.ROLE_ADMIN);

        createRole(RoleType.ROLE_MANAGER);

        createRole(RoleType.ROLE_USER);
    }

    private void createRole(RoleType roleType) {

        roleRepository.findByName(roleType)
                .orElseGet(() -> roleRepository.save(
                        Role.builder()
                                .name(roleType)
                                .description(roleType.name())
                                .build()
                ));
    }
}