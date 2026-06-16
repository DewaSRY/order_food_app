package com.sdewa.order.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sdewa.order.entity.OrderEntity;

@Repository
public interface OrderRepo  extends MongoRepository<OrderEntity, String> {
    
}
