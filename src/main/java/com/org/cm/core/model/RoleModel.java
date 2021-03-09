package com.org.cm.core.model;

import com.org.cm.infrastructure.entity.User;
import com.org.cm.infrastructure.utils.enumeration.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleModel {
    private Long id;
    private RoleName roleName;
    private User user;
}
