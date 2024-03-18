package com.localbite_express.core.repositories;

import com.localbite_express.core.models.sales.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
