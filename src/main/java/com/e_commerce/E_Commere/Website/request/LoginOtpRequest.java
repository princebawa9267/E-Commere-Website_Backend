package com.e_commerce.E_Commere.Website.request;

import com.e_commerce.E_Commere.Website.Domain.UserRole;
import lombok.Data;

@Data
public class LoginOtpRequest {
    private String email;
    private String otp;
    private UserRole role;
}
