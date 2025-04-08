package com.e_commerce.E_Commere.Website.service.impl;

import com.e_commerce.E_Commere.Website.Model.CartItem;
import com.e_commerce.E_Commere.Website.Model.User;
import com.e_commerce.E_Commere.Website.repository.CartItemRepository;
import com.e_commerce.E_Commere.Website.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;


    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws Exception {
        CartItem item = findCartItemById(id);

        User cartItemUser = item.getCart().getUser();

        if(cartItemUser.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setMrpPrice(item.getQuantity() * item.getProduct().getMrpPrice());
            item.setSellingPrice(item.getQuantity() * item.getProduct().getSellingPrice());
            return cartItemRepository.save(item);
        }
        throw new Exception("You can't update this cart items");
    }

    @Override
    public void removeCartItems(Long userId, Long cartItemId) throws Exception {
        CartItem item = findCartItemById(cartItemId);
        User cartItemUser = item.getCart().getUser();

        if(cartItemUser.getId().equals(userId)) {
            cartItemRepository.delete(item);
        }
        else throw new Exception("You can't delete item from cart");
    }

    @Override
    public CartItem findCartItemById(Long id) throws Exception {
        return cartItemRepository.findById(id).orElseThrow(
                ()-> new Exception("Cart item not found with id "+id)
        );
    }
}
