package com.esprit.pidev.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;



import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;



import com.esprit.pidev.entities.UserRole.ERole;

import com.esprit.pidev.entities.UserRole.Role;
import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.entities.UserRole.VerificationToken;
import com.esprit.pidev.payload.request.LoginRequest;
import com.esprit.pidev.payload.request.SignupRequest;
import com.esprit.pidev.payload.response.JwtResponse;
import com.esprit.pidev.payload.response.MessageResponse;
import com.esprit.pidev.repository.UserRoleRepository.RoleRepository;
import com.esprit.pidev.repository.UserRoleRepository.UserRepository;
import com.esprit.pidev.security.jwt.JwtUtils;
import com.esprit.pidev.security.services.UserDetailsImpl;

import com.esprit.pidev.repository.UserRoleRepository.VerificationTokenRepository;
import com.esprit.pidev.security.services.EmailSenderService;
import com.esprit.pidev.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  private EmailSenderService emailSenderService;
  @Autowired
  private VerificationTokenRepository tok;
  @Autowired
  private UserService userservice;
  @Autowired
  private UserDetailsService service;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    // Check if the user is enabled
    if (!userDetails.isEnabled()) {
      return ResponseEntity.badRequest().body("User is disabled");
    }

    String jwt = jwtUtils.generateJwtToken(authentication);

    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
  }
  @GetMapping("/user")
  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    Optional<User> userOptional = userRepository.findByUsername(username);
    User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return new User(user.getRoles());  }



  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest ) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));
    user.setPhone(signUpRequest.getPhone());

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
          case "fournisseur":
            Role fournisseur = roleRepository.findByName(ERole.ROLE_FOURNISSEUR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(fournisseur);

            break;
          case "restaurant":
            Role restaurant = roleRepository.findByName(ERole.ROLE_RESTAURANT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(restaurant);

            break;
          default:
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }
    user.setEnabled(true);

    user.setRoles(roles);
    userRepository.save(user);

    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = new VerificationToken(token, user);
    tok.save(verificationToken);
    String confirmationUrl = "http://localhost:8080/api/auth/confirm-account?token=" + verificationToken.getToken();; // add token to URL

    emailSenderService.sendVerificationEmail(user, verificationToken,confirmationUrl);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  

  @GetMapping("/userObjects")
  public User getCurrentUserObjects() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    Optional<User> userOptional = userRepository.findByUsername(username);
    User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return new User(user.getId());
    //return user;
  }
  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new MessageResponse("You've been signed out!"));
  }

  @PostMapping("/confirm-account")
  public ResponseEntity<?> confirmAccount(@RequestParam("token") String token) {
    VerificationToken verificationToken = tok.findByToken(token);

    if (verificationToken == null) {
      return ResponseEntity.badRequest().body("Invalid token");
    }

    User user = verificationToken.getUser();
    user.setEnabled(true);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("Account confirmed successfully!"));
  }
  @GetMapping("/user-role")
  public String getUserRole(Principal principal) {
    UserDetails user = service.loadUserByUsername(principal.getName());
    Set<ERole> roles = user.getAuthorities().stream()
            .map(authority -> ERole.valueOf(authority.getAuthority()))
            .collect(Collectors.toSet());
    if (roles.contains(ERole.ROLE_ADMIN)) {
      return "admin";
    } else if (roles.contains(ERole.ROLE_USER)) {
      return "user";
    } else {
      return "unknown";
    }
  }


}

