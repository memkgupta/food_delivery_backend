package com.localbite_express.core.repositories;

import com.localbite_express.core.models.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
    @Query("select e from Restaurant e where e.user_id = ?1")
    Optional<Restaurant> findByUserId(int userId);
}
