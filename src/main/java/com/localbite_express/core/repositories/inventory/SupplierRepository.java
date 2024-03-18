package com.localbite_express.core.repositories.inventory;

import com.localbite_express.core.models.inventory.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier,Long> {
}
