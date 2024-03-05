package com.localbite_express.core.models.restaurant;

import com.fasterxml.jackson.annotation.JsonFilter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonFilter("restaurantFilter")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Restaurant {
    @Id
    @GeneratedValue
    private int id;
    private int user_id;
    private String name;
    private String description;
    private String address;
    private double latitude;
    private double longitude;
    private String contact_number;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu", referencedColumnName = "id")
    private Menu menu;
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    private List<Reviews> reviews;

}
