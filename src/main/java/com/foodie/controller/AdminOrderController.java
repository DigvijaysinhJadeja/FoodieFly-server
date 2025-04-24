package com.foodie.controller;

import com.foodie.model.Order;
import com.foodie.model.User;
import com.foodie.request.OrderRequest;
import com.foodie.service.OrderService;
import com.foodie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/order/restaurant/{restaurantId}")
    public ResponseEntity<List<Order>> getOrderHistory(
                                                       @PathVariable Long restaurantId,
                                                       @RequestParam (required = false) String orderStatus,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getRestaurantsOrder(restaurantId,orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@RequestBody OrderRequest req,
                                                       @PathVariable Long orderId,
                                                       @PathVariable String orderStatus,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order orders = orderService.updateOrder(orderId,orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.CREATED);

    }
}
