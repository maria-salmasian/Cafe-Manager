package com.org.cm.infrastructure.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "products", schema = "cm")
public class Product {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String productName;

    @Column(name = "enabled")
    private boolean enabled;



    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<Order> order;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<Cafe> cafe;

    @Column(name = "isEnabled")
    private boolean isEnabled;

    @Column(name = "isDeleted")
    private boolean isDeleted;

}


//userservice create user
