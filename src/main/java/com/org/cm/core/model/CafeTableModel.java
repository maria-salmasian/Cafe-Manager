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
    private List<Long> orderIds;
    private List<Long> userIds;
    private long cafeId;

}
