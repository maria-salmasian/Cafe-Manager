package com.org.cm.infrastructure.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(
        name = "cafe",
        indexes = {
                @Index(name = "idx_c_name_removed", columnList = "name, removed")
        }
)
public class Cafe extends DateAwareDomainEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CafeTable> tables;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "cafe_products",
            joinColumns = {
                    @JoinColumn(name = "cafe_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cp_cafe_id"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cp_product_id"))
            },
            indexes = {
                    @Index(name = "idx_cp_cafe_id", columnList = "cafe_id"),
                    @Index(name = "idx_cp_product_id", columnList = "product_id")
            }
    )
    private List<Product> products;


}
