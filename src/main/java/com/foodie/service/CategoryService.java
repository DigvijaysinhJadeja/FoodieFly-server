package com.foodie.service;

import com.foodie.model.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(String name,Long userId) throws Exception;
    // name of the category and using user id we will be able to find the restaurant for which we need to create category

    public List<Category> findCategoryByRestaurantId(Long id)throws Exception;

    public Category findCategoryById(Long id)throws Exception;

}
