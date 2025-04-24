package com.foodie.controller;

import com.foodie.model.Cart;
import com.foodie.model.CartItem;
import com.foodie.model.User;
import com.foodie.request.AddCartItemRequest;
import com.foodie.request.UpdateCartItemRequest;
import com.foodie.service.CartService;
import com.foodie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;


    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {

        CartItem cartItem = cartService.addItemToCart(req,jwt);

        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
     }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {

        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());

        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart  > RemoveCartItem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {

        Cart cart = cartService.RemoveItemFromCart(id,jwt);

        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart>clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.clearCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart>findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.FindCartByUserId(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }


}
