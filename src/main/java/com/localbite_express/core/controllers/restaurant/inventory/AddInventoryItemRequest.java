package com.localbite_express.core.controllers.restaurant.inventory;

import com.localbite_express.core.models.inventory.Category;

import com.localbite_express.core.models.inventory.Measures;
import com.localbite_express.core.models.inventory.Supplier;
import jakarta.persistence.*;
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
public class AddInventoryItemRequest {


    private int restaurant_id;
    private String name;

    private Measures measures;
    private int quantityOnHand;
    private int minimumStockLevel;
    private BigDecimal unitPrice;
    private int supplier_id;
    private int category_id;
    private Duration shelfLife;
    private LocalDate expiryDate;
    private LocalDate refillDate;
}
