package com.localbite_express.core.repositories.table;

import com.localbite_express.core.models.sales.table.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Table,Long> {
}
