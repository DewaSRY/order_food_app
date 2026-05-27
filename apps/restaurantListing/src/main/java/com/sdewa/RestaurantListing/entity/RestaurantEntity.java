package com.sdewa.RestaurantListing.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantEntity {
    private Long id;
    private String name;
    private String address;
    private String cuisineType;
    private String city;
}