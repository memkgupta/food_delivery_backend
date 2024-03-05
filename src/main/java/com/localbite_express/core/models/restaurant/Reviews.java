package com.localbite_express.core.models.restaurant;

import com.localbite_express.core.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reviews {
    @Id
    @GeneratedValue
    private int id;
    private String description;
    private int rating;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate timeStamp;
}
