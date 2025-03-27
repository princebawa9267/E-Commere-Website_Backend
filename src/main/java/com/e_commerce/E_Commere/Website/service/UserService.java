package com.e_commerce.E_Commere.Website.service;

import com.e_commerce.E_Commere.Website.Model.User;

public interface UserService {
    User findUserByJWTToken(String jwt);
    User findUserByEmail(String email);

}
