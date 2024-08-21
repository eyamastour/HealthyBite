package com.esprit.pidev.security.services;

import com.esprit.pidev.entities.UserRole.ERole;
import com.esprit.pidev.entities.UserRole.Role;
import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.repository.UserRoleRepository.RoleRepository;
import com.esprit.pidev.repository.UserRoleRepository.UserRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService implements IRole{


    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Role findByName(ERole name) {
        Optional<Role> role = roleRepository.findByName(name);
        return role.orElseThrow(() -> new ResourceNotFoundException("Error: Role not found."));
    }


    @Override
    public Set<ERole> getAllRoles() {
        return null;
    }

    @Override
    public void assignRoleToUser(Long id, ERole roleName) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            Role role = findByName(roleName);
            if (role != null) {
                user.getRoles().add(role);
                userRepository.save(user);
            }
        }
    }

    @Override
    public Role saveRole(Role role) {
        return null;
    }

}

