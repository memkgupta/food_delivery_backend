package com.localbite_express.core.models.restaurant;

import com.localbite_express.core.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Restaurant {
    @Id
    @GeneratedValue
    private int id;


    private User user;
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
