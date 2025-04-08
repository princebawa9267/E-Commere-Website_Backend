package com.e_commerce.E_Commere.Website.service;

import com.e_commerce.E_Commere.Website.Model.Cart;
import com.e_commerce.E_Commere.Website.Model.CartItem;
import com.e_commerce.E_Commere.Website.Model.Product;
import com.e_commerce.E_Commere.Website.Model.User;

public interface CartService {
    public CartItem addCardItem(
            User user,
            Product product,
            String size,
            int quantity
    );
    public Cart findUserCart(User user);
}
