package com.foodie.controller;

import com.foodie.model.Food;
import com.foodie.model.Restaurant;
import com.foodie.model.User;
import com.foodie.request.CreateFoodRequest;
import com.foodie.response.MessageResponse;
import com.foodie.service.FoodService;
import com.foodie.service.RestaurantService;
import com.foodie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food>createFood(@RequestBody CreateFoodRequest req,
                                          @RequestHeader("Authorization") String jwt) throws Exception {
            User user = userService.findUserByJwtToken(jwt);
            Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
            Food food = foodService.createFood(req,req.getCategory(),restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED) ;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse>deleteFood(@PathVariable Long id,
                                                     @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        foodService.deleteFood(id);

        MessageResponse res = new MessageResponse();
        res.setMessage("Food deleted successfully...");

        return new ResponseEntity<>(res, HttpStatus.CREATED) ;
    }

    @PutMapping("{id}")
    public ResponseEntity<Food>updateFoodAvailabilityStatus(@PathVariable Long id,
                                                     @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Food food = foodService.updateAvailabiltyStatus(id);

        return new ResponseEntity<>(food, HttpStatus.OK) ;

    }


}
