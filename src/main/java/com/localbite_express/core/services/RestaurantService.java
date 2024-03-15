package com.localbite_express.core.services;

import com.localbite_express.core.controllers.restaurant.requests.AddMenuItemRequest;
import com.localbite_express.core.controllers.restaurant.requests.AddMenuRequest;
import com.localbite_express.core.controllers.restaurant.requests.RegisterRestaurantRequest;
import com.localbite_express.core.controllers.restaurant.requests.UpdateMenuItemRequest;
import com.localbite_express.core.models.Role;
import com.localbite_express.core.models.User;
import com.localbite_express.core.models.restaurant.Menu;
import com.localbite_express.core.models.restaurant.MenuItem;
import com.localbite_express.core.models.restaurant.Restaurant;
import com.localbite_express.core.repositories.MenuItemRepository;
import com.localbite_express.core.repositories.MenuRepository;
import com.localbite_express.core.repositories.RestaurantRepository;
import com.localbite_express.core.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final UserRepository userRepository;
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
            Restaurant restaurantSaved;
            try {
               restaurantSaved= restaurantRepository.save(restaurant);
               user.setRole(Role.RESTAURANT_ADMIN);
               userRepository.save(user);
            }
            catch (Exception e){
                throw new Exception(e);
            }
        return restaurantSaved;

    }

//    public Menu addMenu(AddMenuRequest addMenuRequest) throws Exception {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        var user = (User) authentication.getPrincipal();
//
//        if(!authentication.isAuthenticated()||!user.getRole().equals(Role.RESTAURANT_ADMIN)){
//            throw new Exception("Unauthorized");
//        }
//        Restaurant restaurant = restaurantRepository.findById(addMenuRequest.getRestaurant_id()).orElseThrow();
//        Menu menu = menuRepository.save(Menu.builder().restaurant(restaurant).build());
//        restaurant.setMenu(menu);
//        restaurantRepository.save(restaurant);
//        List<MenuItem> items ;
//        for (MenuItem item : addMenuRequest.getItems()) {
//            item.setMenu(menu);
//
//        }
//        System.out.println("hello ladies");
//        items=menuItemRepository.saveAll(addMenuRequest.getItems());
//menu.setMenuItems(items);
//return menuRepository.save(menu);
//    }
    public MenuItem addMenuItem(AddMenuItemRequest request) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (User) authentication.getPrincipal();

        if(!authentication.isAuthenticated()||!user.getRole().equals(Role.RESTAURANT_ADMIN)){
            throw new Exception("Unauthorized");
        }
        Menu menu;
//       try{
//        menu = menuRepository.findById(request.getMenu_id())
//                   .orElseThrow(()-> new EntityNotFoundException("No Menu Found"));
//       }
//       catch (EntityNotFoundException e){
//           Menu newMenu = Menu.builder().restaurant().build()
//       }
        System.out.println(request.getRestaurant_id());
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurant_id()).orElseThrow(()->new EntityNotFoundException("Restaurant Not Found"));
        if(restaurant.getUser_id()!= user.getUserId()){
            throw new Exception("Unauthorized");
        }
MenuItem menuItem = MenuItem.builder().restaurant(restaurant).availability(request.isAvailability())
        .category(request.getCategory())
        .description(request.getDescription())
        .price(request.getPrice())
        .preparationTime(request.getPreparationTime())
        .name(request.getName())
        .rating(request.getRating())
        .costPrice(request.getCostPrice())
        .build();

return menuItemRepository.save(menuItem);    }
    public MenuItem getMenuItem(int id) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (User) authentication.getPrincipal();

        if(!authentication.isAuthenticated()||!user.getRole().equals(Role.RESTAURANT_ADMIN)){
            throw new Exception("Unauthorized");
        }
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Menu Item Not Found"));
        return menuItem;
    }
    public MenuItem updateMenuItem(UpdateMenuItemRequest request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (User) authentication.getPrincipal();
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurant_id()).orElseThrow(()-> new EntityNotFoundException("No Restaurant Found"));
        if(restaurant.getUser_id()!= user.getUserId()){
            throw new Exception("Unauthorized");
        }
        if(!authentication.isAuthenticated()||!user.getRole().equals(Role.RESTAURANT_ADMIN)){
            throw new Exception("Unauthorized");
        }
        MenuItem item = menuItemRepository.findById(request.getId()).orElseThrow(()-> new EntityNotFoundException("No MenuItem Found"));
        item.setName(request.getName());
        item.setCategory(request.getCategory());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setPreparationTime(request.getPreparationTime());
        item.setCostPrice(request.getCostPrice());
        return menuItemRepository.save(item);
    }

    public void deleteMenuItem(int id,int restaurant_id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (User) authentication.getPrincipal();
        Restaurant restaurant = restaurantRepository.findById(restaurant_id).orElseThrow(()-> new EntityNotFoundException("No Restaurant Found"));
        if(restaurant.getUser_id()!= user.getUserId()){
            throw new Exception("Unauthorized");
        }
        if(!authentication.isAuthenticated()||!(user.getRole().equals(Role.RESTAURANT_ADMIN))){
            throw new Exception("Unauthorized");
        }

        MenuItem item = menuItemRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No Menu Item Found"));

        menuItemRepository.deleteById(id);
    }

//    public Menu getMenu(int restaurantId) {
//       return menuItemRepository.findAllByRestaurantId()
//    }

    public Restaurant findRestaurant(int id) {
        return restaurantRepository.findById(id).orElseThrow();
    }

    public Restaurant findRestaurantByUserId(int id){
        return restaurantRepository.findByUserId(id).orElseThrow(()->new EntityNotFoundException("No Restaurant Found"));
    }
}
