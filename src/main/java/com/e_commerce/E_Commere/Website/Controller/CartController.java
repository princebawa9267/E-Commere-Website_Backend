package com.e_commerce.E_Commere.Website.Controller;

import com.e_commerce.E_Commere.Website.Exceptions.ProductException;
import com.e_commerce.E_Commere.Website.Model.Cart;
import com.e_commerce.E_Commere.Website.Model.CartItem;
import com.e_commerce.E_Commere.Website.Model.Product;
import com.e_commerce.E_Commere.Website.Model.User;
import com.e_commerce.E_Commere.Website.Response.ApiResponse;
import com.e_commerce.E_Commere.Website.request.AddItemRequest;
import com.e_commerce.E_Commere.Website.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt){

        User user = userService.findUserByJWTToken(jwt);

        Cart cart = cartService.findUserCart(user);

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);

    }

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws ProductException {

        User user = userService.findUserByJWTToken(jwt);
        System.out.println(req);
        Product product = productService.findProductById(req.getProductId());
        CartItem item = cartService.addCardItem(
                user,
                product,
                req.getSize(),
                req.getQuantity()
        );

        ApiResponse res = new ApiResponse();
        res.setMessage("Item added to cart successfully");

        return new ResponseEntity<>(item,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJWTToken(jwt);
        cartItemService.removeCartItems(user.getId(),cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Item deleted from cart successfully");

        return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestBody CartItem cartItem,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJWTToken(jwt);

        CartItem updatedCartItem = null;
        if(cartItem.getQuantity()>0){
            updatedCartItem = cartItemService.updateCartItem(user.getId(),cartItemId,cartItem);
        }
        return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
    }

}
