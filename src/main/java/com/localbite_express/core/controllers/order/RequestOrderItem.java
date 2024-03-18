package com.localbite_express.core.controllers.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderItem {
    private int menu_item_id;
    private int quantity;
}