package io.hauyu07.orderingservice.mapper;

import io.hauyu07.orderingservice.dto.*;
import io.hauyu07.orderingservice.entity.Menu;
import io.hauyu07.orderingservice.entity.MenuCategory;
import io.hauyu07.orderingservice.entity.MenuItem;
import io.hauyu07.orderingservice.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Menu menuDtoToMenu(MenuCreationDto menuCreationDto);

    MenuDto menuToMenuDto(Menu menu);

    RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);

    MenuCategory menuCategoryDtoToMenuCategory(MenuCategoryDto menuCategoryDto);

    List<MenuCategory> menuCategoryDtoListToMenuCategoryList(List<MenuCategoryDto> menuCategoryDtoList);

    MenuCategoryDto menuCateogoryToMenuCategoryDto(MenuCategory menuCategory);

    MenuItemDto menuItemToMenuItemDto(MenuItem menuItem);
}