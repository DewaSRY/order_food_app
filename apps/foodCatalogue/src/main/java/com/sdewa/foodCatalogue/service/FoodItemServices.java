package com.sdewa.foodCatalogue.service;

import org.springframework.stereotype.Service;

import com.sdewa.foodCatalogue.repo.FoodItemRepo;
import com.sdewa.foodCatalogue.dto.FoodItemDTO;
import com.sdewa.foodCatalogue.entity.FoodItemEntity;
import com.sdewa.foodCatalogue.mapper.FoodItemMapper;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FoodItemServices {
    private final FoodItemRepo foodItemRepo;
    private final FoodItemMapper foodItemMapper;

    public List<FoodItemDTO> getAllFoodItems() {
        return foodItemRepo.findAll().stream()
                .map(foodItemMapper::toDto)
                .collect(Collectors.toList());
    }

    public FoodItemDTO getFoodItemById(Long id) {
        Optional<FoodItemEntity> entity = foodItemRepo.findById(id);
        return entity.map(foodItemMapper::toDto).orElse(null);
    }

    @Transactional
    public FoodItemDTO createFoodItem(FoodItemDTO foodItemDTO) {
        FoodItemEntity entity = foodItemMapper.toEntity(foodItemDTO);
        entity.setId(0); // Ensure new entity
        FoodItemEntity saved = foodItemRepo.save(entity);
        return foodItemMapper.toDto(saved);
    }

    @Transactional
    public FoodItemDTO updateFoodItem(Long id, FoodItemDTO foodItemDTO) {
        Optional<FoodItemEntity> optionalEntity = foodItemRepo.findById(id);
        if (optionalEntity.isPresent()) {
            FoodItemEntity entity = optionalEntity.get();
            
            entity.setItemName(foodItemDTO.getItemName());
            entity.setItemDescription(foodItemDTO.getItemDescription());
            entity.setVeg(foodItemDTO.isVeg());
            entity.setPrice(foodItemDTO.getPrice());
            entity.setRestaurantId(foodItemDTO.getRestaurantId());

            entity.setQuantity(foodItemDTO.getQuantity());
            FoodItemEntity updated = foodItemRepo.save(entity);
            
            return foodItemMapper.toDto(updated);
        }
        return null;
    }

    @Transactional
    public boolean deleteFoodItem(Long id) {
        if (foodItemRepo.existsById(id)) {
            foodItemRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
