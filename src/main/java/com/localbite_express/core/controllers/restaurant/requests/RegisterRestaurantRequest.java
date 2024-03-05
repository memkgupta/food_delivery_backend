package com.localbite_express.core.controllers.restaurant.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRestaurantRequest {
    private String name;
    private String desc;
    private String address;
    private String contact_number;
    private double latitude;
    private double longitude;
}
