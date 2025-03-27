package com.e_commerce.E_Commere.Website.service;

import com.e_commerce.E_Commere.Website.Domain.UserRole;
import com.e_commerce.E_Commere.Website.Response.AuthResponse;
import com.e_commerce.E_Commere.Website.request.LoginRequest;
import com.e_commerce.E_Commere.Website.request.SignupRequest;

public interface AuthService {
    void sentLoginOtp(String email, UserRole role) throws Exception;
    String createUser(SignupRequest req);
    AuthResponse signing(LoginRequest request) throws Exception;
}
