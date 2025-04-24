package com.foodie.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ingredientItemRequest {

    private String name;

    @JsonProperty("ingredientCategoryId")
    private Long categoryId;

    private Long restaurantId;
}
