package com.org.cm.infrastructure.repository;


import com.org.cm.infrastructure.entity.TableOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<TableOrder, Long> {

}
