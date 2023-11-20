package com.example.craft.services;

import com.example.craft.entity.Customers;
import com.example.craft.model.ResponseEntityBody;
import com.example.craft.model.dto.response.CustomerProfileResponse;
import com.example.craft.repositories.CustomersRepository;
import com.example.craft.utils.ResponseEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ReadProfileServiceImpl implements  ReadProfileService{

    @Autowired
    CustomersRepository customersRepository;

    @Override
    @Cacheable(cacheNames = "customerProfile", key = "#customerId")
    public ResponseEntityBody getCustomerProfile(Long customerId) {
        log.info("Inside getCustomerProfile for customerId : {}", customerId);
        ResponseEntityBody responseEntityBody = new ResponseEntityBody();

        try {
            Optional<Customers> customersOptional = customersRepository.findById(customerId);

            if (customersOptional.isPresent()) {
                log.info("Customer is present with customer Id : {}", customerId);
                CustomerProfileResponse customerProfileResponse = new CustomerProfileResponse(customersOptional.get());

                responseEntityBody = ResponseEntityUtil.createResponseEntityBody("Success", HttpStatus.OK, "/craft/get/profile", null, customerProfileResponse);
            } else {
                log.info("No customer is present with customerId : {}", customerId);
                responseEntityBody =  ResponseEntityUtil.createResponseEntityBody("No record found", HttpStatus.CONFLICT, "/craft/get/profile", null, null);
            }
        } catch (Exception e) {
            log.error("For customerId : {}, exception occurred while reading customer data and error message is : {}", customerId, e.getMessage());
            responseEntityBody = ResponseEntityUtil.createResponseEntityBody(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "/craft/get/profile", null, null);
        }

        log.info("For customerId : {}, response is : {}", customerId, responseEntityBody);
        return responseEntityBody;
    }
}
