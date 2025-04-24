package com.foodie.service;

import com.foodie.model.Order;
import com.foodie.model.User;
import com.foodie.request.OrderRequest;


import java.util.List;

public interface OrderService {

    public Order createOrder(OrderRequest order, User user) throws Exception;

    public com.foodie.model.Order updateOrder(Long orderId, String orderStatus)throws Exception;

    public void cancelOrder(long orderId) throws Exception;

    public List<Order> getUsersOrder(Long userId)throws Exception;

    public List<Order> getRestaurantsOrder(Long restaurantId,String orderStatus)throws Exception;

    public Order findOrderById(Long orderId)throws Exception;

}
