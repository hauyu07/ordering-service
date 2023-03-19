package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.RestaurantCreationDto;
import io.hauyu07.orderingservice.dto.RestaurantDto;
import io.hauyu07.orderingservice.entity.Restaurant;
import io.hauyu07.orderingservice.mapper.RestaurantMapper;
import io.hauyu07.orderingservice.service.RestaurantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Restaurant")
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantMapper restaurantMapper;

//    @GetMapping
//    public Page<Restaurant> getAllRestaurants(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return restaurantService.getAllRestaurants(page, size);
//    }

    @GetMapping("/{id}")
    public RestaurantDto getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return restaurantMapper.restaurantToRestaurantDto(restaurant);
    }

    @PostMapping
    public RestaurantCreationDto createRestaurant(@RequestBody RestaurantCreationDto restaurantCreationDto) {
        Restaurant restaurant = restaurantMapper.restaurantCreationDtoToRestaurant(restaurantCreationDto);
        restaurantService.createRestaurant(restaurant);
        return restaurantCreationDto;
    }

//    @PutMapping("/{id}")
//    public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody Restaurant updatedRestaurant) {
//        return restaurantService.updateRestaurant(id, updatedRestaurant);
//    }

//    @DeleteMapping("/{id}")
//    public void deleteRestaurant(@PathVariable Long id) {
//        restaurantService.deleteRestaurant(id);
//    }
}
