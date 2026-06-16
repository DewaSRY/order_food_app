package com.sdewa.foodCatalogue.mapper;

import org.mapstruct.Mapper;

import com.sdewa.foodCatalogue.dto.FoodItemDTO;
import com.sdewa.foodCatalogue.entity.FoodItemEntity;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FoodItemMapper {

    FoodItemMapper INSTANCE = Mappers.getMapper(FoodItemMapper.class);

    FoodItemDTO toDto(FoodItemEntity foodItem);
    
    FoodItemEntity toEntity(FoodItemDTO foodItemDTO);
}
