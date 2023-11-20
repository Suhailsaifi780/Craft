package com.example.craft.model.dto.response;

import com.example.craft.entity.Customers;
import com.example.craft.model.BusinessAddress;
import lombok.Data;

@Data
public class CustomerProfileResponse {

    private Long customer_id;

    private String legal_name;

    private String company_name;

    private BusinessAddress business_address;

    private String legal_address;

    private String pan_number;

    private String ein_number;

    private String email;

    private String website;

    public CustomerProfileResponse(Customers customers) {
        this.customer_id = customers.getId();
        this.legal_name = customers.getLegalName();
        this.company_name = customers.getCompanyName();
        this.business_address = new BusinessAddress(customers);
        this.legal_address = customers.getLegalAddress();
        this.pan_number = customers.getPanNumber();
        this.ein_number = customers.getEinNumber();
        this.email = customers.getEmail();
        this.website = customers.getWebsite();
    }
}
