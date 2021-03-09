package com.org.cm.core.model;

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
    private List<UserModel> users;
    private List<CafeTableModel> tables;
    private List<Long> productIds;


}
