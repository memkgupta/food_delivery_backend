package com.localbite_express.core.repositories;

import com.localbite_express.core.models.restaurant.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Integer> {
//    Optional<Menu> findByRestaurantId(int restaurantId);
}
