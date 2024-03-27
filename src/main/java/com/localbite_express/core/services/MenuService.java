package com.localbite_express.core.services;

import com.localbite_express.core.config.Exceptions.BadRequestException;
import com.localbite_express.core.config.Exceptions.UnauthorizedException;
import com.localbite_express.core.controllers.restaurant.requests.AddMenuItemRequest;
import com.localbite_express.core.controllers.restaurant.requests.Operation;
import com.localbite_express.core.controllers.restaurant.requests.UpdateInventoryItemRequest;
import com.localbite_express.core.controllers.restaurant.requests.UpdateMenuItemRequest;
import com.localbite_express.core.models.User;
import com.localbite_express.core.models.inventory.InventoryItem;
import com.localbite_express.core.models.restaurant.MenuItem;
import com.localbite_express.core.models.restaurant.Restaurant;
import com.localbite_express.core.models.staff.StaffPerson;
import com.localbite_express.core.models.staff.StaffRole;
import com.localbite_express.core.repositories.MenuItemRepository;
import com.localbite_express.core.repositories.RestaurantRepository;
import com.localbite_express.core.repositories.inventory.InventoryItemRepository;
import com.localbite_express.core.repositories.staff.StaffRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final StaffRepository staffRepository;
    private final InventoryItemRepository inventoryItemRepository;
    public List<MenuItem> addMenuItems(AddMenuItemRequest request){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant =restaurantRepository.findById(request.getRestaurant_id()).orElseThrow(()->new EntityNotFoundException("Restaurant Not Found"));
        StaffPerson staffPerson = staffRepository.findByUserUserId(user.getUserId()).orElseThrow(UnauthorizedException::new);
        if(staffPerson.getRole()== StaffRole.ADMIN&&restaurant.getStaffPeople().contains(staffPerson)){
          List<MenuItem> menuItemList = new ArrayList<>();
            request.getMenuItems().forEach(menuItem -> {
                MenuItem newMenuItem = MenuItem.builder()
                        .name(menuItem.getName())
                        .description(menuItem.getDescription())
                        .price(menuItem.getPrice())
                        .preparationTime(menuItem.getPreparationTime())
                        .rating(0)
                        .category(menuItem.getCategory())
                        .availability(menuItem.isAvailability())
                        .restaurant(restaurant)

                        .costPrice(menuItem.getCostPrice())
                        .build();
                menuItemList.add(newMenuItem);

            });
return menuItemRepository.saveAll(menuItemList);
        }
        else{
            throw new UnauthorizedException();
        }
    }

    public MenuItem updateMenuItem(UpdateMenuItemRequest request){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant =restaurantRepository.findById(request.getRestaurant_id()).orElseThrow(()->new EntityNotFoundException("Restaurant Not Found"));
        StaffPerson staffPerson = staffRepository.findByUserUserId(user.getUserId()).orElseThrow(UnauthorizedException::new);
        if(staffPerson.getRole()== StaffRole.ADMIN&&restaurant.getStaffPeople().contains(staffPerson)){
            MenuItem menuItem = menuItemRepository.findById(request.getId()).orElseThrow(()->new EntityNotFoundException("Menu Item Does not exists"));
          menuItem.setName(request.getName());
          menuItem.setAvailability(request.isAvailable());
          menuItem.setDescription(request.getDescription());
          menuItem.setPrice(request.getPrice());
          menuItem.setCostPrice(request.getCostPrice());
          menuItem.setPreparationTime(request.getPreparationTime());
          menuItem.setCategory(request.getCategory());
         return menuItemRepository.save(menuItem);

        }
        else{
            throw new UnauthorizedException();
        }
    }
    public MenuItem addInventoryItems(int restaurant_id,List<Long> itemList,int menuItemId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant =restaurantRepository.findById(restaurant_id).orElseThrow(()->new EntityNotFoundException("Restaurant Not Found"));
        StaffPerson staffPerson = staffRepository.findByUserUserId(user.getUserId()).orElseThrow(UnauthorizedException::new);
        if(staffPerson.getRole()== StaffRole.ADMIN&&restaurant.getStaffPeople().contains(staffPerson)){
            MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(()->new EntityNotFoundException("Menu Item Does not exists"));
            itemList.forEach(id -> {
                InventoryItem item = inventoryItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
               menuItem.getInventoryItemList().add(item);
            });

            return menuItemRepository.save(menuItem);

        }
        else{
            throw new UnauthorizedException();
        }
    }
    public void deleteMenuItem(int item_id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MenuItem menuItem = menuItemRepository.findById(item_id).orElseThrow(EntityNotFoundException::new);
        Restaurant restaurant =restaurantRepository.findById(menuItem.getRestaurant().getId()).orElseThrow(()->new EntityNotFoundException("Restaurant Not Found"));
        StaffPerson staffPerson = staffRepository.findByUserUserId(user.getUserId()).orElseThrow(UnauthorizedException::new);
        if(staffPerson.getRole()== StaffRole.ADMIN&&restaurant.getStaffPeople().contains(staffPerson)) {
        menuItemRepository.deleteById(item_id);
        }
        else{
            throw new UnauthorizedException();
        }
    }

    public void updateInventoryList(int item_id, List<UpdateInventoryItemRequest> updatetList){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MenuItem menuItem = menuItemRepository.findById(item_id).orElseThrow(EntityNotFoundException::new);
        Restaurant restaurant =restaurantRepository.findById(menuItem.getRestaurant().getId()).orElseThrow(()->new EntityNotFoundException("Restaurant Not Found"));
        StaffPerson staffPerson = staffRepository.findByUserUserId(user.getUserId()).orElseThrow(UnauthorizedException::new);
        if(staffPerson.getRole()== StaffRole.ADMIN&&restaurant.getStaffPeople().contains(staffPerson)) {
            Map<Long,InventoryItem> inventoryMap = menuItem.getInventoryItemList().stream()
                            .collect(Collectors.toMap(InventoryItem::getId, value->value));


            Set<InventoryItem> itemsToDelete = new HashSet<>();
            List<Long> itemsToAdd = new ArrayList<>(); // Corrected declaration

            // Iterate through the updateList and update menuItem.getInventoryItemList() accordingly
            updatetList.forEach(item -> {
                if (item.getOperation() == Operation.DELETE) {
                    if(inventoryMap.containsKey(item.getInventory_id())){
                        itemsToDelete.add(inventoryMap.get(item.getInventory_id()));

                    }
                    else{
                        throw new BadRequestException("Inventory Items are wrong");
                    }
                } else if (item.getOperation() == Operation.ADD) {
                    // Add the ID to the list of items to add
                    itemsToAdd.add(item.getInventory_id());
                }
            });

            menuItem.getInventoryItemList().addAll(inventoryItemRepository.findAllById(itemsToAdd));
            menuItem.getInventoryItemList().removeAll(itemsToDelete);
            menuItemRepository.save(menuItem);
        }
        else{
            throw new UnauthorizedException();
        }
    }
}
