package com.localbite_express.core.repositories.inventory;

import com.localbite_express.core.models.inventory.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItem,Long> {
}
