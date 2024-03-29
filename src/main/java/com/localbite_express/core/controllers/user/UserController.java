package com.localbite_express.core.controllers.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.localbite_express.core.models.User;
import com.localbite_express.core.models.restaurant.Restaurant;
import com.localbite_express.core.repositories.RestaurantRepository;
import com.localbite_express.core.repositories.UserRepository;
import com.localbite_express.core.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final static String[] USER_FILTER = {
            "name","email","id","phone","role"
    };
    private final UserRepository userRepository;

//private final AuthenticationManager authenticationManager;


    @GetMapping("/me")
    public MappingJacksonValue me(){
       var auth = SecurityContextHolder.getContext().getAuthentication();
       User user = (User) auth.getPrincipal();
User finalUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
        SimpleBeanPropertyFilter  filter= SimpleBeanPropertyFilter.filterOutAllExcept(USER_FILTER);
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter",filter);
        MappingJacksonValue response = new MappingJacksonValue(finalUser);
        response.setFilters(filterProvider);
        return response;
    }
    @PutMapping("/update")
    public MappingJacksonValue update(@RequestBody UserUpdateRequest request){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return new MappingJacksonValue("");
    }

}
