package com.org.cm.infrastructure.repository;

import com.org.cm.infrastructure.entity.CafeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CafeTableRepository extends JpaRepository<CafeTable, Long> {
}
