package com.example.craft.services;

import com.example.craft.model.ResponseEntityBody;

public interface DeleteProfileService {
    ResponseEntityBody deleteProfile(Long customerId);
}
