package com.esprit.pidev.security.services;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;

@Service
@Slf4j
public class SMSService {
    @Value("AC69fb4b26547f84de0d9c54e242c25ee9")
    String ACCOUNT_SID;
    @Value("751fe02df0c84b4c61a1b350b5d9e444")
    String AUTH_TOKEN;
    @Value("+1 681 384 6072")
    String SMS_NUMBER;

    @PostConstruct
    private void setup()
    {
        // log.info("SID : "+ ACCOUNT_SID);
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
    }
    public String sendSMS(String smsnumber,String msg)
    {
        Message message=Message.creator(
                new PhoneNumber(smsnumber),
                new PhoneNumber(SMS_NUMBER),
                msg).create();

        return message.getStatus().toString();
    }
    public static String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        String SmsCode = Integer.toString(code);
        return SmsCode;
    }
}
