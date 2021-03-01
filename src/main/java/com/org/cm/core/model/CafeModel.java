package com.org.cm.core.model;

import com.org.cm.infrastructure.entity.CafeTable;
import com.org.cm.infrastructure.entity.Product;
import com.org.cm.infrastructure.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CafeModel {
    private Long id;
    private String name;
    private List<User> users;
    private List<CafeTable> tables;
    private List<Product> products;
    private boolean isDeleted;
}
