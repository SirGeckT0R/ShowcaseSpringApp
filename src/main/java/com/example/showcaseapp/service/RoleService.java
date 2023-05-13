package com.example.showcaseapp.service;


import com.example.showcaseapp.entity.Role;
import com.example.showcaseapp.entity.User;
import com.example.showcaseapp.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository rolesRepository;

    public RoleService(RoleRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public void setUserRole(User user) {
        this.setRole(user, "User");
    }

    public void setAdminRole(User user) {
        this.setRole(user, "Admin");
    }

    public void setRole(User user, String roleName) {
        Role role = saveRole(roleName);

        user.setRole(role);
    }

    public Role saveRole(String name) {
        Optional<Role> userRole = rolesRepository.findByName(name);
        Role role = null;
        if (userRole.isEmpty()) {
            role = new Role(name);
            role = rolesRepository.save(role);
        }else{
            role=userRole.get();
        }
        return role;
    }
}
