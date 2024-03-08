package com.localbite_express.core.controllers.restaurant.requests;

import com.localbite_express.core.models.Categories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddMenuItemRequest {
    private int menu_id;
    private String name;
    private String description;
    private int price;

    private TimeUnit preparationTime;
    private int rating;

    private Categories category;
    private boolean availability;
}
