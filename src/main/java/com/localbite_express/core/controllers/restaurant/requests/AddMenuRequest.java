package com.localbite_express.core.controllers.restaurant.requests;

import com.localbite_express.core.models.restaurant.Menu;
import com.localbite_express.core.models.restaurant.MenuItem;
import com.localbite_express.core.models.restaurant.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddMenuRequest {
  private int restaurant_id;
  private List<MenuItem> items;
}
