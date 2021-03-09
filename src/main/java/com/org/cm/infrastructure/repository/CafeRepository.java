package com.org.cm.infrastructure.repository;

import com.org.cm.infrastructure.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    Cafe findCafeById(long id);

}
