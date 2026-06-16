package com.sdewa.RestaurantListing.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.sdewa.RestaurantListing.dto.RestaurantDto;
import com.sdewa.RestaurantListing.entity.RestaurantEntity;

@Mapper
public interface Mapstruct {

    Mapstruct INSTANCE = Mappers.getMapper(Mapstruct.class);
    
    RestaurantDto restaurantToDto(RestaurantEntity restaurantEntity);
    RestaurantEntity restaurantToEntity(RestaurantDto restaurantDto);
}
