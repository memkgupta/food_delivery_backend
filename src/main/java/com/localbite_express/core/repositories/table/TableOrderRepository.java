package com.localbite_express.core.repositories.table;

import com.localbite_express.core.models.sales.table.TableOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableOrderRepository extends JpaRepository<TableOrder,Long> {
}
