package com.localbite_express.core.models.sales;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.localbite_express.core.models.restaurant.Restaurant;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonFilter("offerFilter")
public class Offer {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal discountPercentage;
    private String details;
    @ManyToOne
    private Restaurant restaurant;
}
