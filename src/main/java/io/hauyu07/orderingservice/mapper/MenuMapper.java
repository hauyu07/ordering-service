package io.hauyu07.orderingservice.mapper;

import io.hauyu07.orderingservice.dto.MenuCategoryDto;
import io.hauyu07.orderingservice.dto.MenuCreationDto;
import io.hauyu07.orderingservice.dto.MenuDto;
import io.hauyu07.orderingservice.dto.RestaurantDto;
import io.hauyu07.orderingservice.entity.Menu;
import io.hauyu07.orderingservice.entity.MenuCategory;
import io.hauyu07.orderingservice.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Menu menuDtoToMenu(MenuCreationDto menuCreationDto);

    MenuDto menuToMenuDto(Menu menu);

    RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);

    MenuCategoryDto menuCateogoryToMenuCategoryDto(MenuCategory menuCategory);
}