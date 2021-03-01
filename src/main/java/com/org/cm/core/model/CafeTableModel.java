package com.org.cm.core.model;

import com.org.cm.infrastructure.entity.Cafe;
import com.org.cm.infrastructure.entity.Order;
import com.org.cm.infrastructure.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CafeTableModel {
    private Long id;
    private List<Order> orders;
    private List<User> users;
    private Cafe cafe;
    private boolean isDeleted;
}
