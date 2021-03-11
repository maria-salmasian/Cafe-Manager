package com.org.cm.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CafeTableDTO {
    private Long id;
    private List<Long> orderIds;
    private List<Long> userIds;
    private long cafeId;

}
