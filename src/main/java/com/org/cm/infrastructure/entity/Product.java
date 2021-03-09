package com.org.cm.infrastructure.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Data
@Table(
        name = "product",
        indexes = {
                @Index(name = "idx_p_name_removed", columnList = "name, removed" , unique = true)
        }
)
public class Product extends DateAwareDomainEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String productName;

    @Column(name = "isEnabled")
    private boolean isEnabled;


}


