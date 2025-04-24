package com.foodie.repository;


import com.foodie.model.Order;
import com.foodie.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    public List<Order> findBycustomer_id(Long userId);

    public List<Order> findByrestaurant_id(Long restaurantId);

}
