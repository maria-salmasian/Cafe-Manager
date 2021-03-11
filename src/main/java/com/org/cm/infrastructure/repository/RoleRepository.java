package com.org.cm.infrastructure.repository;

import com.org.cm.infrastructure.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
    Role getRoleById(long id);
}
