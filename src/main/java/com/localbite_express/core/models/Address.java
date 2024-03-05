package com.localbite_express.core.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



public class Address {
    private String houseNo;
    private String street;
    private String city;
    private int pincode;
    private String state;
}
