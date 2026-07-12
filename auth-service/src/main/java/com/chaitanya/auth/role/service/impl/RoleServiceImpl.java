package com.chaitanya.auth.role.service.impl;


import com.chaitanya.auth.role.repository.RoleRepository;
import com.chaitanya.auth.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

}
