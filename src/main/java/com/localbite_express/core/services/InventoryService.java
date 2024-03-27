package com.localbite_express.core.services;


import com.localbite_express.core.config.Exceptions.UnauthorizedException;
import com.localbite_express.core.controllers.restaurant.inventory.AddMultipleInventoryItemRequest;
import com.localbite_express.core.models.User;
import com.localbite_express.core.models.inventory.InventoryItem;
import com.localbite_express.core.models.restaurant.Restaurant;
import com.localbite_express.core.models.staff.StaffPerson;
import com.localbite_express.core.models.staff.StaffRole;
import com.localbite_express.core.repositories.RestaurantRepository;
import com.localbite_express.core.repositories.inventory.CategoryRepository;
import com.localbite_express.core.repositories.inventory.InventoryItemRepository;
import com.localbite_express.core.repositories.inventory.SupplierRepository;
import com.localbite_express.core.repositories.staff.StaffRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
private final RestaurantRepository restaurantRepository;
private final StaffRepository staffRepository;
private final CategoryRepository categoryRepository;
private final SupplierRepository supplierRepository;
private final InventoryItemRepository inventoryItemRepository;
    public List<InventoryItem> addInventoryItems(AddMultipleInventoryItemRequest request)  {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurant_id()).orElseThrow(()->new EntityNotFoundException("Restaurant Not Found"));
        StaffPerson staffPerson = staffRepository.findByUserUserId(user.getUserId()).orElseThrow(()->new EntityNotFoundException("Not authorized"));
        if(restaurant.getStaffPeople().contains(staffPerson)&&staffPerson.getRole().equals(StaffRole.ADMIN)){
            List<InventoryItem> list = new ArrayList<>();
           request.getItemRequestList().forEach(item -> {
              InventoryItem inventoryItem = InventoryItem.builder()
                      .name(item.getName())
                      .measures(item.getMeasures())
                      .category(categoryRepository.findById((long)item.getCategory_id()).orElse(null))
                      .refillDate(item.getRefillDate())
                      .quantityOnHand(item.getQuantityOnHand())
                      .minimumStockLevel(item.getMinimumStockLevel())
                      .unitPrice(item.getUnitPrice())
                      .supplier(supplierRepository.findById(item.getSupplier_id()).orElse(null))
                      .shelfLife(item.getShelfLife())
                      .expiryDate(item.getExpiryDate())
                      .restaurant(restaurant)
                      .build();
              list.add(inventoryItem);
           });
return inventoryItemRepository.saveAll(list);
        }
        else{
throw new UnauthorizedException();
        }
    }

}
