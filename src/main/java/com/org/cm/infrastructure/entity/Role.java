package com.org.cm.infrastructure.entity;

import com.org.cm.infrastructure.utils.enumeration.RoleName;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Data
@Table(
        name = "role",
        indexes = {
                @Index(name = "idx_r_roleName_removed", columnList = "roleName, removed" , unique = true)
        }
)
public class Role extends DateAwareDomainEntity {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "roleName")
    private RoleName roleName;

}
