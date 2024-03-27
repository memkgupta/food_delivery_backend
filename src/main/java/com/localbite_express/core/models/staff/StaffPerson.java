package com.localbite_express.core.models.staff;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.localbite_express.core.models.User;
import com.localbite_express.core.models.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonFilter("staffPersonFilter")
public class StaffPerson {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn (unique = true,name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private StaffRole role;
}
