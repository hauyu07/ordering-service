package io.hauyu07.orderingservice.service;

import io.hauyu07.orderingservice.entity.Order;
import io.hauyu07.orderingservice.entity.OrderItem;
import io.hauyu07.orderingservice.exception.ResourceNotFoundException;
import io.hauyu07.orderingservice.repository.MenuItemRepository;
import io.hauyu07.orderingservice.repository.OrderItemRepository;
import io.hauyu07.orderingservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public Order createOrderItem(Long orderId, OrderItem orderItem) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        order.appendItem(orderItem);
        orderItemRepository.save(orderItem);
        return order;
    }

    public Order createMultipleOrderItems(Long orderId, List<OrderItem> orderItems) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        orderItems.forEach(orderItem -> {
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        });
        order.setStatus("pending");
        order.setItems(orderItems);
        orderRepository.save(order);
        return order;
    }
}