package com.esprit.pidev.RestController;

import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.services.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/sms")
public class SmsController {


    @Autowired
    private final SmsService smsService;

    @GetMapping(value = "/{userId}/sendSMS")
    public ResponseEntity<String> sendSMS(@PathVariable("userId")Long userId) {

        smsService.sendSms(userId,null);

        return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
    }

}
