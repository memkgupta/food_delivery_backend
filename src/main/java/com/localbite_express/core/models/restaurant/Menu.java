package com.localbite_express.core.models.restaurant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Menu {
    @Id
    @GeneratedValue
    private int id;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    List<MenuItem> menuItems;
    @OneToOne(mappedBy = "menu")
    private Restaurant restaurant;
}
