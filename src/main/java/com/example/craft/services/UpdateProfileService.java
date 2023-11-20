package com.example.craft.services;

import com.example.craft.model.ResponseEntityBody;
import com.example.craft.model.dto.request.UpdateProfileRequest;
import org.springframework.stereotype.Service;

@Service
public interface UpdateProfileService {
    ResponseEntityBody updateProfile(UpdateProfileRequest updateProfileRequest);
}
