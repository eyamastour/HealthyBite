package com.esprit.pidev.payload.request;

import lombok.Data;

@Data
public class SMSSendRequest {
    String phone;
    String message;
}
