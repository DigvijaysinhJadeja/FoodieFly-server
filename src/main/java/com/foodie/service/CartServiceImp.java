package com.foodie.service;

import com.foodie.model.Cart;
import com.foodie.model.CartItem;
import com.foodie.model.Food;
import com.foodie.model.User;
import com.foodie.repository.CartItemRepository;
import com.foodie.repository.CartRepository;
import com.foodie.repository.FoodRepository;
import com.foodie.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private FoodServiceImp foodServiceImp;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Food food = foodServiceImp.findFoodById(req.getFoodId());


        Cart cart = cartRepository.findByCustomerId(user.getId());

        // the item already in cart so we will just update the quantity of the item inside cart
        for(CartItem cartItem : cart.getItem()){
            if(cartItem.getId().equals(food)){
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);

            }
        }

        CartItem newcartItem = new CartItem();

        newcartItem.setFood(food);
        newcartItem.setCart(cart);
        newcartItem.setQuantity(req.getQuantity());
        newcartItem.setIngredients(req.getIngredients());
        newcartItem.setTotalPrice(req.getQuantity() * food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(newcartItem);

        cart.getItem().add(savedCartItem); // after saving cart item into DB we need to save it in cart as well


        return savedCartItem;

     }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if(cartItem.isEmpty()){
            throw new Exception("Cart Item not found");
        }

        CartItem item  = cartItem.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);

        return cartItemRepository.save(item);
    }

    @Override
    public Cart RemoveItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if(cartItem.isEmpty()){
            throw new Exception("Cart Item not found");
        }

        CartItem item  = cartItem.get();

        cart.getItem().remove(item);

        return cartRepository.save(cart);
    }

    @Override
    public Long CalculateCartTotals(Cart cart) throws Exception {

        Long total = 0L;

        for(CartItem cartItem : cart.getItem()){

            total += cartItem.getFood().getPrice() * cartItem.getQuantity();

        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {

        Optional<Cart> optionalCart = cartRepository.findById(id);
        if(optionalCart.isEmpty()){
            throw new Exception("Cart not found with id: " + id);
        }
        return optionalCart.get();
    }

    @Override
    public Cart FindCartByUserId(long userId) throws Exception {

        Cart  cart= cartRepository.findByCustomerId(userId);
        cart.setTotal(CalculateCartTotals(cart));
        return cart;

    }

    @Override
    public Cart clearCart(long userId) throws Exception {


        Cart cart = findCartById(userId);
        cart.getItem().clear();

        return cartRepository.save(cart);
    }
}
