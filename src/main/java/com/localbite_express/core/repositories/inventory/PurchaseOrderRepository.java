package com.localbite_express.core.repositories.inventory;

import com.localbite_express.core.models.inventory.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long> {
}
