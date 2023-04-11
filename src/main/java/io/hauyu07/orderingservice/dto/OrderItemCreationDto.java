package io.hauyu07.orderingservice.dto;

import javax.validation.constraints.Min;

public class OrderItemCreationDto {

    @Min(value = 1)
    private Long menuItemId;

    @Min(value = 1, message = "Invalid quantity: must be at least 1")
    private Integer quantity;

    private String remarks;

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}