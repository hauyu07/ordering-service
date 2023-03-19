package io.hauyu07.orderingservice.mapper;

import io.hauyu07.orderingservice.dto.RestaurantCreationDto;
import io.hauyu07.orderingservice.dto.RestaurantDto;
import io.hauyu07.orderingservice.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "menus", ignore = true)
    Restaurant restaurantCreationDtoToRestaurant(RestaurantCreationDto restaurantCreationDto);

    RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);
}