package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.*;
import io.hauyu07.orderingservice.entity.Customer;
import io.hauyu07.orderingservice.service.CustomerService;
import io.hauyu07.orderingservice.service.MenuService;
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
import java.util.HashMap;
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

    @Autowired
    private MenuService menuService;

    @PostMapping
    @Operation(summary = "Create a customer entry from a restaurant user's view")
    @ApiResponses(value = @ApiResponse(responseCode = "201"))
    public ResponseEntity<HashMap<String, UUID>> createCustomer(
            Principal principal,
            @Valid @RequestBody CustomerCreationDto customerCreationDto
    ) {
        Customer customer = customerService.createCustomer(principal.getName(), customerCreationDto);
        HashMap<String, UUID> payload = new HashMap<>();
        payload.put("customerId", customer.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(payload);
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

    @GetMapping("/{id}/menus/active")
    @Operation(summary = "Get the active")
    public ResponseEntity<MenuDto> getActiveMenu(@PathVariable UUID id) {
        return ResponseEntity.ok(menuService.getActiveMenuForCustomer(id));
    }
}
