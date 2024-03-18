package com.localbite_express.core.models.sales.table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.localbite_express.core.models.restaurant.MenuItem;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TableOrderItem {
    @Id
    @GeneratedValue
    private Long id;
    private TableOrder order;
    private MenuItem item;
    private int quantity;

}
