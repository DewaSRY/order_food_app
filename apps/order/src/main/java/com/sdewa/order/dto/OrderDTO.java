package com.sdewa.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Integer orderId;
    private List<FoodItemsDTO> foodItemsList;
    private RestaurantDTO restaurant;
    private UserDTO userDTO;
}
