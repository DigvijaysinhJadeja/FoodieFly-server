package com.foodie.controller;


import com.foodie.dto.RestaurantDto;
import com.foodie.model.Restaurant;
import com.foodie.model.User;
import com.foodie.request.CreateRestaurantRequest;
import com.foodie.service.RestaurantService;
import com.foodie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController  {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    //Searching a Restaurant (API)
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization")String jwt,
            @RequestParam String keyword
    ) throws Exception {
        // getting user from jwt token
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant > restaurant = restaurantService.searchRestaurant(keyword);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    //getting all restaurants using API Call
    @GetMapping()
    public ResponseEntity<List<Restaurant>> getALlRestaurant(
            @RequestHeader("Authorization")String jwt

    ) throws Exception {
        // getting user from jwt token
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant > restaurant = restaurantService.getAllRestaurants();

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    //Finding Restaruant By ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @RequestHeader("Authorization")String jwt,
            // need to access id from the path variable.
            @PathVariable Long id

    ) throws Exception {
        // getting user from jwt token
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK );
    }

    // Adding of Favorite restaurants
    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<Restaurant> addToFavorites(
            @RequestHeader("Authorization")String jwt,
            // need to access id from the path variable.
            @PathVariable Long id

    ) throws Exception {
        // getting user from jwt token
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.addToFavorites(id,user);

        return new ResponseEntity<>(restaurant, HttpStatus.OK );
    }
}
