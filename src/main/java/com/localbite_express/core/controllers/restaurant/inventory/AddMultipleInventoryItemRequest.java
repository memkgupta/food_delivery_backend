package com.localbite_express.core.controllers.restaurant.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddMultipleInventoryItemRequest {
    private int restaurant_id;
    List<InventoryItemRequestPOJO> itemRequestList ;

}
