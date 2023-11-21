package com.example.craft.services;

import com.example.craft.constants.GeneralConstants;
import com.example.craft.entity.Customers;
import com.example.craft.model.ResponseEntityBody;
import com.example.craft.model.dto.request.UpdateProfileRequest;
import com.example.craft.model.dto.response.ValidationResponse;
import com.example.craft.repositories.CustomersRepository;
import com.example.craft.repositories.SubscribedProductsRepository;
import com.example.craft.utils.CommonUtil;
import com.example.craft.utils.ResponseEntityUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class UpdateProfileServiceImpl implements UpdateProfileService {
    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    SubscribedProductsRepository subscribedProductsRepository;

    @Bean
    private RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Override
    public ResponseEntityBody updateProfile(UpdateProfileRequest updateProfileRequest) {
        log.info("Inside updateProfile for customerId : {}", updateProfileRequest.getCustomer_id());
        ResponseEntityBody responseEntityBody = new ResponseEntityBody();

        try {
            log.info("For customerId : {}, updating profile data is : {}", updateProfileRequest.getCustomer_id(), updateProfileRequest);

            Optional<Customers> customersOptional = customersRepository.findById(updateProfileRequest.getCustomer_id());

            if (customersOptional.isPresent()) {
                Customers customers = customersOptional.get();

                List<String> productValidationUrls = subscribedProductsRepository.findAllProductsSubscribedByUser(updateProfileRequest.getCustomer_id());

                log.info("For customerId : {}, all subscribed products are : {}", updateProfileRequest.getCustomer_id(), productValidationUrls.size());

                String validationMessage = validateAcrossAllSubscribedProducts(productValidationUrls, updateProfileRequest);
                if (validationMessage.equalsIgnoreCase(GeneralConstants.VALIDATED)) {
                    log.info("For customerId : {}, proceeding to update profile.", updateProfileRequest.getCustomer_id());

                    responseEntityBody = updateProfileAfterValidation(updateProfileRequest, customers);

                } else {
                    log.info("For customerId : {}, can't update as it is not validated by other products and message is : {}", updateProfileRequest.getCustomer_id(), validationMessage);
                    responseEntityBody = ResponseEntityUtil.createResponseEntityBody(validationMessage, HttpStatus.CONFLICT, "/craft/update/profile", null, null);
                }
            } else {
                responseEntityBody = ResponseEntityUtil.createResponseEntityBody("Customer not present with this id", HttpStatus.OK, "/craft/update/profile", null, null);
            }
        } catch (Exception e) {
            log.error("For customerId : {}, exception occurred while updating customer profile and error message is : {}", updateProfileRequest.getCustomer_id(), e.getMessage());
            responseEntityBody = ResponseEntityUtil.createResponseEntityBody(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "/craft/update/profile", null, null);
        }

        log.info("For customerId : {}, profile update response is : {}", updateProfileRequest.getCustomer_id(), responseEntityBody);
        return responseEntityBody;
    }

    private ResponseEntityBody updateProfileAfterValidation(UpdateProfileRequest updateProfileRequest, Customers customer) {
        log.info("Inside updateProfileAfterValidation for customerId : {}", updateProfileRequest.getCustomer_id());
        ResponseEntityBody responseEntityBody = new ResponseEntityBody();

        try {
                if (setUpdatedDataToCustomer(customer, updateProfileRequest)) {
                    log.info("For customerId : {}, saving updated data", updateProfileRequest.getCustomer_id());
                    customersRepository.save(customer);

                    responseEntityBody = ResponseEntityUtil.createResponseEntityBody("Successfully updated profile", HttpStatus.OK, "/craft/update/profile", null, null);
                } else {
                    responseEntityBody = ResponseEntityUtil.createResponseEntityBody("No updated data is present.", HttpStatus.OK, "/craft/update/profile", null, null);
                }

        } catch (Exception e) {
            log.error("For customerId : {}, exception occurred while updating profile and error message is : {}", updateProfileRequest.getCustomer_id(), e.getMessage());
            responseEntityBody = ResponseEntityUtil.createResponseEntityBody(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "/craft/update/profile", null, null);
        }

        return responseEntityBody;
    }

    private boolean setUpdatedDataToCustomer(Customers customer, UpdateProfileRequest updateProfileRequest) {
        log.info("Inside setUpdatedDataToCustomer for customerId : {}", customer.getId());
        boolean updateStatus = false;

        if (CommonUtil.checkNotNullNotEmptyAndNotSameString(customer.getLegalName(), updateProfileRequest.getLegal_name())){
            updateStatus = true;
            customer.setLegalName(updateProfileRequest.getLegal_name());
        }

        if (CommonUtil.checkNotNullNotEmptyAndNotSameString(customer.getAddressLine1(), updateProfileRequest.getBusiness_address().getAddress_line1())){
            updateStatus = true;
            customer.setAddressLine1(updateProfileRequest.getBusiness_address().getAddress_line1());
        }

        if (CommonUtil.checkNotNullNotEmptyAndNotSameString(customer.getAddressLine2(), updateProfileRequest.getBusiness_address().getAddress_line2())){
            updateStatus = true;
            customer.setAddressLine2(updateProfileRequest.getBusiness_address().getAddress_line2());
        }

        if (CommonUtil.checkNotNullNotEmptyAndNotSameString(customer.getCity(), updateProfileRequest.getBusiness_address().getCity())){
            updateStatus = true;
            customer.setCity(updateProfileRequest.getBusiness_address().getCity());
        }

        if (CommonUtil.checkNotNullNotEmptyAndNotSameString(customer.getState(), updateProfileRequest.getBusiness_address().getState())){
            updateStatus = true;
            customer.setState(updateProfileRequest.getBusiness_address().getState());
        }

        if (CommonUtil.checkNotNullNotEmptyAndNotSameString(customer.getCountry(), updateProfileRequest.getBusiness_address().getCountry())){
            updateStatus = true;
            customer.setCountry(updateProfileRequest.getBusiness_address().getCountry());
        }

        if (CommonUtil.checkNotNullNotEmptyAndNotSameInteger(customer.getZip(), updateProfileRequest.getBusiness_address().getZip())){
            updateStatus = true;
            customer.setZip(updateProfileRequest.getBusiness_address().getZip());
        }

        if (CommonUtil.checkNotNullNotEmptyAndNotSameString(customer.getLegalAddress(), updateProfileRequest.getLegal_address())){
            updateStatus = true;
            customer.setLegalAddress(updateProfileRequest.getLegal_address());
        }

        if (CommonUtil.checkNotNullNotEmptyAndNotSameString(customer.getPanNumber(), updateProfileRequest.getPan_number())){
            updateStatus = true;
            customer.setPanNumber(updateProfileRequest.getPan_number());
        }

        if (CommonUtil.checkNotNullNotEmptyAndNotSameString(customer.getEinNumber(), updateProfileRequest.getEin_number())){
            updateStatus = true;
            customer.setEinNumber(updateProfileRequest.getEin_number());
        }

        if (CommonUtil.checkNotNullNotEmptyAndNotSameString(customer.getEmail(), updateProfileRequest.getEmail())){
            updateStatus = true;
            customer.setEmail(updateProfileRequest.getEmail());
        }

        if (CommonUtil.checkNotNullNotEmptyAndNotSameString(customer.getWebsite(), updateProfileRequest.getWebsite())){
            updateStatus = true;
            customer.setWebsite(updateProfileRequest.getWebsite());
        }

        return updateStatus;

    }

    @CircuitBreaker(name = GeneralConstants.VALIDATE_DATA_ACROSS_ALL_PRODUCTS, fallbackMethod = "handleValidationHttpCalls")
    private String validateAcrossAllSubscribedProducts(List<String> productValidationUrls, UpdateProfileRequest updateProfileRequest) {
        log.info("Inside validateAcrossAllSubscribedProducts for customerId : {}, with subscribed products urls : {}", updateProfileRequest.getCustomer_id(), productValidationUrls);

        AtomicReference<String> validationMessage = new AtomicReference<>("");

        if (productValidationUrls != null && !productValidationUrls.isEmpty()) {
            List<CompletableFuture<Void>> futures = productValidationUrls.stream()
                    .map(url -> CompletableFuture.supplyAsync(() -> restTemplate().exchange(url, HttpMethod.GET, new HttpEntity<>(updateProfileRequest), String.class))
                            .thenAccept(x -> {

                                log.info("Response from url : {}, is : {}", url, x);
                                try {
                                    ObjectMapper objectMapper = new ObjectMapper();

                                    ValidationResponse validationResponse = objectMapper.readValue(x.getBody(), ValidationResponse.class);
                                    log.info("Response body after parsing from url : {}, is : {}", url, validationResponse);

                                    if (validationResponse != null
                                            && validationResponse.getMessage() != null
                                            && validationResponse.getMessage().equalsIgnoreCase(GeneralConstants.VALIDATED)) {
                                        validationMessage.set(GeneralConstants.VALIDATED);
                                    } else {
                                        validationMessage.set(GeneralConstants.NOT_VALIDATED);
                                    }
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                            })).toList();

            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

            allOf.join();
        }

        return validationMessage.get();
    }

    public String handleValidationHttpCallsFallBack(Exception e) {
        log.info("Inside handleValidationHttpCallsFallBack for error : {}", e.getMessage());

        return GeneralConstants.NOT_VALIDATED;
    }
}
