package com.foodie.service;

import com.foodie.dto.RestaurantDto;
import com.foodie.model.Restaurant;
import com.foodie.model.User;
import com.foodie.request.CreateRestaurantRequest;

import java.util.List;


public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    // fields will be same for createRestaurant and updateRestaurant so we have used CreateRestaurantRequest
    public Restaurant updateRestaurant (Long RestaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception ;

    
    public void deleteRestaurant (Long RestaurantId) throws Exception;


    // endpoint for only admin role (admin can only get all the restaurants)
    public List<Restaurant> getAllRestaurants();

    //if any body want to search restaurant by name he can do it from here
    public List<Restaurant> searchRestaurant(String keyword) ;

    // if restaurant not found with id it throws exception
    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant findRestaurantByUserId(Long UserId) throws Exception;

    public RestaurantDto addToFavorites(Long RestaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}


