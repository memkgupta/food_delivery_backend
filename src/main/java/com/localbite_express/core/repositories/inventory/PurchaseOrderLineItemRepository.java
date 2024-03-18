package com.localbite_express.core.repositories.inventory;

import com.localbite_express.core.models.inventory.PurchaseOrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderLineItemRepository extends JpaRepository<PurchaseOrderLineItem,Long> {

}
