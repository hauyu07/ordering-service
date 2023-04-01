package io.hauyu07.orderingservice.dto;

import java.util.List;

public class MenuCreationDto {

    private String name;

    private String description;

    private List<MenuCategoryCreationDto> categories;

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

    public List<MenuCategoryCreationDto> getCategories() {
        return categories;
    }

    public void setCategories(List<MenuCategoryCreationDto> categories) {
        this.categories = categories;
    }
}