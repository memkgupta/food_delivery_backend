package com.localbite_express.core.models.inventory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InventoryTransaction {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private InventoryItem item;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;
    private int quantity;
    @Column(nullable = false)
    private LocalDateTime transactionDate;
    @Column(nullable = false)
    private BigDecimal unitPrice;
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @Column(length = 1000)
    private String invoiceLink;
}
enum TransactionType {
    PURCHASE,
    SALE,
    RETURN
}