package com.example.craft.services;

import com.example.craft.model.ResponseEntityBody;
import org.springframework.stereotype.Service;

//@Service
public interface ReadProfileService {
    ResponseEntityBody getCustomerProfile(Long customerId);
}
