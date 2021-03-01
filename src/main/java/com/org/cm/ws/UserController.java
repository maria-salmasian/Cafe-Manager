package com.org.cm.ws;

import com.org.cm.core.model.UserModel;
import com.org.cm.core.service.UserService;
import com.org.cm.infrastructure.entity.Role;
import com.org.cm.infrastructure.entity.User;
import com.org.cm.infrastructure.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<User> createManager(@RequestBody User user) {
        User user1 = new User();
        user1.setUsername("Nareh");
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setRoles(Arrays.asList(roleRepository.findByRoleName("ROLE_MANAGER")));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
