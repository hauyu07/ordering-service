package io.hauyu07.orderingservice.service;

import io.hauyu07.orderingservice.dto.OrderCreationDto;
import io.hauyu07.orderingservice.dto.OrderItemCreationDto;
import io.hauyu07.orderingservice.entity.MenuItem;
import io.hauyu07.orderingservice.entity.Order;
import io.hauyu07.orderingservice.entity.OrderItem;
import io.hauyu07.orderingservice.entity.User;
import io.hauyu07.orderingservice.exception.ResourceNotFoundException;
import io.hauyu07.orderingservice.mapper.OrderMapper;
import io.hauyu07.orderingservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderMapper orderMapper;

    public List<Order> getOrdersByRestaurantUser(String userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return user.getRestaurant().getOrders();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository
                .findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
    }

    @Transactional
    public void createOrderByRestaurantUser(String userId, OrderCreationDto orderCreationDto) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<OrderItemCreationDto> orderItemCreationDtos = orderCreationDto.getItems();
        Order order = orderMapper.orderCreationDtoToOrder(orderCreationDto);
        order.setRestaurant(user.getRestaurant());
        Order createdOrder = orderRepository.save(order);

        List<OrderItem> orderItemsToCreate = new ArrayList<>();
        for (OrderItemCreationDto orderItemCreationDto : orderItemCreationDtos) {
            OrderItem orderItem = new OrderItem();
            Long menuItemId = orderItemCreationDto.getMenuItemId();
            MenuItem menuItem = menuItemRepository
                    .findById(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu Item", "id", menuItemId));
            orderItem.setMenuItem(menuItem);
            orderItem.setOrder(createdOrder);
            orderItem.setQuantity(orderItemCreationDto.getQuantity());
            orderItemsToCreate.add(orderItem);
        }
        orderItemRepository.saveAll(orderItemsToCreate);
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }
}