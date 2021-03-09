package com.org.cm.infrastructure.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(
        name = "cafe_table",
        indexes = {
                @Index(name = "idx_c_table_id_removed", columnList = "table_id, removed" , unique = true)
        }
)
public class CafeTable extends DateAwareDomainEntity {

    @Id
    @Column(name = "table_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cafeTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TableOrder> orders;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "cafe_table_users",
            joinColumns = {
                    @JoinColumn(name = "cafe_table_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cu_cafe_table_id"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cu_user_id"))
            },
            indexes = {
                    @Index(name = "idx_cu_cafe_table_id", columnList = "cafe_table_id"),
                    @Index(name = "idx_cu_user_id", columnList = "user_id")
            }
    )
    private List<User> users;

    @ManyToOne
    private Cafe cafe;

}
