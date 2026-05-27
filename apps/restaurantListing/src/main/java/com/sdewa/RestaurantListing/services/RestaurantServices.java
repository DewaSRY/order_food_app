package com.sdewa.RestaurantListing.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sdewa.RestaurantListing.dto.RestaurantDto;

import com.sdewa.RestaurantListing.repository.RestaurantRepository;
import com.sdewa.RestaurantListing.entity.RestaurantEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantServices {

    private final RestaurantRepository restaurantRepository;

    public List<RestaurantDto> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public RestaurantDto getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }

    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        RestaurantEntity entity = toEntity(restaurantDto);
        entity.setId(null);
        RestaurantEntity saved = restaurantRepository.save(entity);
        return toDto(saved);
    }

    public RestaurantDto updateRestaurant(Long id, RestaurantDto restaurantDto) {
        RestaurantEntity entity = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
        entity.setName(restaurantDto.getName());
        entity.setAddress(restaurantDto.getAddress());
        entity.setCuisineType(restaurantDto.getCuisineType());
        entity.setCity(restaurantDto.getCity());
        RestaurantEntity updated = restaurantRepository.save(entity);
        return toDto(updated);
    }

    public void deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new RuntimeException("Restaurant not found with id: " + id);
        }
        restaurantRepository.deleteById(id);
    }

    private RestaurantDto toDto(RestaurantEntity entity) {
        return RestaurantDto.builder()
                .id(entity.getId() != null ? entity.getId() : 0)
                .name(entity.getName())
                .address(entity.getAddress())
                .cuisineType(entity.getCuisineType())
                .city(entity.getCity())
                .build();
    }

    private RestaurantEntity toEntity(RestaurantDto dto) {
        return RestaurantEntity.builder()
                .id(dto.getId() != 0 ? dto.getId() : null)
                .name(dto.getName())
                .address(dto.getAddress())
                .cuisineType(dto.getCuisineType())
                .city(dto.getCity())
                .build();
    }
}
