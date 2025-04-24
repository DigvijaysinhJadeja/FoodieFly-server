package com.foodie.controller;


import com.foodie.model.Restaurant;
import com.foodie.model.User;
import com.foodie.request.CreateRestaurantRequest;
import com.foodie.response.MessageResponse;
import com.foodie.service.RestaurantService;
import com.foodie.service.UserService;
import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    //Creating a new Restaurant (API)
    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            //Using request header I am getting token
            @RequestHeader("Authorization")String jwt
            ) throws Exception {
        // getting user from jwt token
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.createRestaurant(req,user);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED );
    }

    //Updating a Restaurant(API)
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest req,
            //Using request header I am getting token
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long id
    ) throws Exception {
        // getting user from jwt token
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurant(id,req);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED );
    }

    //Delete a Restaurant(API)
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse>deleteRestaurant(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long id
    ) throws Exception {
        // getting user from jwt token
        User user = userService.findUserByJwtToken(jwt);
        restaurantService.deleteRestaurant(id);

        // we have used message response from response
        MessageResponse res = new MessageResponse();
        res.setMessage("Restaurant deleted Successfully");
        return new ResponseEntity<>(res,HttpStatus.OK );
    }

    //created a status update of a restaurant (API)
    @PutMapping("/{id}/status") // here id and status defines the end point of http
    public ResponseEntity<Restaurant> updateStatusRestaurant(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long id
    ) throws Exception {
        // getting user from jwt token
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user") // here user the end point of http
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        //getting user from jwt token
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
