package com.chaitanya.pms.config.initializer;

import com.chaitanya.pms.common.enums.RoleType;
import com.chaitanya.pms.role.entity.Role;

import com.chaitanya.pms.role.repository.RoleRepository;
import com.chaitanya.pms.user.entity.User;
import com.chaitanya.pms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        System.out.println("===== AdminInitializer Started =====");

        System.out.println("Admin exists: "
                + userRepository.existsByEmail("admin@pms.com"));

        System.out.println("Users count: "
                + userRepository.count());
        if (userRepository.existsByEmail("admin1234@pms.com")) {
            System.out.println("Admin already exists.");
            return;
        }

        Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                .orElseThrow(() ->
                        new RuntimeException("ROLE_ADMIN not found"));

        User admin = User.builder()
                .firstName("System")
                .lastName("Administrator")
                .email("admin1234@pms.com")
                .password(passwordEncoder.encode("Admin@1234"))
                .phoneNumber("9999999999")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .roles(Set.of(adminRole))
                .build();

        userRepository.save(admin);

        System.out.println("Default Admin Created");
    }
}