package com.e_commerce.E_Commere.Website.service;

import com.e_commerce.E_Commere.Website.Model.Product;
import com.e_commerce.E_Commere.Website.Model.User;
import com.e_commerce.E_Commere.Website.Model.Wishlist;

public interface WishListService {
    Wishlist createWishList(User user);
    Wishlist getWishListByUserId(User user);
    Wishlist addProductToWishList(User user, Product product);
}
