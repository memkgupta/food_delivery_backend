package com.localbite_express.core.models.sales;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "_order")
@JsonFilter("orderFilter")
public class Sale {
    @Id
    @GeneratedValue
    private int id;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    @OneToMany(mappedBy = "order" ,cascade = CascadeType.ALL)
    private List<OrderItem> items;
    private int total;
    private float discount;
    private float tax;
    private float grand_total;
    private String email;
    private String phone;
    private int restaurant_id;
    private boolean payment_done;
    @Enumerated(EnumType.STRING)
    private PaymentMethods payment_method;
    private LocalDateTime time;
    private Offer offer;
    private Table table;
}
