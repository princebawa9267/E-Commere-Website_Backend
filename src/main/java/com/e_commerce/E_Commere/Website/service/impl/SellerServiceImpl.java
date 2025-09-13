package com.e_commerce.E_Commere.Website.service.impl;

import com.e_commerce.E_Commere.Website.Config.JwtProvider;
import com.e_commerce.E_Commere.Website.Domain.AccountStatus;
import com.e_commerce.E_Commere.Website.Exceptions.SellerException;
import com.e_commerce.E_Commere.Website.Model.Address;
import com.e_commerce.E_Commere.Website.Model.Seller;
import com.e_commerce.E_Commere.Website.repository.AddressRepository;
import com.e_commerce.E_Commere.Website.repository.SellerRepository;
import com.e_commerce.E_Commere.Website.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Override
    public Seller getSellerProfile(String jwt) {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return sellerRepository.findByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {
        Seller sellerExist = sellerRepository.findByEmail(seller.getEmail());
        if(sellerExist != null){
            throw new Exception("Seller exist with email id");
        }
        Address address = addressRepository.save(seller.getPickupAddress());

        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(seller.getPassword());
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setPickupAddress(seller.getPickupAddress());
        newSeller.setMobile(seller.getSellerName());
        newSeller.setMobile(seller.getMobile());
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetail(seller.getBusinessDetail());
        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws SellerException {
        return sellerRepository.findById(id).orElseThrow(() -> new SellerException("Seller doesn't exist with id "+id));
    }

    @Override
    public Seller getSellerByEmail(String email) {
        Seller seller = sellerRepository.findByEmail(email);
        if(seller == null){
             throw new UsernameNotFoundException("Seller not found");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus accountStatus) {
        return sellerRepository.findByAccountStatus(accountStatus);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {
        Seller existingSeller = this.getSellerById(id);

        if(seller.getSellerName() != null){
            existingSeller.setSellerName(seller.getSellerName());
        }
        if(seller.getEmail() != null){
            existingSeller.setEmail(seller.getEmail());
        }
        if(seller.getPickupAddress() != null){
            existingSeller.setPickupAddress(seller.getPickupAddress());
        }
        if(seller.getGSTIN() != null){
            existingSeller.setGSTIN(seller.getGSTIN());
        }
        if(seller.getBankDetails() != null){
            existingSeller.setBankDetails(seller.getBankDetails());
        }
        if(seller.getMobile() != null){
            existingSeller.setMobile(seller.getMobile());
        }
        if(seller.getBusinessDetail() != null && seller.getBusinessDetail().getBusinessName() != null){
            existingSeller.getBusinessDetail().setBusinessName(seller.getBusinessDetail().getBusinessName());
        }
        if(seller.getBankDetails() != null && seller.getBankDetails().getAccountHolderName() != null && seller.getBankDetails().getAccountNumber() != null && seller.getBankDetails().getIfacCode() != null ){
            existingSeller.getBankDetails().setAccountNumber(seller.getBankDetails().getAccountNumber());
            existingSeller.getBankDetails().setIfacCode(seller.getBankDetails().getIfacCode());
            existingSeller.getBankDetails().setAccountHolderName(seller.getBankDetails().getAccountHolderName());
        }
        if(seller.getPickupAddress() !=null
        && seller.getPickupAddress().getAddress() != null
        && seller.getPickupAddress().getMobile() != null
        && seller.getPickupAddress().getCity() != null
        && seller.getPickupAddress().getLocality() != null
        ){
            existingSeller.getPickupAddress().setAddress(seller.getPickupAddress().getAddress());
            existingSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
            existingSeller.getPickupAddress().setMobile(seller.getPickupAddress().getMobile());
            existingSeller.getPickupAddress().setPinCode(seller.getPickupAddress().getPinCode());

        }

        if(seller.getGSTIN() != null){
            existingSeller.setGSTIN(seller.getGSTIN());
        }


        return sellerRepository.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) throws Exception {
        Seller seller = getSellerById(id);
        sellerRepository.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) {
        Seller seller = sellerRepository.findByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long id, AccountStatus status) throws Exception {
        Seller seller = getSellerById(id);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }
}
