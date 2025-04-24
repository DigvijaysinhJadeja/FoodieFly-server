package com.foodie.service;

import com.foodie.model.Category;
import com.foodie.model.Food;
import com.foodie.model.Restaurant;
import com.foodie.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public  Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantFood(Long RestaurantId,
                                        boolean isVegetarian,
                                        boolean isNonveg,
                                        boolean isSeasonal,
                                        String FoodCategory
    ); // if user wants to filter the food on this criteria.

    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long foodId) throws Exception;
    public Food updateAvailabiltyStatus(Long foodId) throws Exception;



}
