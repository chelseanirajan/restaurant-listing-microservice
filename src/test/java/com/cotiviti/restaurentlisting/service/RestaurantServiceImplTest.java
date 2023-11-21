package com.cotiviti.restaurentlisting.service;

import com.cotiviti.restaurentlisting.dto.RestaurantDTO;
import com.cotiviti.restaurentlisting.entity.Restaurant;
import com.cotiviti.restaurentlisting.mapper.RestaurantMapper;
import com.cotiviti.restaurentlisting.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


 class RestaurantServiceImplTest {

    @InjectMocks
    RestaurantServiceImpl restaurantService;

    @Mock
    RestaurantRepository restaurantRepository;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testFetchAllRestaurant() {
        List<Restaurant> restaurant = Arrays.asList(
                        new Restaurant(1, "Restaurant 1", "This is test", "Roanoke 1", "test1"),
                        new Restaurant(2, "Restaurant 2", "This is test2", "Roanoke 2", "test2"));
      when(restaurantRepository.findAll()).thenReturn(restaurant);
        List<RestaurantDTO> restaurantDTOS = restaurantService.fetchAllRestaurant();
        assertEquals(restaurant.size(), restaurantDTOS.size());
        for(int i=0;i<restaurant.size();i++){
            RestaurantDTO restaurantDTO = RestaurantMapper.INSTANCE.getRestaurantDTOFromRestaurant(restaurant.get(i));
            assertEquals(restaurantDTO, restaurantDTOS.get(i));
        }
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
     void testAddRestaurant(){
        RestaurantDTO restaurantdto = new RestaurantDTO(1, "Restaurant 1", "This is test", "Roanoke 1", "test1");

        Restaurant restaurant = RestaurantMapper.INSTANCE.getRestaurantFromRestaurantDTO(restaurantdto);
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        RestaurantDTO restaurantDTO1 = restaurantService.addRestaurant(restaurantdto);

        assertEquals(restaurantdto, restaurantDTO1);
        verify(restaurantRepository, times(1)).save(restaurant);

    }
    @Test
     void testGetRestaurantById(){
        int id = 1;
        Restaurant restaurant = new Restaurant(1, "Restaurant 1", "This is test", "Roanoke 1", "test1");

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));

        ResponseEntity<RestaurantDTO> response = restaurantService.getRestaurantById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        verify(restaurantRepository, times(1)).findById(id);
    }
    @Test
     void testGetRestaurantByIdNotExisting(){
        int id = 1;
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());
        ResponseEntity<RestaurantDTO> response = restaurantService.getRestaurantById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(restaurantRepository, times(1)).findById(id);
    }


}
