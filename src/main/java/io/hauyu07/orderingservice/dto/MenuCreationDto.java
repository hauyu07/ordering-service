package io.hauyu07.orderingservice.dto;

import java.util.List;

public class MenuCreationDto {

    private String name;

    private String description;

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

    public List<MenuCategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<MenuCategoryDto> categories) {
        this.categories = categories;
    }
}