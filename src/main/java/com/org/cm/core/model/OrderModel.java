package com.org.cm.core.model;

import com.org.cm.infrastructure.entity.CafeTable;
import com.org.cm.infrastructure.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {
    private Long id;
    private boolean isFinished;
    private List<Product> products;
    private CafeTable cafeTable;
    private boolean isDeleted;
}
