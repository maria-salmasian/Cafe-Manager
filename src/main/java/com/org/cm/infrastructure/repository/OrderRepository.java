package com.org.cm.infrastructure.repository;

import com.org.cm.infrastructure.entity.Order;
import com.org.cm.infrastructure.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
