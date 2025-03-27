package com.e_commerce.E_Commere.Website.service.impl;

import com.e_commerce.E_Commere.Website.Domain.UserRole;
import com.e_commerce.E_Commere.Website.Model.Seller;
import com.e_commerce.E_Commere.Website.Model.User;
import com.e_commerce.E_Commere.Website.repository.SellerRepository;
import com.e_commerce.E_Commere.Website.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomUserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private static final String SELLER_PREFIX = "seller_";
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.startsWith(SELLER_PREFIX)){
            String actualUsername = username.substring(SELLER_PREFIX.length());
            Seller seller = sellerRepository.findByEmail(actualUsername);

            if(seller!=null){
                return buildUserDetails(seller.getEmail(),seller.getPassword(),seller.getRole());
            }

        }
        else{
            User user = userRepository.findByEmail(username);
            if(user!=null){
                 return buildUserDetails(user.getEmail(), user.getPassword(),user.getRole() );
            }
        }
        throw new UsernameNotFoundException("user or seller not found with email - "+username);
    }

    private UserDetails buildUserDetails(String email, String password, UserRole role) {
        if(role == null) role = UserRole.USER_CUSTOMER;

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(role.toString()));

        return new org.springframework.security.core.userdetails.User(email,password,authorityList);
    }
}
