package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.OrderCreationDto;
import io.hauyu07.orderingservice.dto.OrderDto;
import io.hauyu07.orderingservice.dto.OrderListingDto;
import io.hauyu07.orderingservice.entity.Order;
import io.hauyu07.orderingservice.mapper.OrderMapper;
import io.hauyu07.orderingservice.service.MenuItemService;
import io.hauyu07.orderingservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Order")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    @Operation(summary = "List orders of the current user's restaurant")
    public List<OrderListingDto> getRestaurantOrders(Principal principal) {
        List<Order> orders = orderService.getOrdersByRestaurantUser(principal.getName());
        return orderMapper.orderListToOrderListingDtoList(orders);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get details of a specific order")
    public OrderDto getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return orderMapper.orderToOrderDto(order);
    }

    @PostMapping
    @Operation(summary = "Create an order for the current user's restaurant (scenario of a waiter taking order )")
    public ResponseEntity<String> createRestaurantOrder(
            Principal principal,
            @RequestBody OrderCreationDto orderCreationDto
    ) {
        orderService.createOrderByRestaurantUser(principal.getName(), orderCreationDto);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a specific order")
    public ResponseEntity<String> deleteRestaurantOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.ok("Success");
    }
}