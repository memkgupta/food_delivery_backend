package com.localbite_express.core.models.inventory;

import jakarta.persistence.*;

import java.time.LocalDate;

public class InventoryAdjustment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private InventoryItem inventoryItem;

    @Column(nullable = false)
    private LocalDate adjustmentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdjustmentType adjustmentType;

    @Column(nullable = false)
    private int quantity;

    private String reason;
}
enum AdjustmentType {
    ADDITION,
    SUBTRACTION
}