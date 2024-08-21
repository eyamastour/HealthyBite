package com.esprit.pidev.security.services;
import com.esprit.pidev.entities.UserRole.ERole;
import com.esprit.pidev.entities.UserRole.Role;
import com.esprit.pidev.entities.UserRole.User;

import java.util.Set;

public interface IRole {
    void assignRoleToUser(Long id, ERole roleName);
    Role saveRole(Role role);
    Role findByName(ERole name);
    Set<ERole> getAllRoles();

}
