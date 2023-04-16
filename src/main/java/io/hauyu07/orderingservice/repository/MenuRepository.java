package io.hauyu07.orderingservice.repository;

import io.hauyu07.orderingservice.entity.Menu;
import io.hauyu07.orderingservice.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    public Menu findByRestaurantAndIsActive(Restaurant restaurant, Boolean isActive);
}