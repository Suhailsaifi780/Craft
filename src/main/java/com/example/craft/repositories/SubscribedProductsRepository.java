package com.example.craft.repositories;

import com.example.craft.entity.SubscribedProducts;
import com.example.craft.model.SubscribedProductsByUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribedProductsRepository extends JpaRepository<SubscribedProducts, Long> {

    @Query(value = "SELECT p.product_validation_url " +
            "FROM products p " +
            "JOIN subscribed_products sp ON p.id = sp.product_id " +
            "WHERE sp.customer_id =:customerId", nativeQuery = true)
    List<String> findAllProductsSubscribedByUser(Long customerId);
}
