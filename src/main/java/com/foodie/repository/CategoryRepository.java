package com.foodie.repository;

import com.foodie.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    public List<Category> findByRestaurantId(Long id);// using id it will find all the category of the Restaurant.

}
