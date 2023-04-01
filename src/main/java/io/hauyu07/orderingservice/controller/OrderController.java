package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.OrderDto;
import io.hauyu07.orderingservice.dto.OrderItemCreationDto;
import io.hauyu07.orderingservice.entity.MenuItem;
import io.hauyu07.orderingservice.entity.Order;
import io.hauyu07.orderingservice.entity.OrderItem;
import io.hauyu07.orderingservice.mapper.OrderMapper;
import io.hauyu07.orderingservice.service.MenuItemService;
import io.hauyu07.orderingservice.service.OrderItemService;
import io.hauyu07.orderingservice.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Order")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return orderMapper.orderToOrderDto(order);
    }

    @PostMapping("/{id}/items")
    public OrderDto appendOrderItem(@PathVariable Long id, @RequestBody List<OrderItemCreationDto> orderItemCreationDtos) {
        List<OrderItem> orderItems = orderItemCreationDtos.stream().map(item -> {
            MenuItem menuItem = menuItemService.getMenuItemById(item.getMenuItemId());
            return new OrderItem(menuItem, item.getQuantity());
        }).collect(Collectors.toList());
        Order order = orderItemService.createMultipleOrderItems(id, orderItems);
        return orderMapper.orderToOrderDto(order);
    }
}