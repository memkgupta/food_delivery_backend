package com.localbite_express.core.controllers.restaurant.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateInventoryItemRequest {
    long inventory_id;
    Operation operation;
}
