package com.cotiviti.restaurentlisting.mapper;

import com.cotiviti.restaurentlisting.dto.RestaurantDTO;
import com.cotiviti.restaurentlisting.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);
    Restaurant getRestaurantFromRestaurantDTO(RestaurantDTO restaurantDTO);
    RestaurantDTO getRestaurantDTOFromRestaurant(Restaurant restaurant);
}
