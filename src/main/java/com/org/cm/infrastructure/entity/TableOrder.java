package com.org.cm.infrastructure.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@Table(
        name = "table_order",
        indexes = {
                @Index(name = "idx_t_table_order_id_removed", columnList = "table_order_id, removed" , unique = true)
        }


)
public class TableOrder extends DateAwareDomainEntity {
    @Id
    @Column(name = "table_order_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_finished")
    private boolean isFinished;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ordered_products",
            joinColumns = {
                    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "fk_op_order_id"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_op_product_id"))
            },
            indexes = {
                    @Index(name = "idx_op_order_id", columnList = "order_id"),
                    @Index(name = "idx_op_product_id", columnList = "product_id")
            }
    )
    private List<Product> products;

    @ManyToOne
    private CafeTable cafeTable;




}
