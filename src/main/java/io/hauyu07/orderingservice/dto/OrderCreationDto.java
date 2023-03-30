package io.hauyu07.orderingservice.dto;

import java.util.List;

public class OrderCreationDto {

    private Integer tableNumber;

    private List<OrderItemCreationDto> items;

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public List<OrderItemCreationDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemCreationDto> items) {
        this.items = items;
    }
}