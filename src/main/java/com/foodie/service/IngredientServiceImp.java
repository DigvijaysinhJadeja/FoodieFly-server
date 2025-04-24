package com.foodie.service;

import com.foodie.model.IngredientCategory;
import com.foodie.model.IngredientItem;
import com.foodie.model.Restaurant;
import com.foodie.repository.IngredientCategoryRepository;
import com.foodie.repository.IngredientItemRepository;
import com.foodie.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredientService {

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory category = new IngredientCategory();
        category.setRestaurant(restaurant);
        category.setName(name);
        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientCategory FindIngredientCategoryById(Long id) throws Exception {

        Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(id);

        if (opt.isEmpty()) {
            throw new Exception("Ingredient category not found");
        }
        return opt.get();
    }

    @Override
    public List<IngredientCategory> FindIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id); // if find by id not found the service will automatically throw exception
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientItem createIngredientItem(Long RestaurantId, String ingredientName, Long CategoryId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(RestaurantId);
        IngredientCategory category = FindIngredientCategoryById(CategoryId);

        IngredientItem item = new IngredientItem();
        item.setRestaurant(restaurant);
        item.setName(ingredientName);
        item.setCategory(category);

        IngredientItem ingredient = ingredientItemRepository.save(item);
        category.getIngredients().add(ingredient);
        return ingredient;
    }

    @Override
    public List<IngredientItem> FindRestaurantsIngredients(Long RestaurantId) {
        return ingredientItemRepository.findByRestaurantId(RestaurantId);
    }

    @Override
    public IngredientItem UpdateStock(Long id) throws Exception {
        Optional<IngredientItem> optional = ingredientItemRepository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Ingredient item not found");
        }
        IngredientItem ingredientItem = optional.get();

        ingredientItem.setInStock(!ingredientItem.isInStock());
        return ingredientItemRepository.save(ingredientItem);
    }
}
