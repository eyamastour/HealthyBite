package com.esprit.pidev.controllers;


import com.esprit.pidev.entities.UserRole.ERole;
import com.esprit.pidev.entities.UserRole.Role;
import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.payload.request.SMSSendRequest;
import com.esprit.pidev.payload.request.SignupRequest;
import com.esprit.pidev.payload.response.MessageResponse;
import com.esprit.pidev.repository.UserRoleRepository.RoleRepository;
import com.esprit.pidev.repository.UserRoleRepository.UserRepository;

import com.esprit.pidev.security.services.IUser;
import com.esprit.pidev.security.services.SMSService;
import com.esprit.pidev.security.services.UserService;

import com.esprit.pidev.security.services.RoleService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController {
  @Autowired
  UserRepository userRepository;
  private final SMSService SS;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;
@Autowired
  private RoleService roleservice;

  @Autowired

  private UserService service;
  @Autowired
  private IUser iuser;
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }
  @PostMapping("/add")
  public ResponseEntity<?> addUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            break;
          case "mod":
            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);
            break;
          case "restaurant":
            Role restaurantRole = roleRepository.findByName(ERole.ROLE_RESTAURANT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(restaurantRole);
            break;
          case "fournisseur":
            Role fournisseurRole = roleRepository.findByName(ERole.ROLE_FOURNISSEUR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(fournisseurRole);
            break;

          default:
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }

    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));
    user.setPhone(signUpRequest.getPhone());
    user.setEnabled(true);

    User newUser = service.addUser(user, roles);



    return ResponseEntity.ok(new MessageResponse("User added successfully!"));
  }
  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @RequestBody SignupRequest signUpRequest) {
    service.updateUser(id, signUpRequest);
    return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
  }




  //@PutMapping("/updateUser")
  //public User updateUser(@RequestBody User user){
  //return iuser.updateUser(user);
  //}
  @GetMapping("getUserById/{id}")
  public User retrieveUserById(@PathVariable("id") Long id){
    return iuser.retrieveUserById(id);
  }
  //@PreAuthorize("hasAuthority('ROLE_USER') and isAuthenticated() and principal.isEnabled()")
  @GetMapping("/getAllUser")
  public List<User> retrieveAllUser(){
    return iuser.retrieveAllUser();
  }
  @DeleteMapping("deleteUser/{id}")
  public void deleteUser(@PathVariable("id") Long id){
    iuser.deleteUser(id);
  }
  @GetMapping("/users/search")
  public List<User> searchUsersByUsername(@RequestParam("username") String username) {
    return iuser.searchUsersByUsername(username);
  }

  @PostMapping("/{id}/roles")
  public ResponseEntity<?> assignRoleToUser(@PathVariable Long id, @RequestParam ERole roleName) {
    User user = service.retrieveUserById(id);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    Role role = roleservice.findByName(roleName);
    if (role == null) {
      return ResponseEntity.badRequest().build();
    }

    roleservice.assignRoleToUser(id, roleName);
    return ResponseEntity.ok().build();
  }
  @PutMapping("/{id}/enable")
  public ResponseEntity<?> enableUser(@PathVariable Long id) {
    User user = service.enableUser(id);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(user);
  }

  @PutMapping("/{id}/disable")
  public ResponseEntity<?> disableUser(@PathVariable Long id) {
    User user = service.disableUser(id);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(user);
  }
  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers(@AuthenticationPrincipal User user) {
    if (user != null && user.isEnabled()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    List<User> users = service.retrieveAllUser();
    return ResponseEntity.ok(users);
  }
  @PutMapping("/disableU3-roles")
  public ResponseEntity<?> disableUsersWithMoreThan3Roles() {
    service.disableUsersWithMoreThan3Roles();
    return ResponseEntity.ok().build();
  }
  @PutMapping("/disable-by-role")
  public ResponseEntity<String> disableUsersByRoleName(@RequestParam("roleName") String roleName) {
    service.disableUsersByRoleName(roleName);
    return ResponseEntity.ok("Users with role " + roleName + " have been disabled.");
  }
  @PutMapping("/sendsms")
  public String sendSms(@RequestBody SMSSendRequest sendRequest)
  {
    String codeGenerated=SS.generateCode();
    System.out.println(codeGenerated);
    User u1=userRepository.findByPhone(sendRequest.getPhone());
    System.out.println(u1.getPhone());
    u1.setCode(codeGenerated);
    iuser.SMSUSER(u1);
    return SS.sendSMS(sendRequest.getPhone(),"your code is :"+codeGenerated);
  }
  @PutMapping("/resetbysms/{code}/{newpassword}")
  public String resetBySms(@RequestBody SMSSendRequest sendRequest, @PathVariable("code") String code , @PathVariable("newpassword") String newpassword)
  {   User u12=userRepository.findByPhone(sendRequest.getPhone());
    if (code.equals(u12.getCode())&u12.getCode()!=null) {
      u12.setPassword(encoder.encode(newpassword));
      u12.setCode(null);
      iuser.SMSUSER(u12);
    }
    //here the exception or message if the code is not match to
    return SS.sendSMS(sendRequest.getPhone(),"your password has changed ;)");
  }











}
