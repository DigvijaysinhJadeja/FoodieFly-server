package com.foodie.service;

import com.foodie.model.Cart;
import com.foodie.model.CartItem;
import com.foodie.request.AddCartItemRequest;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest req, String jwt)throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity)throws Exception;
    //which cartItem item you want to update and how many quantity to update.

    public Cart RemoveItemFromCart(Long cartItemId, String jwt)throws Exception;

    public Long CalculateCartTotals(Cart cart)throws Exception;

    public Cart findCartById(Long id)throws Exception;

    public Cart FindCartByUserId(long userId)throws Exception;

    public Cart clearCart(long userId)throws Exception;
}
