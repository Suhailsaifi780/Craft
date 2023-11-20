package com.example.craft.services;

import com.example.craft.model.ResponseEntityBody;
import com.example.craft.repositories.CustomersRepository;
import com.example.craft.utils.ResponseEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeleteProfileServiceImpl implements DeleteProfileService{
    @Autowired
    CustomersRepository customersRepository;

    @Override
    @CacheEvict(cacheNames = "customerProfile", key = "#customerId")
    public ResponseEntityBody deleteProfile(Long customerId) {
        log.info("Inside deleteProfile for customerId : {}", customerId);
        ResponseEntityBody responseEntityBody = new ResponseEntityBody();

        try {
            customersRepository.deleteById(customerId);

            responseEntityBody = ResponseEntityUtil.createResponseEntityBody("Successfully Deleted Profile for CustomerId : " + customerId.toString(), HttpStatus.OK, "/craft/delete/profile", null, null);
        } catch (Exception e) {
            log.error("For customerId : {}, exception occurred while deleting customer profile and error message is : {}", customerId, e.getMessage());
            responseEntityBody = ResponseEntityUtil.createResponseEntityBody(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "/craft/create/profile", null, null);
        }

        log.info("Response form delete process : {}", responseEntityBody);
        return responseEntityBody;
    }
}
