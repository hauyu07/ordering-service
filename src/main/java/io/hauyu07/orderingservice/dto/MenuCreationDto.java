package io.hauyu07.orderingservice.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class MenuCreationDto {

    @NotEmpty(message = "Invalid menu name: must not be empty")
    private String name;

    private String description;
    
    private Boolean isActive;

    @NotEmpty(message = "Invalid categories: must consist of at least 1 category")
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<MenuCategoryCreationDto> getCategories() {
        return categories;
    }

    public void setCategories(List<MenuCategoryCreationDto> categories) {
        this.categories = categories;
    }
}