package com.cotiviti.restaurentlisting.service;


import com.cotiviti.restaurentlisting.dto.RestaurantDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantService {
    public List<RestaurantDTO> fetchAllRestaurant();
    public RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO);
    public ResponseEntity<RestaurantDTO> getRestaurantById(int id);
}
