package com.example.craft.repositories;

import com.example.craft.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long> {
    Optional<Customers> findByEmail(String email);
}
