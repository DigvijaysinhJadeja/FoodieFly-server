package com.foodie.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Long price;

    @ManyToOne
    private Category foodCategory;

    @Column(length =  1000)
    @ElementCollection // this will create a different table for food images
    private List<String> images;

    private boolean available;

    @ManyToOne
    private Restaurant restaurant; // which restaurant can provide the food

    private boolean isVegetarian;
    private boolean isSeasonal;

    @ManyToMany // multiple foods have same ingredient as well as multiple ingredient have same food
    private List<IngredientItem> ingredients = new ArrayList<>();

    private Date creationDate;

}
