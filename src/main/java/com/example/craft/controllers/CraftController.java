package com.example.craft.controllers;

import com.example.craft.model.ResponseEntityBody;
import com.example.craft.model.dto.request.CreateProfileRequest;
import com.example.craft.model.dto.request.UpdateProfileRequest;
import com.example.craft.services.CreateCustomerProfileService;
import com.example.craft.services.DeleteProfileService;
import com.example.craft.services.ReadProfileService;
import com.example.craft.services.UpdateProfileService;
import com.example.craft.utils.ResponseEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/craft")
public class CraftController {

    @Autowired
    ReadProfileService readProfileService;

    @Autowired
    UpdateProfileService updateProfileService;

    @Autowired
    CreateCustomerProfileService createCustomerProfileService;

    @Autowired
    DeleteProfileService deleteProfileService;

    @PutMapping(value = "/create/profile",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCustomerProfile(@RequestBody CreateProfileRequest createProfileRequest) {
        log.info("Starting customer profile creation process for data : {}", createProfileRequest);

        ResponseEntityBody responseEntityBody = createCustomerProfileService.createProfile(createProfileRequest);

        return ResponseEntityUtil.createResponseEntity(responseEntityBody);
    }

    @GetMapping(value = "/get/profile",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readCustomerProfile(@RequestParam("customerId") Long customerId) {
        log.info("Reading customer data for customerId : {}", customerId);

        ResponseEntityBody responseEntityBody = readProfileService.getCustomerProfile(customerId);

        return ResponseEntityUtil.createResponseEntity(responseEntityBody);
    }

    @DeleteMapping(value = "/delete/profile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCustomerProfile(@RequestParam("customerId") Long customerId) {
        log.info("Starting customer profile delete process for customer id : {}", customerId);

        ResponseEntityBody responseEntityBody = deleteProfileService.deleteProfile(customerId);

        return ResponseEntityUtil.createResponseEntity(responseEntityBody);
    }

    @PostMapping(value = "/update/profile",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCustomerProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {
        log.info("Starting customer profile update process for customer id : {}", updateProfileRequest.getCustomer_id());

        ResponseEntityBody responseEntityBody = updateProfileService.updateProfile(updateProfileRequest);

        return ResponseEntityUtil.createResponseEntity(responseEntityBody);
    }


    @GetMapping("/test")
    public String testService () {
        return "test Workinggggg";
    }
}
