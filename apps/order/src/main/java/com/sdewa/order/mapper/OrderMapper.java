package com.sdewa.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.sdewa.order.dto.OrderDTO;
import com.sdewa.order.entity.OrderEntity;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    
    OrderDTO toDto(OrderEntity order);
    OrderEntity toEntity(OrderDTO orderDTO);
    
}
