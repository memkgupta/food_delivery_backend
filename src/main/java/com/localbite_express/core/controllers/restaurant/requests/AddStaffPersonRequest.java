package com.localbite_express.core.controllers.restaurant.requests;


import com.localbite_express.core.models.staff.StaffPerson;
import com.localbite_express.core.models.staff.StaffRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddStaffPersonRequest {
    private int restaurantId;
private long staff_id;
private List<StaffPersonRequestItem> staffPersonList;
}
