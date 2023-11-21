package com.example.craft.repositories;

import com.example.craft.entity.Customers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomersRepositoryTest {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setup() {
        Customers customers = new Customers(50L, "abc", "testCompany", "addressLine1", "addressLine2", "city", "state", 1234, "India", "legalAddress", "panNumber", "einNumber", "email", "website");
        testEntityManager.persist(customers);
    }

    @Test
    public void testFindByEmail(){
        Customers customers = customersRepository.findByEmail("email").get();
        assertEquals("email", customers.getEmail());
    }
}