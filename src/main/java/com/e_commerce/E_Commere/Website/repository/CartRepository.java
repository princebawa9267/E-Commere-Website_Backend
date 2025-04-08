package com.e_commerce.E_Commere.Website.repository;

import com.e_commerce.E_Commere.Website.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long id);
}
