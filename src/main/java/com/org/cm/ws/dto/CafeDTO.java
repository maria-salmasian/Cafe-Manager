package com.org.cm.ws.dto;

import com.org.cm.infrastructure.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CafeDTO {
    private Long id;
    private String name;
    private List<User> users;
    private List<CafeTableDTO> tables;
    private List<Long> productIds;


}
