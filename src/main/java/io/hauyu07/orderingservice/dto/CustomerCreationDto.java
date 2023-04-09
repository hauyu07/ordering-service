package io.hauyu07.orderingservice.dto;

public class CustomerCreationDto {

    private Integer tableNumber;

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