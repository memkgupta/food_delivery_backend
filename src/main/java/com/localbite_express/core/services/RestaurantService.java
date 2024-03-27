package com.localbite_express.core.services;

import com.localbite_express.core.config.Exceptions.PostgresException;
import com.localbite_express.core.config.Exceptions.UnauthorizedException;
import com.localbite_express.core.controllers.restaurant.inventory.AddInventoryItemRequest;
import com.localbite_express.core.controllers.restaurant.requests.*;
import com.localbite_express.core.models.Role;
import com.localbite_express.core.models.User;
import com.localbite_express.core.models.inventory.InventoryItem;
import com.localbite_express.core.models.restaurant.Menu;
import com.localbite_express.core.models.restaurant.MenuItem;
import com.localbite_express.core.models.restaurant.Restaurant;
import com.localbite_express.core.models.staff.StaffPerson;
import com.localbite_express.core.models.staff.StaffRole;
import com.localbite_express.core.repositories.MenuItemRepository;
import com.localbite_express.core.repositories.MenuRepository;
import com.localbite_express.core.repositories.RestaurantRepository;
import com.localbite_express.core.repositories.UserRepository;
import com.localbite_express.core.repositories.inventory.CategoryRepository;
import com.localbite_express.core.repositories.inventory.InventoryItemRepository;
import com.localbite_express.core.repositories.inventory.SupplierRepository;
import com.localbite_express.core.repositories.staff.StaffRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryItemRepository inventoryItemRepository;
    public Restaurant registerRestaurant(RegisterRestaurantRequest request) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()){
            throw new UnauthorizedException();
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
               staffRepository.save(StaffPerson.builder()
                               .restaurant(restaurantSaved)
                               .role(StaffRole.ADMIN)
                               .user(user)

                       .build());

               userRepository.save(user);
            }
            catch (Exception e){
                throw new Exception(e);
            }
        return restaurantSaved;

    }

    public List<StaffPerson> addStaffPersons(AddStaffPersonRequest request){
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow(()->new EntityNotFoundException("Restaurant not found"));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getUserId()+" "+user.getName());
        StaffPerson staffPerson = staffRepository.findByUserUserId(user.getUserId()).orElseThrow(UnauthorizedException::new);
        System.out.println(staffPerson.getRole()+" "+staffPerson.getUser().getUserId());
if(staffPerson.getRole().equals(StaffRole.ADMIN)){
    List<StaffPerson> list = new ArrayList<>();
    request.getStaffPersonList().forEach(item->{
list.add(StaffPerson.builder()
                .restaurant(restaurant)
                .role(item.getRole())
                .user(userRepository.findByEmail(item.getEmail()).orElseThrow(()->new EntityNotFoundException("One of the staff's user email is incorrect")))
        .build());
    });
try{
    return staffRepository.saveAll(list);
}
catch (Exception e){
    throw new PostgresException(e.getMessage());
}

}
else {
    throw new UnauthorizedException();
}
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

    public InventoryItem addInventoryItem(AddInventoryItemRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StaffPerson staffPerson = staffRepository.findByUserUserId(user.getUserId()).orElseThrow(UnauthorizedException::new);
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurant_id()).orElseThrow(EntityNotFoundException::new);
        if(staffPerson.getRole().equals(StaffRole.ADMIN)&&restaurant.getStaffPeople().contains(staffPerson)){

            InventoryItem inventoryItem = InventoryItem.builder()
                    .restaurant(restaurant)
                    .expiryDate(request.getExpiryDate())
                    .unitPrice(request.getUnitPrice())
                    .name(request.getName())
                    .shelfLife(request.getShelfLife())
                    .supplier(supplierRepository.findById((long) request.getSupplier_id()).orElse(null))
                    .refillDate(request.getRefillDate())
                    .minimumStockLevel(request.getMinimumStockLevel())
                    .measures(request.getMeasures())
                    .quantityOnHand(request.getQuantityOnHand())
                    .category(categoryRepository.findById((long)request.getCategory_id()).orElse(null))
                    .build();
            return inventoryItemRepository.save(inventoryItem);
        }
        else{
            throw new UnauthorizedException();
        }
    }
}
