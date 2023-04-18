package io.hauyu07.orderingservice.dto;

import java.util.List;

public class MenuDto {

    private String name;

    private String description;

    private Boolean isActive;

    private RestaurantDto restaurant;

    private List<MenuCategoryDto> categories;

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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public RestaurantDto getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDto restaurant) {
        this.restaurant = restaurant;
    }

    public List<MenuCategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<MenuCategoryDto> categories) {
        this.categories = categories;
    }
}