package com.sdewa.foodCatalogue.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdewa.foodCatalogue.entity.FoodItemEntity;

@Repository
public interface FoodItemRepo extends JpaRepository<FoodItemEntity, Long>    {
    
}
