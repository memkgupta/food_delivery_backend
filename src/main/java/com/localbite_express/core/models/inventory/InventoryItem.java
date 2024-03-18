package com.localbite_express.core.models.inventory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InventoryItem {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Measures measures;
    private int quantityOnHand;
    private int minimumStockLevel;
    private BigDecimal unitPrice;
    private Supplier supplier;
    private Category category;
    private Duration shelfLife;
    private LocalDate expiryDate;
    private LocalDate refillDate;


}

enum Measures{
WEIGHT,
VOLUME,
COUNT,
LENGTH,
AREA,

}
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private Duration shelfLife;
}