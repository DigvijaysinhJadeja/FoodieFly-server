package com.foodie.service;

import com.foodie.model.Category;
import com.foodie.model.Food;
import com.foodie.model.Restaurant;
import com.foodie.repository.FoodRepository;
import com.foodie.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImp implements FoodService {

    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private FilterChainProxy filterChainProxy;

    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setSeasonal(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());
        food.setIngredients(req.getIngredients());
        food.setCreationDate(new Date());

        Food savedFood =  foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;

    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);

        // here we are removing restaurant from the food.

    }

    @Override
    public List<Food> getRestaurantFood(Long RestaurantId,
                                        boolean isVegetarian,
                                        boolean isNonveg,
                                        boolean isSeasonal,
                                        String FoodCategory) {


        List<Food> foods = foodRepository.findByRestaurantId(RestaurantId); // finding list of all food in restaurant

        if (isVegetarian) {
            foods=filterByVegetarian(foods,isVegetarian) ;
        }
        if(isNonveg){
            foods = filterByNonVeg(foods,isNonveg);
        }
        if(isSeasonal){
            foods = filterBySeasonal(foods,isSeasonal);
        }
        if(FoodCategory!=null && !FoodCategory.equals("")){
            foods = filterByFoodCategory(foods,FoodCategory);
        }
        return foods;

    }

    // whenever we need to apply filter we use stream.
    // -> (this sign is lambda sign
    private List<Food> filterByFoodCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food ->{ // name of this food should be equal to name of foodCategory(return true);
            if(food.getFoodCategory() != null){
                return food.getFoodCategory().getName().equalsIgnoreCase(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }


    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {

        return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> food.isVegetarian()==false).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {

        return foods.stream().filter(food -> food.isVegetarian()==isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if(optionalFood.isEmpty()){
            throw new Exception("Food Does not exist....");

        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailabiltyStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
