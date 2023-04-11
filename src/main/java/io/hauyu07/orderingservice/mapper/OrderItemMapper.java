package io.hauyu07.orderingservice.mapper;

import io.hauyu07.orderingservice.dto.OrderItemListingDto;
import io.hauyu07.orderingservice.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {

    List<OrderItemListingDto> orderItemListToOrderItemListingDtoList(List<OrderItem> orderItemList);

    default OrderItemListingDto orderItemToOrderItemListingDto(OrderItem orderItem) {
        OrderItemListingDto orderItemListingDto = new OrderItemListingDto();
        orderItemListingDto.setTableNumber(orderItem.getOrder().getCustomer().getTableNumber());
        orderItemListingDto.setMenuItemName(orderItem.getMenuItem().getName());
        orderItemListingDto.setQuantity(orderItem.getQuantity());
        orderItemListingDto.setCreatedAt(orderItem.getCreatedAt());
        orderItemListingDto.setRemarks(orderItem.getRemarks());
        return orderItemListingDto;
    }

}