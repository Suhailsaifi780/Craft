package com.example.craft.model;

import com.example.craft.entity.Customers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class BusinessAddress {
    private String address_line1;

    private String address_line2;

    private String city;

    private String state;

    private Integer zip;

    private String country;

    public BusinessAddress(Customers customers) {
        this.address_line1 = customers.getAddressLine1();
        this.address_line2 = customers.getAddressLine2();
        this.city = customers.getCity();
        this.state = customers.getState();
        this.country = customers.getCountry();
        this.zip = customers.getZip();
    }
}
