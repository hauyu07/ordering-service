package io.hauyu07.orderingservice.dto;

import javax.validation.constraints.NotEmpty;

public class RestaurantRootUserCreationDto {

    @NotEmpty(message = "Invalid username: must not be empty")
    private String username;

    @NotEmpty(message = "Invalid restaurant name: must not be empty")
    private String restaurantName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}