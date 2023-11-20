package com.example.craft.services;

import com.example.craft.model.ResponseEntityBody;
import com.example.craft.model.dto.request.CreateProfileRequest;
import org.springframework.stereotype.Service;

@Service
public interface CreateCustomerProfileService {
    ResponseEntityBody createProfile(CreateProfileRequest createProfileRequest);
}
