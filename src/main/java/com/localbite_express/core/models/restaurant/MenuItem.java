package com.localbite_express.core.models.restaurant;

import com.localbite_express.core.models.Categories;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MenuItem {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    private int price;
    @Enumerated(EnumType.STRING)
    private TimeUnit preparationTime;
    private int rating;
    @Enumerated(EnumType.STRING)
    private Categories category;
    private boolean availability;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

}
