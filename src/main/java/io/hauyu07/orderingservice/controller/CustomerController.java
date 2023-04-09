package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.CustomerCreationDto;
import io.hauyu07.orderingservice.dto.CustomerDto;
import io.hauyu07.orderingservice.dto.CustomerListingDto;
import io.hauyu07.orderingservice.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    @Operation(summary = "Create a customer entry from a restaurant user's view")
    public ResponseEntity<String> createCustomer(
            Principal principal,
            @RequestBody CustomerCreationDto customerCreationDto
    ) {
        customerService.createCustomer(principal.getName(), customerCreationDto);
        return ResponseEntity.ok("Success");
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
}
