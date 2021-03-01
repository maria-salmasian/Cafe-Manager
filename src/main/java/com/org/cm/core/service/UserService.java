package com.org.cm.core.service;

import com.org.cm.core.model.UserModel;
import com.org.cm.infrastructure.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserModel> getUsers();
    UserModel getUserByID(int id) ;
    User saveUser(UserModel userModel) ;
    User updateUserByID(int id, UserModel userModel) ;
    void deleteUserByID(int id) ;
}
