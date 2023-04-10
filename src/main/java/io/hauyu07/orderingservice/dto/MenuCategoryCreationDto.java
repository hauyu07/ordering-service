package io.hauyu07.orderingservice.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class MenuCategoryCreationDto {

    @NotEmpty(message = "Invalid category name: must not be empty")
    private String name;

    private String description;

    @NotEmpty(message = "Invalid items: must consist of at least 1 item")
    private List<MenuItemCreationDto> items;

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

    public List<MenuItemCreationDto> getItems() {
        return items;
    }

    public void setItems(List<MenuItemCreationDto> items) {
        this.items = items;
    }
}