package com.example.craft.controllers;

import com.example.craft.entity.Customers;
import com.example.craft.model.ResponseEntityBody;
import com.example.craft.repositories.CustomersRepository;
import com.example.craft.repositories.ProductsRepository;
import com.example.craft.repositories.SubscribedProductsRepository;
import com.example.craft.services.CreateCustomerProfileService;
import com.example.craft.services.DeleteProfileService;
import com.example.craft.services.ReadProfileService;
import com.example.craft.services.UpdateProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CraftController.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
class CraftControllerTest {

     @Autowired
     private MockMvc mockMvc;

     @MockBean
     private ReadProfileService readProfileService;

     @MockBean
     UpdateProfileService updateProfileService;

     @MockBean
     CreateCustomerProfileService createCustomerProfileService;

     @MockBean
     DeleteProfileService deleteProfileService;

     @MockBean
     CustomersRepository customersRepository;

     @MockBean
     ProductsRepository productsRepository;

     @MockBean
     SubscribedProductsRepository subscribedProductsRepository;

//     private ResponseEntityBody responseEntityBody;

     @BeforeEach
     void status() {
          Optional<Customers> customers = Optional.of(new Customers(1L, "abc", "testCompany", "addressLine1", "addressLine2", "city", "state", 1234, "India", "legalAddress", "panNumber", "einNumber", "email", "website"));
          Mockito.when(customersRepository.findById(1L)).thenReturn(customers);

          ResponseEntityBody responseEntityBody = new ResponseEntityBody(HttpStatus.OK, "success", "/craft/get/profile", HttpStatus.OK.value(), null);
          Mockito.when(readProfileService.getCustomerProfile(1L)).thenReturn(responseEntityBody);

     }

     @Test
     public void testReadCustomerProfile() throws Exception {

         mockMvc.perform(MockMvcRequestBuilders.get("/craft/get/profile")
                 .contentType(MediaType.ALL).requestAttr("customerId", 1L))
                 .andExpect(MockMvcResultMatchers.status().isOk());
     }

//     @Test
//     public void testCreateCustomerProfile() throws Exception {
//          Mockito.when(readProfileService.getCustomerProfile(1L)).thenReturn(responseEntityBody);
//
//          mockMvc.perform(MockMvcRequestBuilders.put("/craft/get/profile")
//                          .contentType(MediaType.ALL))
//                  .andExpect(MockMvcResultMatchers.status().isOk());
//     }

}