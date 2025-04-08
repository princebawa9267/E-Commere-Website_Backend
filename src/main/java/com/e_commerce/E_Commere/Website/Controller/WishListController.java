package com.e_commerce.E_Commere.Website.Controller;

import com.e_commerce.E_Commere.Website.Exceptions.ProductException;
import com.e_commerce.E_Commere.Website.Model.Product;
import com.e_commerce.E_Commere.Website.Model.User;
import com.e_commerce.E_Commere.Website.Model.Wishlist;
import com.e_commerce.E_Commere.Website.repository.WishListRepository;
import com.e_commerce.E_Commere.Website.service.ProductService;
import com.e_commerce.E_Commere.Website.service.UserService;
import com.e_commerce.E_Commere.Website.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishListController {

    private final WishListService wishListService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<Wishlist> getWishListByUserId(
            @RequestHeader("Authorization") String jwt){
        User user = userService.findUserByJWTToken(jwt);
        Wishlist wishlist = wishListService.getWishListByUserId(user);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Wishlist> addProductToWishlist(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt) throws ProductException {
        Product product = productService.findProductById(productId);
        User user = userService.findUserByJWTToken(jwt);
        Wishlist updateWishList = wishListService.addProductToWishList(user,product);
        return ResponseEntity.ok(updateWishList);
    }


}
