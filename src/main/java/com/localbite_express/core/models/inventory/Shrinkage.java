package com.localbite_express.core.models.inventory;

import com.localbite_express.core.models.restaurant.MenuItem;
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
public class Shrinkage {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "menuItem_id") // Foreign key column name
    private MenuItem menuItem;
}
