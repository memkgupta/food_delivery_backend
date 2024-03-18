package com.localbite_express.core.repositories.inventory;

import com.localbite_express.core.models.inventory.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction,Long> {
}
