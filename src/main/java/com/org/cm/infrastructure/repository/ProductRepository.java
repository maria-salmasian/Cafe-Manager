package com.org.cm.infrastructure.repository;

import com.org.cm.infrastructure.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(long id);
    Product getProductById(long id);
}
