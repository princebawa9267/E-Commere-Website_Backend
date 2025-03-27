package com.e_commerce.E_Commere.Website.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String otp;
}
