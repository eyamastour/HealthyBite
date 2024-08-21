package com.esprit.pidev.security.services;

import com.esprit.pidev.entities.UserRole.Role;
import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.payload.request.SignupRequest;

import java.util.List;
import java.util.Set;

public interface IUser {
    User addUser(User user, Set<Role> roles);
    User retrieveUserById(Long id);
    public void SMSUSER(User user);
    List<User> retrieveAllUser();
    void deleteUser(Long id);
    List<User> searchUsersByUsername(String username);
    void updateUser(Long id, SignupRequest signUpRequest);
    User findByEmail(String email);
    Set<Role> getRoles(Long id);
}
