package com.example.craft.model.dto.request;

import com.example.craft.model.BusinessAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CreateProfileRequest {
    private String legal_name;

    private String company_name;

    private BusinessAddress business_address;

    private String legal_address;

    private String pan_number;

    private String ein_number;

    private String email;

    private String website;

    private List<Long> subscribed_product_ids;
}
