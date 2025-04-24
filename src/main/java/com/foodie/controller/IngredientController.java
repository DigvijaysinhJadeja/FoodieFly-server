package com.foodie.controller;

import com.foodie.model.IngredientCategory;
import com.foodie.model.IngredientItem;
import com.foodie.request.IngredientCategoryRequest;
import com.foodie.request.ingredientItemRequest;
import com.foodie.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")

public class IngredientController {

    @Autowired
    private IngredientService ingredientService;


    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req
    ) throws Exception {

        IngredientCategory item = ingredientService.createIngredientCategory(req.getName(), req.getRestaurantId());

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientItem> createIngredientItem(
            @RequestBody ingredientItemRequest req
    ) throws Exception {

        IngredientItem item = ingredientService.createIngredientItem(req.getRestaurantId(), req.getName(), req.getCategoryId());

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientItem> updateIngredientStoke(
            @PathVariable Long id
    ) throws Exception {

        IngredientItem item = ingredientService.UpdateStock(id);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientItem>> getRestaurantIngredient(
            @PathVariable Long id
    ) throws Exception {

        List<IngredientItem> items = ingredientService.FindRestaurantsIngredients(id);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantCategory(
            @PathVariable Long id
    ) throws Exception {

        List<IngredientCategory> categories = ingredientService.FindIngredientCategoryByRestaurantId(id);

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
