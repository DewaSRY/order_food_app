package com.sdewa.RestaurantListing.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sdewa.RestaurantListing.dto.RestaurantDto;
import com.sdewa.RestaurantListing.repository.RestaurantRepository;
import com.sdewa.RestaurantListing.entity.RestaurantEntity;
import com.sdewa.RestaurantListing.mapstruct.Mapstruct;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantServices {

    private final RestaurantRepository restaurantRepository;

    public List<RestaurantDto> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(Mapstruct.INSTANCE::restaurantToDto)
                .toList();
    }

    public RestaurantDto getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .map(Mapstruct.INSTANCE::restaurantToDto)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }

    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        RestaurantEntity entity = Mapstruct.INSTANCE.restaurantToEntity(restaurantDto);
        entity.setId(null);
        RestaurantEntity saved = restaurantRepository.save(entity);
        return Mapstruct.INSTANCE.restaurantToDto(saved);
    }

    public RestaurantDto updateRestaurant(Long id, RestaurantDto restaurantDto) {
        RestaurantEntity entity = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
        entity.setName(restaurantDto.getName());
        entity.setAddress(restaurantDto.getAddress());
        entity.setCuisineType(restaurantDto.getCuisineType());
        entity.setCity(restaurantDto.getCity());
        RestaurantEntity updated = restaurantRepository.save(entity);
        return Mapstruct.INSTANCE.restaurantToDto(updated);
    }

    public void deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new RuntimeException("Restaurant not found with id: " + id);
        }
        restaurantRepository.deleteById(id);
    }
}
