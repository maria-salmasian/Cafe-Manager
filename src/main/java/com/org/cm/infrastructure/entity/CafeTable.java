package com.org.cm.infrastructure.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "tables", schema = "cm")
public class CafeTable {

    @Id
    @Column(name = "table_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cafeTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @ManyToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<User> users;

    @ManyToOne
    private Cafe cafe;

    @Column(name = "isDeleted")
    private boolean isDeleted;
//table uni waiter
}
