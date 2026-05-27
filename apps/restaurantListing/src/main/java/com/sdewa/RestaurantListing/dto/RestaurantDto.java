package com.sdewa.RestaurantListing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {
    private long id;
    private String name;
    private String address;
    private String cuisineType;
    private String city;
}
