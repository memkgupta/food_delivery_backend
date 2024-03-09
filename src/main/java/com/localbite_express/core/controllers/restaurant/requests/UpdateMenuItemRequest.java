package com.localbite_express.core.controllers.restaurant.requests;

import com.localbite_express.core.models.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMenuItemRequest {
    private int menu_item_id;
    private String name;
    private String description;
    private int price;
    private int costPrice;
    private long preparationTime;
    private int restaurantId;

    private Categories category;

}
