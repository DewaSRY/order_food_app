package com.sdewa.foodCatalogue.mapper;

import org.mapstruct.Mapper;

import com.sdewa.foodCatalogue.dto.FoodItemDTO;
import com.sdewa.foodCatalogue.entity.FoodItemEntity;

@Mapper
public interface FoodItemMapper {
    
    FoodItemDTO toDto(FoodItemEntity foodItem);
    
    FoodItemEntity toEntity(FoodItemDTO foodItemDTO);
}
