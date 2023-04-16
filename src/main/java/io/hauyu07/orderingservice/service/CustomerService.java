package io.hauyu07.orderingservice.service;

import io.hauyu07.orderingservice.dto.CustomerCreationDto;
import io.hauyu07.orderingservice.dto.CustomerDto;
import io.hauyu07.orderingservice.dto.CustomerListingDto;
import io.hauyu07.orderingservice.entity.Customer;
import io.hauyu07.orderingservice.entity.Restaurant;
import io.hauyu07.orderingservice.entity.User;
import io.hauyu07.orderingservice.exception.ResourceNotFoundException;
import io.hauyu07.orderingservice.mapper.CustomerMapper;
import io.hauyu07.orderingservice.repository.CustomerRepository;
import io.hauyu07.orderingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public Customer createCustomer(
            String restaurantUserId,
            CustomerCreationDto customerCreationDto
    ) {
        Customer customer = customerMapper.customerCreationDtoToCustomer(customerCreationDto);
        User user = userRepository
                .findById(restaurantUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", restaurantUserId));
        Restaurant restaurant = user.getRestaurant();
        customer.setRestaurant(restaurant);
        return customerRepository.save(customer);
    }

    public List<CustomerListingDto> getCustomersByRestaurantUser(String restaurantUserId) {
        User user = userRepository
                .findById(restaurantUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", restaurantUserId));
        List<Customer> customers = user.getRestaurant().getCustomers();
        return customerMapper.customerListToCustomerListingDtoList(customers);
    }

    public CustomerDto getCustomerById(UUID customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
        return customerMapper.customerToCustomerDto(customer);
    }

    public void removeCustomerById(UUID id) {
        customerRepository.deleteById(id);
    }

}