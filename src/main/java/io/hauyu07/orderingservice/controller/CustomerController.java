package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.*;
import io.hauyu07.orderingservice.service.CustomerService;
import io.hauyu07.orderingservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @PostMapping
    @Operation(summary = "Create a customer entry from a restaurant user's view")
    @ApiResponses(value = @ApiResponse(responseCode = "201"))
    public ResponseEntity<String> createCustomer(
            Principal principal,
            @Valid @RequestBody CustomerCreationDto customerCreationDto
    ) {
        customerService.createCustomer(principal.getName(), customerCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @GetMapping
    @Operation(summary = "List customers of a restaurant")
    public ResponseEntity<List<CustomerListingDto>> getCustomersByRestaurant(Principal principal) {
        return ResponseEntity.ok(customerService.getCustomersByRestaurantUser(principal.getName()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get details of a customer with specific id")
    public ResponseEntity<CustomerDto> getCustomerById(UUID customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer with specific ID (scenario of customer checkout)")
    public ResponseEntity<String> removeCustomerById(@PathVariable UUID id) {
        customerService.removeCustomerById(id);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/{id}/orders")
    @Operation(summary = "Create an order for a customer")
    @ApiResponses(value = @ApiResponse(responseCode = "201"))
    public ResponseEntity<String> createOrderForCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody OrderCreationDto orderCreationDto
    ) {
        orderService.createOrderByCustomer(id, orderCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @GetMapping("/{id}/orders")
    @Operation(summary = "List orders of a customer")
    public ResponseEntity<List<OrderListingDto>> getCustomerOrders(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderByCustomer(id));
    }
}
