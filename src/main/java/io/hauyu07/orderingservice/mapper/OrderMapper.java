package io.hauyu07.orderingservice.mapper;

import io.hauyu07.orderingservice.dto.OrderCreationDto;
import io.hauyu07.orderingservice.dto.OrderDto;
import io.hauyu07.orderingservice.dto.OrderItemDto;
import io.hauyu07.orderingservice.entity.Order;
import io.hauyu07.orderingservice.entity.OrderItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    Order orderDtoToOrder(OrderDto orderDto);

    OrderDto orderToOrderDto(Order order);

    Order orderCreationDtoToOrder(OrderCreationDto orderCreationDto);

    OrderItem orderItemDtoToOrderItem(OrderItemDto orderItemDto);

    @Mapping(target = ".", source = "menuItem")
    OrderItemDto orderItemToOrderItemDto(OrderItem orderItem);

    @AfterMapping
    default void calculateTotalPrice(Order order, @MappingTarget OrderDto orderDto) {
        orderDto.setTotalPrice(order.getTotalPrice());
    }
}