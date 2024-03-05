package com.localbite_express.core.controllers.restaurant;

import com.localbite_express.core.controllers.restaurant.requests.RegisterRestaurantRequest;
import com.localbite_express.core.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

private final RestaurantRepository restaurantRepository;
    @PostMapping("/register_restaurant")
    public MappingJacksonValue registerRestaurant(@RequestBody RegisterRestaurantRequest request){


        return new MappingJacksonValue("");
    }

}
