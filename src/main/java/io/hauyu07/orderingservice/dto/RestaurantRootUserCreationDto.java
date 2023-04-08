package io.hauyu07.orderingservice.dto;

public class RestaurantRootUserCreationDto {

    private String username;

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