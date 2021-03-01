package com.org.cm.core.model;

import com.org.cm.infrastructure.entity.CafeTable;
import com.org.cm.infrastructure.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
    private Long id;
    private String productName;
    private CafeTable cafeTable;
    private Order order;
    private boolean isEnabled;
    private boolean isDeleted;
}
