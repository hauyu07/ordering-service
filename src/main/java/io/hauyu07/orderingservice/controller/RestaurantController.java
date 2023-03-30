package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.*;
import io.hauyu07.orderingservice.entity.*;
import io.hauyu07.orderingservice.mapper.MenuMapper;
import io.hauyu07.orderingservice.mapper.OrderMapper;
import io.hauyu07.orderingservice.mapper.RestaurantMapper;
import io.hauyu07.orderingservice.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private OrderMapper orderMapper;

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

    // TODO: refactor creation of order along with the associated nested entities
    @Transactional
    @PostMapping("/{id}/orders")
    public OrderCreationDto createOrder(@PathVariable Long id, @RequestBody OrderCreationDto orderCreationDto) {
        Order order = orderMapper.orderCreationDtoToOrder(orderCreationDto);
        Order createdOrder = orderService.createOrder(id, order);
        List<OrderItemCreationDto> orderItemCreationDtos = orderCreationDto.getItems();
        List<OrderItem> orderItems = orderItemCreationDtos.stream().map(item -> {
            MenuItem menuItem = menuItemService.getMenuItemById(item.getMenuItemId());
            return new OrderItem(menuItem, item.getQuantity());
        }).collect(Collectors.toList());
        orderItemService.createMultipleOrderItems(createdOrder.getId(), orderItems);
        return orderCreationDto;
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
