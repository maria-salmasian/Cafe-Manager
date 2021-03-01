package com.org.cm.infrastructure.repository;

import com.org.cm.infrastructure.entity.Cafe;
import com.org.cm.infrastructure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

}