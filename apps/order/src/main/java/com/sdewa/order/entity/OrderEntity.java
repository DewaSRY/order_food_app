package com.sdewa.order.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sdewa.order.dto.FoodItemsDTO;
import com.sdewa.order.dto.RestaurantDTO;
import com.sdewa.order.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    
    @Id
    private String id;
    private Integer orderId;
    private List<FoodItemsDTO> foodItemsList;
    private RestaurantDTO restaurant;
    private UserDTO userDTO;
}
