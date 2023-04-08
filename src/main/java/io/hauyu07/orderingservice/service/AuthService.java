package io.hauyu07.orderingservice.service;

import io.hauyu07.orderingservice.dto.RestaurantRootUserCreationDto;
import io.hauyu07.orderingservice.entity.Restaurant;
import io.hauyu07.orderingservice.entity.User;
import io.hauyu07.orderingservice.repository.RestaurantRepository;
import io.hauyu07.orderingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Transactional
    public void createRestaurantRootUser(String id, RestaurantRootUserCreationDto dto) {
        Restaurant restaurant = new Restaurant(dto.getRestaurantName());
        User user = new User(id, dto.getUsername(), restaurant);
        restaurant.addUser(user);
        restaurantRepository.save(restaurant);
        userRepository.save(user);
    }
}