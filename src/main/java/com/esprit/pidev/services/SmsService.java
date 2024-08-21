package com.esprit.pidev.services;

import com.esprit.pidev.constants.HealthyBiteBackConstants;
import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.security.services.UserService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {


    private final UserService userService;

    @Autowired
    public SmsService(UserService userService) {
        this.userService = userService;
    }

    public void sendSms(Long userId, String messageBody) {

        try {
            User user = userService.retrieveUserById(userId);


            System.out.println("------------------------------");
            String s = System.getenv("TWILIO_ACCOUNT_SID");
            System.out.println("TWILIO_ACCOUNT_SID"+s);
            System.out.println("------------------------------");
            String d = System.getenv("TWILIO_AUTH_TOKEN");
            System.out.println("TWILIO_AUTH_TOKEN"+d);
            System.out.println("------------------------------");
            Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
            Message
                    .creator(
                            new PhoneNumber("+21655592142"),
                            new PhoneNumber("+16206709679"),
                            messageBody != null ? messageBody: "HealthyBiteBackConstants.TWILIO_MESSAGE_BODY"
                    ).create(

                    );

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Error e) {
            System.out.println(e.getMessage());
            throw new Error(e);
        }


    }



}
