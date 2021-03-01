package com.org.cm.infrastructure.entity;


import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "orders", schema = "cm")
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isFinished")
    private boolean isFinished;

    @ManyToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<Product> products;

    @ManyToOne
    private CafeTable cafeTable;

    @Column(name = "isDeleted")
    private boolean isDeleted;



}
