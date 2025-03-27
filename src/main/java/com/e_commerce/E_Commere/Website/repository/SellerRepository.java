package com.e_commerce.E_Commere.Website.repository;

import com.e_commerce.E_Commere.Website.Domain.AccountStatus;
import com.e_commerce.E_Commere.Website.Model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findByEmail(String email);
    List<Seller> findByAccountStatus(AccountStatus status);
}
