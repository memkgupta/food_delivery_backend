package com.localbite_express.core.controllers.restaurant.inventory;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.localbite_express.core.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    @PostMapping("/add-multiple")
    public ResponseEntity<?> addMultipleItems(@RequestBody AddMultipleInventoryItemRequest request){
        String[] inventoryFilter = {
                "id","name","measures","quantityOnHand","minimumStockLevel","unitPrice","supplier","category","shelfLife","expiryDate","refillDate"
        };
        SimpleBeanPropertyFilter inventoryItemFilter = SimpleBeanPropertyFilter.filterOutAllExcept(inventoryFilter);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("inventoryItemFilter",inventoryItemFilter)
                ;
        MappingJacksonValue res = new MappingJacksonValue(inventoryService.addInventoryItems(request));
        res.setFilters(filterProvider);
        return ResponseEntity.ok(res);
    }
}
