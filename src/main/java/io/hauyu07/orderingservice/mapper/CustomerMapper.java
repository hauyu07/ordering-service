package io.hauyu07.orderingservice.mapper;

import io.hauyu07.orderingservice.dto.CustomerCreationDto;
import io.hauyu07.orderingservice.dto.CustomerDto;
import io.hauyu07.orderingservice.dto.CustomerListingDto;
import io.hauyu07.orderingservice.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    Customer customerCreationDtoToCustomer(CustomerCreationDto customerCreationDto);

    CustomerDto customerToCustomerDto(Customer customer);

    List<CustomerListingDto> customerListToCustomerListingDtoList(List<Customer> customerList);
}