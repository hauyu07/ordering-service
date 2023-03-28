package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.MenuCreationDto;
import io.hauyu07.orderingservice.dto.RestaurantCreationDto;
import io.hauyu07.orderingservice.dto.RestaurantDto;
import io.hauyu07.orderingservice.entity.Menu;
import io.hauyu07.orderingservice.entity.MenuCategory;
import io.hauyu07.orderingservice.entity.MenuItem;
import io.hauyu07.orderingservice.entity.Restaurant;
import io.hauyu07.orderingservice.mapper.MenuMapper;
import io.hauyu07.orderingservice.mapper.RestaurantMapper;
import io.hauyu07.orderingservice.service.MenuCategoryService;
import io.hauyu07.orderingservice.service.MenuItemService;
import io.hauyu07.orderingservice.service.MenuService;
import io.hauyu07.orderingservice.service.RestaurantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Restaurant")
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuCategoryService menuCategoryService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private MenuMapper menuMapper;

//    @GetMapping
//    public Page<Restaurant> getAllRestaurants(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return restaurantService.getAllRestaurants(page, size);
//    }

    @GetMapping("/{id}")
    public RestaurantDto getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return restaurantMapper.restaurantToRestaurantDto(restaurant);
    }

    @PostMapping
    public RestaurantCreationDto createRestaurant(@RequestBody RestaurantCreationDto restaurantCreationDto) {
        Restaurant restaurant = restaurantMapper.restaurantCreationDtoToRestaurant(restaurantCreationDto);
        restaurantService.createRestaurant(restaurant);
        return restaurantCreationDto;
    }

    // TODO: refactor creation of menu along with the associated nested entities
    @PostMapping("/{id}/menus")
    public MenuCreationDto createMenu(@PathVariable Long id, @RequestBody MenuCreationDto menuCreationDto) {
        Menu menu = menuMapper.menuDtoToMenu(menuCreationDto);
        Menu createdMenu = menuService.createMenu(id, menu);
        List<MenuCategory> categories = menuMapper.menuCategoryDtoListToMenuCategoryList(menuCreationDto.getCategories());
        for (MenuCategory category : categories) {
            MenuCategory createdMenuCategory = menuCategoryService.createMenuCategory(createdMenu.getId(), category);
            List<MenuItem> items = category.getItems();
            for (MenuItem item : items) {
                menuItemService.createMenuItem(createdMenuCategory.getId(), item);
            }
        }
        return menuCreationDto;
    }

//    @PutMapping("/{id}")
//    public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody Restaurant updatedRestaurant) {
//        return restaurantService.updateRestaurant(id, updatedRestaurant);
//    }

//    @DeleteMapping("/{id}")
//    public void deleteRestaurant(@PathVariable Long id) {
//        restaurantService.deleteRestaurant(id);
//    }
}
