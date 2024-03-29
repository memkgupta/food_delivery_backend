package com.localbite_express.core.controllers.restaurant;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.localbite_express.core.controllers.restaurant.inventory.AddInventoryItemRequest;
import com.localbite_express.core.controllers.restaurant.requests.*;
import com.localbite_express.core.models.User;
import com.localbite_express.core.models.restaurant.MenuItem;
import com.localbite_express.core.models.restaurant.Restaurant;

import com.localbite_express.core.models.staff.StaffPerson;
import com.localbite_express.core.services.RestaurantService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

private final RestaurantService restaurantService;

    @PostMapping("/register_restaurant")
    public ResponseEntity<?> registerRestaurant(@RequestBody RegisterRestaurantRequest request){

        try{
            Restaurant restaurant = restaurantService.registerRestaurant(request);
            SimpleBeanPropertyFilter restaurantFilter = SimpleBeanPropertyFilter
                    .filterOutAllExcept("name","id","address","description","latitude","longitude","contact_number","user");
            SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept("userId","name","email","phone","role");

            FilterProvider filterProvider = new SimpleFilterProvider()
                                        .addFilter("restaurantFilter",restaurantFilter)
                                        .addFilter("userFilter",userFilter);

            MappingJacksonValue response = new MappingJacksonValue(restaurant);
            response.setFilters(filterProvider);
            return ResponseEntity.ok().body(response);

        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e);
        }


    }
