package com.e_commerce.E_Commere.Website.repository;

import com.e_commerce.E_Commere.Website.Model.Cart;
import com.e_commerce.E_Commere.Website.Model.CartItem;
import com.e_commerce.E_Commere.Website.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long>  {
    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
