package com.localbite_express.core.controllers.order;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.localbite_express.core.models.sales.Sale;
import com.localbite_express.core.services.OrderServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderServices orderServices;
    @PostMapping("/new")
    public ResponseEntity<?> addNewOfflineOrder(@RequestBody NewOrderRequest request){
        try {
            Sale sale = orderServices.addNewOfflineOrder(request);
            String[] Restaurant_filter_string = {
                    "id","user_id","name","description","menuItems"
            };
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(Restaurant_filter_string);

            SimpleBeanPropertyFilter menuItemFilter = SimpleBeanPropertyFilter
                    .filterOutAllExcept("name","price");
            FilterProvider filterProvider = new SimpleFilterProvider()
                    .addFilter("restaurantFilter",filter)
                    .addFilter("menuItemFilter",menuItemFilter);
            MappingJacksonValue response = new MappingJacksonValue(sale);
            return ResponseEntity.ok(response);
        }
catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
}

    }
}
