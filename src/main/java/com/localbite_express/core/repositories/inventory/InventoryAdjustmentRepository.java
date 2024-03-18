package com.localbite_express.core.repositories.inventory;

import com.localbite_express.core.models.inventory.InventoryAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryAdjustmentRepository extends JpaRepository<InventoryAdjustment,Long> {
}
