package io.hauyu07.orderingservice.dto;

public class MenuDto {

    private String name;

    private String description;

    private RestaurantDto restaurant;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RestaurantDto getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDto restaurant) {
        this.restaurant = restaurant;
    }
}