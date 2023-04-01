package io.hauyu07.orderingservice.mapper;

import io.hauyu07.orderingservice.dto.*;
import io.hauyu07.orderingservice.entity.Order;
import io.hauyu07.orderingservice.entity.OrderItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    Order orderDtoToOrder(OrderDto orderDto);

    OrderDto orderToOrderDto(Order order);

    Order orderCreationDtoToOrder(OrderCreationDto orderCreationDto);


    @Mapping(target = ".", source = "menuItem")
    OrderItemDto orderItemToOrderItemDto(OrderItem orderItem);

    List<OrderItem> orderItemCreationDtoListToOrderItemList(List<OrderItemCreationDto> orderItemCreationDtoList);

    @AfterMapping
    default void calculateTotalPrice(Order order, @MappingTarget OrderDto orderDto) {
        orderDto.setTotalPrice(order.getTotalPrice());
    }

    List<OrderListingDto> orderListToOrderListingDtoList(List<Order> orderList);

    @AfterMapping
    default void calculateNumberOfItems(Order order, @MappingTarget OrderListingDto orderListingDto) {
        int count = 0;
        List<OrderItem> items = order.getItems();
        for (OrderItem item : items) {
            count += item.getQuantity();
        }
        orderListingDto.setNumberOfItems(count);
    }
}