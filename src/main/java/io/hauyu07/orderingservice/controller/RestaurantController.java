package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.OrderCreationDto;
import io.hauyu07.orderingservice.dto.OrderItemCreationDto;
import io.hauyu07.orderingservice.dto.OrderListingDto;
import io.hauyu07.orderingservice.entity.MenuItem;
import io.hauyu07.orderingservice.entity.Order;
import io.hauyu07.orderingservice.entity.OrderItem;
import io.hauyu07.orderingservice.mapper.MenuMapper;
import io.hauyu07.orderingservice.mapper.OrderMapper;
import io.hauyu07.orderingservice.mapper.RestaurantMapper;
import io.hauyu07.orderingservice.service.MenuItemService;
import io.hauyu07.orderingservice.service.OrderItemService;
import io.hauyu07.orderingservice.service.OrderService;
import io.hauyu07.orderingservice.service.RestaurantService;
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

    @GetMapping("/{id}/orders")
    public List<OrderListingDto> getOrders(@PathVariable Long id) {
        List<Order> orders = restaurantService.getRestaurantOrders(id);
        return orderMapper.orderListToOrderListingDtoList(orders);
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
}
