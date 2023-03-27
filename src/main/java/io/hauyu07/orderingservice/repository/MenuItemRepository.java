package io.hauyu07.orderingservice.repository;

import io.hauyu07.orderingservice.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}