package com.foodie.dto;

import lombok.Data;
import java.util.List;

@Data
public class RestaurantDto {
    private String title;
    private List<String> images;
    private String description;
    private Long id;
}
