package com.localbite_express.core.services;

import com.localbite_express.core.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private static List<User> store = new ArrayList<>();


    public User getUser(String id){
        return (User)store.get(0);
    }

}
