package com.localbite_express.core.repositories;

import com.localbite_express.core.models.restaurant.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem,Integer> {

}
