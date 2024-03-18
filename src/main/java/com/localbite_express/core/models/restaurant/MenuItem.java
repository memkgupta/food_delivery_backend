package com.localbite_express.core.models.restaurant;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.localbite_express.core.models.Categories;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.concurrent.TimeUnit;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonFilter("menuItemFilter")
public class MenuItem {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    private int price;

    private long preparationTime;
    private int rating;
    @Enumerated(EnumType.STRING)
    private Categories category;
    private boolean availability;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    private int costPrice = 0;
    private int currentStock =0;


}
