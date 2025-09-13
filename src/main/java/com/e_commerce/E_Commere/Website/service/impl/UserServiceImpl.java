package com.e_commerce.E_Commere.Website.service.impl;

import com.e_commerce.E_Commere.Website.Config.JwtProvider;
import com.e_commerce.E_Commere.Website.Model.Address;
import com.e_commerce.E_Commere.Website.Model.User;
import com.e_commerce.E_Commere.Website.repository.AddressRepository;
import com.e_commerce.E_Commere.Website.repository.UserRepository;
import com.e_commerce.E_Commere.Website.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class  UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final AddressRepository addressRepository;


    @Override
    public User findUserByJWTToken(String jwt) {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("User not found exception with email:-" + email);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found exception with email:-"+email);
        }
        return user;
    }

    @Override
    public void addAddress(User user, Address address) {
        addressRepository.save(address); // Save address first
        user.getAddresses().add(address); // Add to user
        userRepository.save(user);
    }
}
