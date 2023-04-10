package io.hauyu07.orderingservice.dto;

import javax.validation.constraints.NotEmpty;

public class RestaurantCreationDto {

    @NotEmpty(message = "Invalid restaurant name: must not be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}