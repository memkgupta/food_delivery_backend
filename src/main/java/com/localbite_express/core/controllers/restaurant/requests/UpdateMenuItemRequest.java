package com.localbite_express.core.controllers.restaurant.requests;

import com.localbite_express.core.models.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMenuItemRequest {
    private int id;
    private String name;
    private String description;
    private int price;
    private int costPrice;
    private long preparationTime;
    private Categories category;
   private int restaurant_id;
   private boolean available;
//   private List<Long> inventoryItems;

}
