package io.hauyu07.orderingservice.service;

import io.hauyu07.orderingservice.dto.OrderCreationDto;
import io.hauyu07.orderingservice.dto.OrderItemCreationDto;
import io.hauyu07.orderingservice.dto.OrderListingDto;
import io.hauyu07.orderingservice.entity.*;
import io.hauyu07.orderingservice.exception.ResourceNotFoundException;
import io.hauyu07.orderingservice.mapper.OrderMapper;
import io.hauyu07.orderingservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private CustomerRepository customerRepository;

    @Autowired
    private OrderMapper orderMapper;

    public List<Order> getOrdersByRestaurantUser(String userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return user.getRestaurant().getOrders();
    }

    public List<OrderListingDto> getOrderByCustomer(UUID customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
        List<Order> orders = customer.getOrders();
        return orderMapper.orderListToOrderListingDtoList(orders);
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

        Integer tableNumber = orderCreationDto.getTableNumber();
        Customer customer = customerRepository.findByTableNumber(tableNumber);

        List<OrderItemCreationDto> orderItemCreationDtos = orderCreationDto.getItems();
        Order order = orderMapper.orderCreationDtoToOrder(orderCreationDto);
        order.setRestaurant(user.getRestaurant());
        order.setCustomer(customer);
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

    @Transactional
    public void createOrderByCustomer(UUID customerId, OrderCreationDto orderCreationDto) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        List<OrderItemCreationDto> orderItemCreationDtos = orderCreationDto.getItems();
        Order order = orderMapper.orderCreationDtoToOrder(orderCreationDto);
        order.setCustomer(customer);
        order.setRestaurant(customer.getRestaurant());
        order.setTableNumber(customer.getTableNumber());
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