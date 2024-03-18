package com.localbite_express.core.services;

import com.localbite_express.core.controllers.order.NewOrderRequest;
import com.localbite_express.core.models.User;
import com.localbite_express.core.models.sales.Sale;
import com.localbite_express.core.models.sales.OrderItem;
import com.localbite_express.core.models.sales.OrderType;
import com.localbite_express.core.models.restaurant.MenuItem;
import com.localbite_express.core.models.restaurant.Restaurant;
import com.localbite_express.core.repositories.MenuItemRepository;
import com.localbite_express.core.repositories.OrderItemRepository;
import com.localbite_express.core.repositories.OrderRepository;
import com.localbite_express.core.repositories.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServices {
private final RestaurantRepository restaurantRepository;
private final OrderRepository orderRepository;
private final MenuItemRepository menuItemRepository;
private final OrderItemRepository orderItemRepository;
    public Sale addNewOfflineOrder(NewOrderRequest request) throws Exception {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurant_id()).orElseThrow(()->new EntityNotFoundException("Restaurant Not Found"));
        if(user.getUserId()==restaurant.getUser_id()){
            Sale sale = orderRepository.save(Sale.builder()
                    .orderType(OrderType.OFFLINE)
                    .restaurant_id(request.getRestaurant_id())

                    .build());
            List<OrderItem> orderItems = new ArrayList<>();
request.getItems().forEach(requestOrderItem -> {
    MenuItem menuItem = menuItemRepository.findById(requestOrderItem.getMenu_item_id()).orElseThrow(()->new EntityNotFoundException("Menu Item of id "+requestOrderItem.getMenu_item_id()+"Was Not Found"));
    OrderItem orderItem = orderItemRepository.save(OrderItem.builder()
            .sale(sale)
            .menuItem(menuItem)
            .quantity(requestOrderItem.getQuantity())
            .totalCost(requestOrderItem.getQuantity()*menuItem.getPrice())
            .build());
    orderItems.add(orderItem);
});
sale.setItems(orderItems);
sale.setEmail(request.getEmail());
sale.setPhone(request.getPhone());
sale.setTotal(calculateTotal(orderItems));

sale.setDiscount(calculateDiscount(request.getDiscount(), sale.getTotal()));
sale.setTax(calculateTax(request.getTax(), sale.getTotal()));
sale.setGrand_total(sale.getTotal()- sale.getDiscount()+ sale.getTax());
return orderRepository.save(sale);
        }
        else{
            throw new Exception("Not Authorized");
        }

    }

    private int calculateTotal(List<OrderItem> orderItems){
        int total = 0;
        for (OrderItem item:orderItems
             ) {
            total+=item.getTotalCost();
        }
        return  total;
    }
    private float calculateDiscount(float percent,int cost){
        return (percent*cost)/100;
    }
    private float calculateTax(float percent,int cost){
        return (percent*cost)/100;
    }
}
