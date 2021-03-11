package com.org.cm.ws.controller;


import com.org.cm.core.model.CafeModel;
import com.org.cm.core.model.UserModel;
import com.org.cm.core.service.UserService;
import com.org.cm.core.service.exception.CafeNotFoundException;
import com.org.cm.core.service.exception.NotFoundException;
import com.org.cm.core.service.exception.UserNotFoundException;
import com.org.cm.core.service.exception.ValidationException;
import com.org.cm.ws.dto.CafeDTO;
import com.org.cm.ws.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/user")
public class MyUserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public MyUserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") long id) throws UserNotFoundException {
        log.info("method getUserById in Controller is invoked");
        UserModel userModel = userService.getUserByID(id);
        UserDTO userDTO = modelMapper.map(userModel, UserDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("method getAllUsers in Controller is invoked");
        List<UserModel> userModels = userService.getUsers();
        List<UserDTO> userDTOS = userModels.stream().map(x -> modelMapper.map(x, UserDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserDTO> createUser( @RequestBody UserDTO userDTO) {
        log.info("method createUser in Controller is invoked");

        UserModel userModel = modelMapper.map(userDTO, UserModel.class);
        userService.saveUser(userModel);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") long id,
                                              @RequestBody UserDTO userDTO) throws UserNotFoundException {
        log.info("method updateUser in Controller is invoked");

        UserModel userModel = modelMapper.map(userDTO, UserModel.class);
        userService.updateUserByID(id, userModel);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) throws UserNotFoundException {
        log.info("method deleteUser in Controller is invoked");
        userService.deleteUserByID(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
