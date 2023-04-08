package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.RestaurantRootUserCreationDto;
import io.hauyu07.orderingservice.mapper.RestaurantMapper;
import io.hauyu07.orderingservice.service.AuthService;
import io.hauyu07.orderingservice.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Tag(name = "Auth", description = "User sign up and Restaurant creation")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Operation(summary = "Create restaurant and its root user")
    @PostMapping("/sign-up")
    public ResponseEntity<String> createRestaurantRootUser(
            Principal principal,
            @RequestBody RestaurantRootUserCreationDto restaurantRootUserCreationDto
    ) {
        authService.createRestaurantRootUser(principal.getName(), restaurantRootUserCreationDto);
        return ResponseEntity.ok("Success");
    }

}