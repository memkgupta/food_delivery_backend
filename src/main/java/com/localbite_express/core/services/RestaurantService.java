package com.localbite_express.core.services;

import com.localbite_express.core.controllers.restaurant.requests.AddMenuRequest;
import com.localbite_express.core.controllers.restaurant.requests.RegisterRestaurantRequest;
import com.localbite_express.core.models.User;
import com.localbite_express.core.models.restaurant.Menu;
import com.localbite_express.core.models.restaurant.MenuItem;
import com.localbite_express.core.models.restaurant.Restaurant;
import com.localbite_express.core.repositories.MenuItemRepository;
import com.localbite_express.core.repositories.MenuRepository;
import com.localbite_express.core.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    public Restaurant registerRestaurant(RegisterRestaurantRequest request) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()){
            throw new Exception("Unauthorized");
        }
        User user = (User) authentication.getPrincipal();
            Restaurant restaurant = Restaurant.builder()
                    .name(request.getName())
                    .address(request.getAddress())
                    .description(request.getDesc())
                    .contact_number(request.getContact_number())
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .user_id(user.getUserId())
                    .build();
        return restaurantRepository.save(restaurant);

    }

    public Menu addMenu(AddMenuRequest addMenuRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()){
            throw new Exception("Unauthorized");
        }
        Restaurant restaurant = restaurantRepository.findById(addMenuRequest.getRestaurant_id()).orElseThrow();
        Menu menu = menuRepository.save(Menu.builder().restaurant(restaurant).build());
        restaurant.setMenu(menu);
        restaurantRepository.save(restaurant);
        List<MenuItem> items ;
        for (MenuItem item : addMenuRequest.getItems()) {
            item.setMenu(menu);

        }
        System.out.println("hello ladies");
        items=menuItemRepository.saveAll(addMenuRequest.getItems());
menu.setMenuItems(items);
return menuRepository.save(menu);
    }
}
