package com.foodie.request;

import com.foodie.model.Category;
import com.foodie.model.IngredientItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images; // this stores the url of the images which will be provided by cloudnary

    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;

    private List<IngredientItem> ingredients;

}
