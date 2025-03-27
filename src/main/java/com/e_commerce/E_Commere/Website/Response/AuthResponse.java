package com.e_commerce.E_Commere.Website.Response;

import com.e_commerce.E_Commere.Website.Domain.UserRole;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserRole role;
}
