package com.cotiviti.restaurentlisting.controller;

import com.cotiviti.restaurentlisting.dto.RestaurantDTO;
import com.cotiviti.restaurentlisting.service.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RestaurantControllerTest {

    @InjectMocks
    RestaurantController restaurantController;

    @Mock
    RestaurantServiceImpl restaurantService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchAllRestaurant(){

        List<RestaurantDTO>  restaurantDTOS = Arrays
                .asList(
                        new RestaurantDTO(1, "Restaurant 1", "This is test", "Roanoke 1","test1"),
                        new RestaurantDTO(2, "Restaurant 2", "This is test2", "Roanoke 2","test2"));
        when(restaurantService.fetchAllRestaurant()).thenReturn(restaurantDTOS);

        ResponseEntity<List<RestaurantDTO>> response = restaurantController.fetchAllRestaurant();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantDTOS, response.getBody());

        verify(restaurantService, times(1)).fetchAllRestaurant();

    }
    @Test
    public void addRestaurant(){
        RestaurantDTO restaurantDTO = new RestaurantDTO(1, "Restaurant 1", "This is test", "Roanoke 1","test1");
        when(restaurantService.addRestaurant(restaurantDTO)).thenReturn(restaurantDTO);

        ResponseEntity<RestaurantDTO> response = restaurantController.addRestaurant(restaurantDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(restaurantDTO, response.getBody());

        verify(restaurantService, times(1)).addRestaurant(restaurantDTO);

    }

    @Test
    public void testGetRestaurantById(){
        int id = 1;
        RestaurantDTO restaurantDTO = new RestaurantDTO(1, "Restaurant 1", "This is test", "Roanoke 1","test1");


        when(restaurantService.getRestaurantById(id)).thenReturn(new ResponseEntity<>(restaurantDTO, HttpStatus.OK));
        ResponseEntity<RestaurantDTO> response = restaurantController.getRestaurantById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantDTO, response.getBody());

        verify(restaurantService, times(1)).getRestaurantById(id);
    }
}
