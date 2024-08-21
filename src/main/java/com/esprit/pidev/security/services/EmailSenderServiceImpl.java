package com.esprit.pidev.security.services;


import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.entities.UserRole.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;




    @Override
    public void sendVerificationEmail(User user, VerificationToken verificationToken, String confirmationUrl) {
        // Create a new SimpleMailMessage object
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // Set the email's sender
        mailMessage.setFrom(emailFrom);
        // Set the email's recipient
        mailMessage.setTo(user.getEmail());
        // Set the email's subject
        mailMessage.setSubject("Complete Registration!");
        // Set the email's content with the confirmationUrl that contains the verification token
        mailMessage.setText("To confirm your account, please click here : " + confirmationUrl);

        // Send the email message using the configured JavaMailSender
        mailSender.send(mailMessage);
    }


}
