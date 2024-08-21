package com.esprit.pidev.security.services;

import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.entities.UserRole.VerificationToken;

public interface EmailSenderService {
    void sendVerificationEmail(User user, VerificationToken verificationToken, String confirmationUrl);


}
