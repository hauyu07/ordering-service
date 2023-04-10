package io.hauyu07.orderingservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class MenuItemCreationDto {

    @NotEmpty(message = "Invalid menu item name: must not be empty")
    private String name;

    private String description;

    @Min(value = 0, message = "Invalid price: must be non-negative")
    private Double price;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}