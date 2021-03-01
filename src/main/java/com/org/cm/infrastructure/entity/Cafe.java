package com.org.cm.infrastructure.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "cafes", schema = "cm")
public class Cafe {
    @Id
    @Column(name = "cafe_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cafeName")
    private String name;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CafeTable> tables;

    @ManyToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<Product> products;

    @Column(name = "isDeleted")
    private boolean isDeleted;


}
