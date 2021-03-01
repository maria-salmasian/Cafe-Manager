package com.org.cm.infrastructure.repository;

import com.org.cm.infrastructure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
     User getUserByUsername(@Param("username") String username);
}
