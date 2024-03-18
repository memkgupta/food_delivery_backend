package com.localbite_express.core.controllers.order;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderRequest {


    private int restaurant_id;
    private List<RequestOrderItem> items;
    private float discount;
    private float tax;
    private int grand_total;
    private String email;
    private String phone;
}


