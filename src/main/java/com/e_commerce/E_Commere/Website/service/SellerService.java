package com.e_commerce.E_Commere.Website.service;

import com.e_commerce.E_Commere.Website.Domain.AccountStatus;
import com.e_commerce.E_Commere.Website.Exceptions.SellerException;
import com.e_commerce.E_Commere.Website.Model.Seller;
import com.e_commerce.E_Commere.Website.repository.SellerRepository;

import java.util.List;

public interface SellerService {
    Seller getSellerProfile(String jwt);
    Seller createSeller(Seller seller) throws Exception;
    Seller getSellerById(Long id) throws SellerException;
    Seller getSellerByEmail(String email);
    List<Seller> getAllSellers(AccountStatus accountStatus);
    Seller updateSeller(Long id, Seller seller) throws Exception;
    void deleteSeller(Long id) throws Exception;
    Seller verifyEmail(String email, String otp);
    Seller updateSellerAccountStatus(Long id,AccountStatus status) throws Exception;
}
