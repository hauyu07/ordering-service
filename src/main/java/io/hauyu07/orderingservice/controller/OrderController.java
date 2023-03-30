package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.OrderDto;
import io.hauyu07.orderingservice.entity.Order;
import io.hauyu07.orderingservice.mapper.OrderMapper;
import io.hauyu07.orderingservice.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return orderMapper.orderToOrderDto(order);
    }
}