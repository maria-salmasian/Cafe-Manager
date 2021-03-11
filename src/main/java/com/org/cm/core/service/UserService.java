package com.org.cm.core.service;

import com.org.cm.core.model.UserModel;
import com.org.cm.core.service.exception.UserNotFoundException;
import com.org.cm.infrastructure.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserModel> getUsers();
    UserModel getUserByID(long id) throws UserNotFoundException;
    User saveUser(UserModel userModel) ;
    User updateUserByID(long id, UserModel userModel) throws UserNotFoundException;
    void deleteUserByID(long id) throws UserNotFoundException;
}
