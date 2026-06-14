package com.sdewa.foodCatalogue.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;

import com.sdewa.foodCatalogue.service.FoodItemServices;
import com.sdewa.foodCatalogue.dto.FoodItemDTO;

@RestController
@RequestMapping("/api/food-items")
@RequiredArgsConstructor
public class FoodItemController {
	private final FoodItemServices foodItemServices;

	@GetMapping
	public List<FoodItemDTO> getAllFoodItems() {
		return foodItemServices.getAllFoodItems();
	}

	@GetMapping("/{id}")
	public ResponseEntity<FoodItemDTO> getFoodItemById(@PathVariable Long id) {
		FoodItemDTO dto = foodItemServices.getFoodItemById(id);
		if (dto != null) {
			return ResponseEntity.ok(dto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<FoodItemDTO> createFoodItem(@RequestBody FoodItemDTO foodItemDTO) {
		FoodItemDTO created = foodItemServices.createFoodItem(foodItemDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<FoodItemDTO> updateFoodItem(@PathVariable Long id, @RequestBody FoodItemDTO foodItemDTO) {
		FoodItemDTO updated = foodItemServices.updateFoodItem(id, foodItemDTO);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
		boolean deleted = foodItemServices.deleteFoodItem(id);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
