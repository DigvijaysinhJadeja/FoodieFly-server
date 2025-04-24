package com.foodie.service;

import com.foodie.model.IngredientCategory;
import com.foodie.model.IngredientItem;

import java.util.List;

public interface IngredientService {

    public IngredientCategory createIngredientCategory(String name,Long RestaurantId)throws Exception;

    public IngredientCategory FindIngredientCategoryById(Long id)throws Exception;

    public List<IngredientCategory> FindIngredientCategoryByRestaurantId(Long id)throws Exception;

    public IngredientItem createIngredientItem(Long RestaurantId,String ingredientName,Long CategoryId) throws Exception;

    public List<IngredientItem> FindRestaurantsIngredients(Long RestaurantId);
    //by providing restaurant id the list will provide all the ingredients

    public IngredientItem UpdateStock(Long id)throws Exception;


}
