package com.localbite_express.core.models.sales;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.localbite_express.core.models.restaurant.MenuItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonFilter("orderItemFilter")
public class OrderItem {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "menu_item")
    private MenuItem menuItem;
    private int quantity;
    private int totalCost;
    private LocalDateTime time;
    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;
}
