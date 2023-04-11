package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.OrderItemListingDto;
import io.hauyu07.orderingservice.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "Order Item")
@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    @Operation(summary = "List order items of the current user's restaurant")
    public ResponseEntity<List<OrderItemListingDto>> getRestaurantOrderItems(Principal principal) {
        return ResponseEntity.ok(orderItemService.getRestaurantOrderItems(principal.getName()));
    }

}