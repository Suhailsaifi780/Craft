package com.example.craft.services;

import com.example.craft.entity.Customers;
import com.example.craft.entity.Products;
import com.example.craft.entity.SubscribedProducts;
import com.example.craft.model.ResponseEntityBody;
import com.example.craft.model.dto.request.CreateProfileRequest;
import com.example.craft.repositories.CustomersRepository;
import com.example.craft.repositories.SubscribedProductsRepository;
import com.example.craft.utils.ResponseEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CreateCustomerProfileServiceImpl implements CreateCustomerProfileService{

    @Autowired
    CustomersRepository customersRepository;

    @Autowired
    SubscribedProductsRepository subscribedProductsRepository;

    @Override
    public ResponseEntityBody createProfile(CreateProfileRequest createProfileRequest) {
        log.info("Inside createProfile for data : {}", createProfileRequest);
        ResponseEntityBody responseEntityBody = new ResponseEntityBody();

        try {
            if (createProfileRequest.getEmail() != null) {
                Optional<Customers> customersOptional = customersRepository.findByEmail(createProfileRequest.getEmail());

                if (customersOptional.isPresent()) {
                    log.info("User with this email already present.");

                    responseEntityBody = ResponseEntityUtil.createResponseEntityBody("User with this email already present", HttpStatus.OK, "/craft/create/profile", null, null);
                } else {
                    Customers customer = new Customers(createProfileRequest);

                    List<Long> subscribedProductsDtoList = createProfileRequest.getSubscribed_product_ids();
                    log.info("Subscribed Product Ids : {}", subscribedProductsDtoList);
                    if (subscribedProductsDtoList != null && !subscribedProductsDtoList.isEmpty()) {
                        List<SubscribedProducts>  subscribedProductsList = new ArrayList<>();
                        for (Long productId : subscribedProductsDtoList) {
                            log.info("Product id : {}", productId);

                            SubscribedProducts subscribedProducts = new SubscribedProducts();
                            Products product = new Products(productId);

                            subscribedProducts.setProduct(product);
                            subscribedProducts.setCustomer(customer);

                            subscribedProductsList.add(subscribedProducts);
                        }

                        customer.setSubscribedProducts(subscribedProductsList);
                    }

                    log.info("Saving customer profile");
                    customersRepository.save(customer);

                    responseEntityBody = ResponseEntityUtil.createResponseEntityBody("Successfully Created Profile", HttpStatus.CREATED, "/craft/create/profile", null, null);
                }

            }
        } catch (Exception e) {
            log.error("Exception occurred while reading customer data and error message is : {}", e.getMessage());
            return ResponseEntityUtil.createResponseEntityBody(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "/craft/create/profile", null, null);
        }

        log.info("Response after saving profile : {}", responseEntityBody);
        return responseEntityBody;
    }
}
