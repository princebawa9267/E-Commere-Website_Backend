package com.e_commerce.E_Commere.Website.service.impl;

import com.e_commerce.E_Commere.Website.Model.Product;
import com.e_commerce.E_Commere.Website.Model.User;
import com.e_commerce.E_Commere.Website.Model.Wishlist;
import com.e_commerce.E_Commere.Website.repository.WishListRepository;
import com.e_commerce.E_Commere.Website.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;

    @Override
    public Wishlist createWishList(User user) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        return wishListRepository.save(wishlist);
    }

    @Override
    public Wishlist getWishListByUserId(User user) {
        Wishlist wishlist = wishListRepository.findByUserId(user.getId());
        if(wishlist == null){
            wishlist = createWishList(user);
        }
        return wishlist;
    }

    @Override
    public Wishlist addProductToWishList(User user, Product product) {
        Wishlist wishlist = getWishListByUserId(user);

        if(wishlist.getProducts().contains(product)){
            wishlist.getProducts().remove(product);
        }
        else{
            wishlist.getProducts().add(product);
        }
        return wishListRepository.save(wishlist);
    }
}
