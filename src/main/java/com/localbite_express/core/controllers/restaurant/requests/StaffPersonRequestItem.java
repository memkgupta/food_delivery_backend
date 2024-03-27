package com.localbite_express.core.controllers.restaurant.requests;

import com.localbite_express.core.models.staff.StaffRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffPersonRequestItem{
    private StaffRole role;
    private String email;

}