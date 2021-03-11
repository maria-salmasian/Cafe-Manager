package com.org.cm.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private List<Long> roleIds;
    private long cafeId;



}
