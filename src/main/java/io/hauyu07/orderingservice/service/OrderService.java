package io.hauyu07.orderingservice.service;

import io.hauyu07.orderingservice.entity.Order;
import io.hauyu07.orderingservice.entity.Restaurant;
import io.hauyu07.orderingservice.exception.ResourceNotFoundException;
import io.hauyu07.orderingservice.mapper.OrderMapper;
import io.hauyu07.orderingservice.repository.OrderItemRepository;
import io.hauyu07.orderingservice.repository.OrderRepository;
import io.hauyu07.orderingservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderMapper orderMapper;

    public Order getOrderById(Long orderId) {
        return orderRepository
                .findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
    }

    public Order createOrder(Long restaurantId, Order order) {
        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", restaurantId));
        order.setRestaurant(restaurant);
        return orderRepository.save(order);
    }
}