package io.hauyu07.orderingservice.repository;

import io.hauyu07.orderingservice.entity.Order;
import io.hauyu07.orderingservice.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    public List<OrderItem> findByOrderInOrderByCreatedAtAsc(List<Order> order);
}