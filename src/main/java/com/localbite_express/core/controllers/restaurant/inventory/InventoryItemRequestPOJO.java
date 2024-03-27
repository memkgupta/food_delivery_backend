package com.localbite_express.core.controllers.restaurant.inventory;

import com.localbite_express.core.models.inventory.Measures;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItemRequestPOJO {
    private String name;
    private Measures measures;
    private int quantityOnHand;
    private int minimumStockLevel;
    private BigDecimal unitPrice;
    private long supplier_id;
    private long category_id;
    private Duration shelfLife;
    private LocalDate expiryDate;
    private LocalDate refillDate;
}
