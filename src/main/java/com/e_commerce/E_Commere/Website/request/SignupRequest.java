package com.e_commerce.E_Commere.Website.request;

import lombok.Data;

@Data
public class SignupRequest{
    private String email;
    private String full_name;
    private String otp;
//    private String password;
//    private String address;
//    private String mobile;
}
