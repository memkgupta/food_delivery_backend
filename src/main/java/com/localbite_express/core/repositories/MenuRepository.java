package com.localbite_express.core.repositories;

import com.localbite_express.core.models.restaurant.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu,Integer> {
}
