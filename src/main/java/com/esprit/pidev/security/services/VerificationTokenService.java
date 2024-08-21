package com.esprit.pidev.security.services;

import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.entities.UserRole.VerificationToken;
import com.esprit.pidev.repository.UserRoleRepository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface VerificationTokenService {
    VerificationToken generateVerificationToken(User user);


}
