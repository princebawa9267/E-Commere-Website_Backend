package com.e_commerce.E_Commere.Website.service.impl;

import com.e_commerce.E_Commere.Website.Model.Cart;
import com.e_commerce.E_Commere.Website.Model.CartItem;
import com.e_commerce.E_Commere.Website.Model.Product;
import com.e_commerce.E_Commere.Website.Model.User;
import com.e_commerce.E_Commere.Website.repository.CartItemRepository;
import com.e_commerce.E_Commere.Website.repository.CartRepository;
import com.e_commerce.E_Commere.Website.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem addCardItem(User user, Product product, String size, int quantity) {
        Cart cart = findUserCart(user);

        CartItem isPresent = cartItemRepository.findByCartAndProductAndSize(cart,product,size);
            if(isPresent == null){
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setUserId(user.getId());
                cartItem.setSize(size);
                cartItem.setCart(cart);

                int totalSellingPrice = quantity * product.getSellingPrice();
                cartItem.setSellingPrice(totalSellingPrice);
                int totalMrpPrice = quantity * product.getMrpPrice();
                cartItem.setMrpPrice(totalMrpPrice);

                cart.getCartItems().add(cartItem);
                cartItem.setCart(cart);

                return cartItemRepository.save(cartItem);

            }

        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        System.out.println("MRP of item >>>>>>>>>>>>>>>> cartItem ");

        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice += Optional.ofNullable(cartItem.getMrpPrice()).orElse(0);
            totalDiscountedPrice += Optional.ofNullable(cartItem.getSellingPrice()).orElse(0) ;
            totalItem += cartItem.getQuantity();
        }

        cart.setTotalItem(totalItem);
        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalSellingPrice(totalDiscountedPrice);
        cart.setDiscount(totalItem>0 ?calculateDiscountPercentage(totalPrice, totalDiscountedPrice) : 0);
        return cart;
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if (mrpPrice <= 0) {
            throw new IllegalArgumentException("Actual price must be greater than 0");
        }
        double Percentage = ((mrpPrice - sellingPrice) / mrpPrice) * 100;
        return (int) Percentage;
    }
}
