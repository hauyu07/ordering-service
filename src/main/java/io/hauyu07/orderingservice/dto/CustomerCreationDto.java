package io.hauyu07.orderingservice.dto;

import javax.validation.constraints.Min;

public class CustomerCreationDto {

    @Min(value = 1, message = "Invalid table number: must be no smaller than 1")
    private Integer tableNumber;

    @Min(value = 1, message = "Invalid head count: must be no smaller than 1")
    private Integer headCount;

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getHeadCount() {
        return headCount;
    }

    public void setHeadCount(Integer headCount) {
        this.headCount = headCount;
    }
}