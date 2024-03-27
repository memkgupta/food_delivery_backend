package com.localbite_express.core.controllers.restaurant;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.localbite_express.core.controllers.restaurant.requests.AddMenuItemRequest;
import com.localbite_express.core.controllers.restaurant.requests.UpdateInventoryItemRequest;
import com.localbite_express.core.controllers.restaurant.requests.UpdateMenuItemRequest;
import com.localbite_express.core.models.restaurant.MenuItem;
import com.localbite_express.core.repositories.MenuItemRepository;
import com.localbite_express.core.services.MenuService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu-item")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    @PostMapping("/add")
    public ResponseEntity<?> addMenuItem(@RequestBody AddMenuItemRequest request){

            List<MenuItem> menuItem = menuService.addMenuItems(request);
            String [] menuFilterArgs = {"id","name","price","category","availability","category","rating","preparationTime"};
            SimpleBeanPropertyFilter menuItemFilter = SimpleBeanPropertyFilter
                    .filterOutAllExcept(menuFilterArgs);
            String[] inventoryFilter = {
                    "id","name"
            };
            SimpleBeanPropertyFilter inventoryItemFilter = SimpleBeanPropertyFilter.filterOutAllExcept(inventoryFilter);

            FilterProvider filterProvider = new SimpleFilterProvider()
                    .addFilter("menuItemFilter",menuItemFilter)
                    .addFilter("inventoryItemFilter",inventoryItemFilter);
            MappingJacksonValue response = new MappingJacksonValue(menuItem);
            response.setFilters(filterProvider);
            return ResponseEntity.ok(response);

    }
    @PutMapping("/update")
    public ResponseEntity<?> updateMenuItem(@RequestBody UpdateMenuItemRequest request){
        String [] menuFilterArgs = {"id","name","price","category","availability","category","rating","preparationTime"};
        SimpleBeanPropertyFilter menuItemFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept(menuFilterArgs);
        String[] inventoryFilter = {
                "id","name"
        };
        SimpleBeanPropertyFilter inventoryItemFilter = SimpleBeanPropertyFilter.filterOutAllExcept(inventoryFilter);
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("menuItemFilter",menuItemFilter)
                .addFilter("inventoryItemFilter",inventoryItemFilter);

   MappingJacksonValue response = new MappingJacksonValue(menuService.updateMenuItem(request));
   response.setFilters(filterProvider);
   return ResponseEntity.ok(response);
    }
    @PutMapping("/add-inventory")
    public ResponseEntity<?> updateMenuItem(@RequestParam("rid") int restaurant_id,@RequestParam("mit_id") int item_id,@RequestBody List<Long> list){
        String [] menuFilterArgs = {"id","name","inventoryItemList"};
        SimpleBeanPropertyFilter menuItemFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept(menuFilterArgs);
        String[] inventoryFilter = {
                "id","name"
        };
        SimpleBeanPropertyFilter inventoryItemFilter = SimpleBeanPropertyFilter.filterOutAllExcept(inventoryFilter);
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("menuItemFilter",menuItemFilter)
                .addFilter("inventoryItemFilter",inventoryItemFilter);

        MappingJacksonValue response = new MappingJacksonValue(menuService.addInventoryItems(restaurant_id,list,item_id));
        response.setFilters(filterProvider);
        return ResponseEntity.ok(response);
    }
   @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMenuItem(@RequestParam("mit_id") int item_id){
   menuService.deleteMenuItem(item_id);
   return ResponseEntity.ok("Menu Item Deleted Successfully");
   }
   @PutMapping("/update-inventory")
    public ResponseEntity<?> updateInventory(@RequestParam("mit_id") int item_id, @RequestBody List<UpdateInventoryItemRequest> inventoryItems){
menuService.updateInventoryList(item_id,inventoryItems);
return ResponseEntity.ok("Inventory List Updated SuccessFully");
   }
}
