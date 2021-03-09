package com.org.cm.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private List<RoleDTO> roles = new ArrayList<>();
    private CafeDTO cafe;



}
