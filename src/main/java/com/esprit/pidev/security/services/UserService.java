package com.esprit.pidev.security.services;

import com.esprit.pidev.entities.UserRole.ERole;
import com.esprit.pidev.entities.UserRole.Role;

import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.payload.request.SignupRequest;
import com.esprit.pidev.repository.UserRoleRepository.RoleRepository;
import com.esprit.pidev.repository.UserRoleRepository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserService implements IUser{
    private UserRepository repo;
    @Autowired

    PasswordEncoder encoder;
    private RoleRepository rolo;
    @Autowired
    private JavaMailSender mailSender;


    public User enableUser(Long id) {
        User user = repo.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        user.setEnabled(true);
        return repo.save(user);
    }

    public User disableUser(Long id) {
        User user = repo.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        user.setEnabled(false);
        return repo.save(user);
    }
    @Transactional
    public void disableUsersWithMoreThan3Roles() {
        repo.disableUsersWithMoreThan3Roles();
    }
    @Transactional
    public void disableUsersByRoleName(String roleName) {
        List<User> users = repo.findByRolesName(roleName);
        for (User user : users) {
            user.setEnabled(false);
            repo.save(user);
        }
    }


        //public User saveUser (User user){
           // return repo.save(user);
        //}

    //public User fetchUserByEmail(String email) {
       // return repo.findByEmail(email);
    //}

    //public User fetchUserByEmailAndPassword(String email, String password) {
        //return repo.findByEmailAndPassword(email, password);
    //}


    public void updatePassword(User user, String newPassword) {
        user.setPassword(encoder.encode(newPassword));
        repo.save(user);
    }


    @Override
    public User addUser(User user, Set<Role> roles) {
        user.setRoles(roles);
        return repo.save(user);    }

    @Override
    public User retrieveUserById(Long id) {
        return repo.findById(id).orElse(null);    }

    @Override
    public void SMSUSER(User user) {
         repo.save(user);
    }

    @Override
    public List<User> retrieveAllUser() {

        return repo.findAll();    }

    @Override
    public void deleteUser(Long id) {
        repo.deleteById(id);


    }

    @Override
    public List<User> searchUsersByUsername(String username) {
        return repo.findByUsernameContainingIgnoreCase(username);
    }

    @Override
    public void updateUser(Long id, SignupRequest signUpRequest) {
        User user = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPhone(signUpRequest.getPhone());

        if (signUpRequest.getPassword() != null) {
            user.setPassword(encoder.encode(signUpRequest.getPassword()));
        }

        Set<Role> roles = new HashSet<>();

        signUpRequest.getRole().forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = rolo.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                    break;
                case "mod":
                    Role modRole = rolo.findByName(ERole.ROLE_MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);
                    break;
                case "restaurant":
                    Role restaurantRole = rolo.findByName(ERole.ROLE_RESTAURANT)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(restaurantRole);
                    break;
                case "fournisseur":
                    Role fournisseurRole = rolo.findByName(ERole.ROLE_FOURNISSEUR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(fournisseurRole);
                    break;
                default:
                    Role userRole = rolo.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);

        repo.save(user);
    }





    @Override
    public User findByEmail(String email) {
        return repo.findByEmail(email);    }

    @Override
    public Set<Role> getRoles(Long id) {
        User user = repo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
        return user.getRoles();    }


}
