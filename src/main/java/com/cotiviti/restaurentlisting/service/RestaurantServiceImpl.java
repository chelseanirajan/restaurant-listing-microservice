package com.cotiviti.restaurentlisting.service;

import com.cotiviti.restaurentlisting.dto.RestaurantDTO;
import com.cotiviti.restaurentlisting.entity.Restaurant;
import com.cotiviti.restaurentlisting.mapper.RestaurantMapper;
import com.cotiviti.restaurentlisting.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<RestaurantDTO> fetchAllRestaurant() {
        List<Restaurant> allRestaurant = restaurantRepository.findAll();
        return allRestaurant.stream().map(RestaurantMapper.INSTANCE::getRestaurantDTOFromRestaurant).collect(Collectors.toList());
    }

    @Override
    public RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.save(RestaurantMapper.INSTANCE.getRestaurantFromRestaurantDTO(restaurantDTO));
        return RestaurantMapper.INSTANCE.getRestaurantDTOFromRestaurant(restaurant);
    }

    @Override
    public ResponseEntity<RestaurantDTO> getRestaurantById(int id) {
        Optional<Restaurant> byId = restaurantRepository.findById(id);
        if (byId.isPresent()) {
            RestaurantDTO restaurantDTO = RestaurantMapper.INSTANCE.getRestaurantDTOFromRestaurant(byId.get());
            return new ResponseEntity<>(restaurantDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
