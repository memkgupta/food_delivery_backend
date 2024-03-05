package com.localbite_express.core.repositories;

import com.localbite_express.core.models.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
}
