package com.org.cm.core.model;

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
    private List<Long> products;
    private long cafeTableId;

}
