package com.localbite_express.core.controllers.restaurant.requests;

import com.localbite_express.core.models.Categories;

import com.localbite_express.core.models.restaurant.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddMenuItemRequest {

    private int restaurant_id;
    List<MenuItem> menuItems;
}
