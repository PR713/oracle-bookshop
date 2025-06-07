package org.example.bookshop.repository;

import org.example.bookshop.entity.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE) //blocking
    @Query("SELECT p FROM Product p WHERE p.productId = :id") //HQL
    Optional<Product> findProductForUpdate(@Param("id") Long id);
}
