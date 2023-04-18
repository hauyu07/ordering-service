package io.hauyu07.orderingservice.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class OrderCreationDto {

    private Integer tableNumber;

    private String name;

    @NotEmpty(message = "Invalid items: must consist at least 1 item")
    private List<OrderItemCreationDto> items;

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrderItemCreationDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemCreationDto> items) {
        this.items = items;
    }
}