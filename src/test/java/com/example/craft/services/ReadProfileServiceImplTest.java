package com.example.craft.services;

import com.example.craft.entity.Customers;
import com.example.craft.model.ResponseEntityBody;
import com.example.craft.repositories.CustomersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReadProfileServiceImplTest {

    @Autowired
    private ReadProfileService readProfileService;

    @MockBean
    private CustomersRepository customersRepository;

    @BeforeEach
    void status() {
        Optional<Customers> customers = Optional.of(new Customers(1L, "abc", "testCompany", "addressLine1", "addressLine2", "city", "state", 1234, "India", "legalAddress", "panNumber", "einNumber", "email", "website"));
        Mockito.when(customersRepository.findById(1L)).thenReturn(customers);

//        ResponseEntityBody responseEntityBody = new ResponseEntityBody(HttpStatus.OK, "success", "/craft/get/profile", HttpStatus.OK.value(), null);
//        Mockito.when(customersRepository.findById(1L)).thenReturn(customers);
    }

    @Test
    public void testGetCustomerProfile(){
        ResponseEntityBody responseEntityBody = readProfileService.getCustomerProfile(1L);
        assertEquals(HttpStatus.OK, responseEntityBody.getStatus());
    }

}