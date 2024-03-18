package com.localbite_express.core.repositories.table;

import com.localbite_express.core.models.sales.table.TableOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableOrderItemRepository extends JpaRepository<TableOrderItem,Long> {
}
