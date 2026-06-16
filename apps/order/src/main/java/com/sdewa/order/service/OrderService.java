package com.sdewa.order.service;

import org.springframework.stereotype.Service;

import com.sdewa.order.dto.OrderDTO;
import com.sdewa.order.dto.UserDTO;
import com.sdewa.order.mapper.OrderMapper;
import com.sdewa.order.repo.OrderRepo;

import lombok.RequiredArgsConstructor;

import com.sdewa.order.dto.OrderRequestDTO;
import com.sdewa.order.entity.OrderEntity;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepo orderRepo;

    private final SequenceGeneratorServices sequenceGeneratorServices;

    public OrderDTO saveOrderInDb(OrderRequestDTO orderDetails) {
        Long newOrderID = sequenceGeneratorServices.generateNextOrderId();
        UserDTO userDTO = null; //fetchUserDetailsFromUserId

        OrderEntity orderToBeSaved = OrderEntity.builder()
                .orderId(newOrderID.intValue())
                .foodItemsList(orderDetails.getFoodItemsList())
                .restaurant(orderDetails.getRestaurant())
                .userDTO(userDTO)
                .build();
       
        orderRepo.save(orderToBeSaved);
        return OrderMapper.INSTANCE.toDto(orderToBeSaved);
    }
    
}