//    @PostMapping("/add_menu")
//    public ResponseEntity<?> addMenu(@RequestBody AddMenuRequest request){
//      try{
//          Menu menu = restaurantService.addMenu(request);
//          SimpleBeanPropertyFilter menuFilter = SimpleBeanPropertyFilter
//                  .filterOutAllExcept("menuItems","id");
//          SimpleBeanPropertyFilter menuItemFilter = SimpleBeanPropertyFilter
//                  .filterOutAllExcept("id","name","price","category","availability","category","rating","preparationTime");
//
//          FilterProvider filterProvider = new SimpleFilterProvider()
//                  .addFilter("menuFilter",menuFilter)
//                  .addFilter("menuItemFilter",menuItemFilter);
//          MappingJacksonValue response = new MappingJacksonValue(menu);
//          response.setFilters(filterProvider);
//          return ResponseEntity.ok().body(response);
//      }
//      catch (Exception e){
//          return ResponseEntity.badRequest().body(e.getMessage());
//      }
//    }

    @PutMapping("/update_menu_item")
    public ResponseEntity<?> updateMenuItem(@RequestBody UpdateMenuItemRequest request){
try{

MenuItem updatedItem = restaurantService.updateMenuItem(request);
    SimpleBeanPropertyFilter menuItemFilter = SimpleBeanPropertyFilter
            .filterOutAllExcept("id","name","price","category","availability","category","rating","preparationTime");
    FilterProvider filterProvider = new SimpleFilterProvider()
            .addFilter("menuItemFilter",menuItemFilter);
    MappingJacksonValue response = new MappingJacksonValue(updatedItem);
    response.setFilters(filterProvider);
    return ResponseEntity.ok(response);

}
catch (Exception e){
    return ResponseEntity.badRequest().body(e.getMessage());
}
    }


    @DeleteMapping("/delete_menu_item")
    public ResponseEntity<?> deleteMenuItem(@RequestParam int id,@RequestParam int restaurant_id){
        System.out.println("dddd");
        try{
            restaurantService.deleteMenuItem(id,restaurant_id);
            return ResponseEntity.ok("Menu Item deleted successfully");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//    @GetMapping("/menu")
//    public ResponseEntity<?> getMenu(@RequestParam int restaurant_id){
//        try{
//            Menu menu = restaurantService.getMenu(restaurant_id);
//            SimpleBeanPropertyFilter menuFilter = SimpleBeanPropertyFilter
//                    .filterOutAllExcept("menuItems","id");
//            SimpleBeanPropertyFilter menuItemFilter = SimpleBeanPropertyFilter
//                    .filterOutAllExcept("id","name","price","category","availability","category","rating","preparationTime");
//
//            FilterProvider filterProvider = new SimpleFilterProvider()
//                    .addFilter("menuFilter",menuFilter)
//                    .addFilter("menuItemFilter",menuItemFilter);
//            MappingJacksonValue response = new MappingJacksonValue(menu);
//            response.setFilters(filterProvider);
//            return ResponseEntity.ok().body(response);
//        }
//        catch (Exception e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
    @GetMapping("/restaurant")
    public ResponseEntity<?> getRestaurantByid(@RequestParam int id){
      try{
          Restaurant restaurant = restaurantService.findRestaurant(id);
          SimpleBeanPropertyFilter menuFilter = SimpleBeanPropertyFilter
                  .filterOutAllExcept("menuItems","id");
          SimpleBeanPropertyFilter menuItemFilter = SimpleBeanPropertyFilter
                  .filterOutAllExcept("id","name","price","category","availability","category","rating","preparationTime");
          SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","userId");
          FilterProvider filterProvider = new SimpleFilterProvider()
                  .addFilter("menuFilter",menuFilter)
                  .addFilter("menuItemFilter",menuItemFilter)
                  .addFilter("userFilter",userFilter);
          MappingJacksonValue response = new MappingJacksonValue(restaurant);
          response.setFilters(filterProvider);
          return ResponseEntity.ok(response);
      }
      catch (Exception e){
          return ResponseEntity.notFound().build();
      }
    }
    @GetMapping("/my_restaurant")
    public ResponseEntity<?> myRestaurant(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Restaurant restaurant = restaurantService.findRestaurantByUserId(user.getUserId());

        String[] Restaurant_filter_string = {
                "id","user_id","name","description","menuItems"
        };
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(Restaurant_filter_string);
        SimpleBeanPropertyFilter menuFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("menuItems","id");
        SimpleBeanPropertyFilter menuItemFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","price","category","availability","category","rating","preparationTime");
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("restaurantFilter",filter)
                .addFilter("menuFilter",menuFilter)
                .addFilter("menuItemFilter",menuItemFilter);
        MappingJacksonValue response = new MappingJacksonValue(restaurant);
        response.setFilters(filterProvider);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/menu_item/{id}")
    public ResponseEntity<?> getMenuItemById(@PathVariable int id){
        try{
            String[] fields = {
                    "id",
                    "name",
                    "description",
                    "price",
                    "costPrice",
                    "preparationTime",
                    "rating",
                    "category",
                    "availability"
            };
            SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
            SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("menuItemFilter",simpleBeanPropertyFilter);
            MappingJacksonValue res =new MappingJacksonValue(restaurantService.getMenuItem(id));
            res.setFilters(filterProvider);
            return ResponseEntity.ok(res);
        }
        catch (Exception e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    @PostMapping("/staff/add")
    public ResponseEntity<?> addStaff(@RequestBody AddStaffPersonRequest request){

      List<StaffPerson> result=  restaurantService.addStaffPersons(request);
        SimpleBeanPropertyFilter staffPersonFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id","user","role");
      SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id","email");
      SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("staffPersonFilter",staffPersonFilter)
              .addFilter("userFilter",userFilter);
      MappingJacksonValue res = new MappingJacksonValue(result);
      res.setFilters(filterProvider);
      return ResponseEntity.ok(res);
    }
    @PostMapping("/inventory/add")
    public ResponseEntity<?> addInventoryItem(@RequestBody AddInventoryItemRequest request){
        String[] inventoryFilter = {
                "id","name","measures","quantityOnHand","minimumStockLevel","unitPrice","supplier","category","shelfLife","expiryDate","refillDate"
        };
        SimpleBeanPropertyFilter inventoryItemFilter = SimpleBeanPropertyFilter.filterOutAllExcept(inventoryFilter);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("inventoryItemFilter",inventoryItemFilter)
                ;
        MappingJacksonValue res = new MappingJacksonValue(restaurantService.addInventoryItem(request));
        res.setFilters(filterProvider);
        return ResponseEntity.ok(res);
    }
}
