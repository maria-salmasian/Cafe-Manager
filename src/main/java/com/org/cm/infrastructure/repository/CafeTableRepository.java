package com.org.cm.infrastructure.repository;

import com.org.cm.infrastructure.entity.CafeTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeTableRepository extends JpaRepository<CafeTable, Long> {
     List<CafeTable> findAllByCafe(long cafeId);
     CafeTable findCafeTableById(long id);
     CafeTable getCafeTableById(long id);
}

