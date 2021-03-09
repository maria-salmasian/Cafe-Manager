package com.org.cm.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CafeTableModel {
    private Long id;
    private List<OrderModel> orders;
    private List<UserModel> users;
    private long cafeId;

}
