package com.cotiviti.restaurentlisting.controller;

import com.cotiviti.restaurentlisting.dto.RestaurantDTO;
import com.cotiviti.restaurentlisting.service.RestaurantServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin
public class RestaurantController {

    private RestaurantServiceImpl restaurantService;

    public RestaurantController(RestaurantServiceImpl restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/fetchAllRestaurant")
    public ResponseEntity<List<RestaurantDTO>> fetchAllRestaurant() {
        List<RestaurantDTO> restaurantDTOS = restaurantService.fetchAllRestaurant();
        return new ResponseEntity<>(restaurantDTOS, HttpStatus.OK);
    }

    @PostMapping("/addRestaurant")
    public ResponseEntity<RestaurantDTO> addRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO restaurantDTO1 = restaurantService.addRestaurant(restaurantDTO);
        return new ResponseEntity<>(restaurantDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable("id") int id) {
        return restaurantService.getRestaurantById(id);

    }
}
