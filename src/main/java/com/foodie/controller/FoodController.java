package com.foodie.controller;

import com.foodie.model.Food;
import com.foodie.model.Restaurant;
import com.foodie.model.User;
import com.foodie.request.CreateFoodRequest;
import com.foodie.service.FoodService;
import com.foodie.service.RestaurantService;
import com.foodie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.searchFood (name);

        return new ResponseEntity<>(foods, HttpStatus.CREATED) ;
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestParam(required = false) boolean vegetarian ,
            @RequestParam(required = false) boolean seasonal ,
            @RequestParam(required = false) boolean nonveg ,
            @RequestParam(required = false) String food_category,
            @PathVariable long restaurantId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.getRestaurantFood(restaurantId,vegetarian,seasonal,nonveg,food_category);

        return new ResponseEntity<>(foods, HttpStatus.OK) ;
    }
}
