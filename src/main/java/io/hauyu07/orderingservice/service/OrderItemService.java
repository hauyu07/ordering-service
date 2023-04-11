package io.hauyu07.orderingservice.service;

import io.hauyu07.orderingservice.dto.OrderItemListingDto;
import io.hauyu07.orderingservice.entity.Order;
import io.hauyu07.orderingservice.entity.OrderItem;
import io.hauyu07.orderingservice.entity.User;
import io.hauyu07.orderingservice.exception.ResourceNotFoundException;
import io.hauyu07.orderingservice.mapper.OrderItemMapper;
import io.hauyu07.orderingservice.repository.OrderItemRepository;
import io.hauyu07.orderingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemMapper orderItemMapper;

    public List<OrderItemListingDto> getRestaurantOrderItems(String restaurantUserId) {
        User user = userRepository
                .findById(restaurantUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", restaurantUserId));
        List<Order> orders = user.getRestaurant().getOrders();
        List<OrderItem> orderItems = orderItemRepository.findByOrderInOrderByCreatedAtAsc(orders);
        return orderItemMapper.orderItemListToOrderItemListingDtoList(orderItems);
    }
}