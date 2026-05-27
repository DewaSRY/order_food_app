package com.sdewa.RestaurantListing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdewa.RestaurantListing.entity.RestaurantEntity;



@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    
}

