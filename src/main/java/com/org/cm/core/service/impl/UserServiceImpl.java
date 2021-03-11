package com.org.cm.core.service.impl;

import com.org.cm.core.model.UserModel;
import com.org.cm.core.service.UserService;
import com.org.cm.core.service.exception.UserNotFoundException;
import com.org.cm.infrastructure.entity.Role;
import com.org.cm.infrastructure.entity.User;
import com.org.cm.infrastructure.repository.CafeRepository;
import com.org.cm.infrastructure.repository.RoleRepository;
import com.org.cm.infrastructure.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final
    UserRepository userRepository;

    private final
    CafeRepository cafeRepository;

    private final
    RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CafeRepository cafeRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.cafeRepository = cafeRepository;
        this.roleRepository = roleRepository;

    }

    @Override
    @Transactional(readOnly = true)
    public List<UserModel> getUsers() {
        log.info("method getUsers is called");
        List<UserModel> userModels = (userRepository.findAll())
                .stream()
                .map(x -> userToUserModel(x))
                .collect(Collectors.toList());
        Assert.notEmpty(userModels, "No user found");
        return userModels;

    }

    @Override
    @Transactional(readOnly = true)
    public UserModel getUserByID(long id) throws UserNotFoundException {
        log.info("method getUserById is called");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found for this id :: " + id));
        UserModel userModel = userToUserModel(user);
        Assert.notNull(userModel, "cafeModel null");
        return userModel;
    }

    @Override
    @Transactional
    public User saveUser(UserModel userModel) {
        log.info("method saveUser is called");
        Assert.notNull(userModel, "user not valid");
        User user = userModelToUser(userModel);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        return userRepository.save(user);

    }

    @Override
    @Transactional
    public User updateUserByID(long id, UserModel userModel) throws UserNotFoundException {
        log.info("method updateUserById is called");
        Assert.notNull(userModel, "userModel not valid");
        User userToBeUpdated = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("user not found for this id :: " + id));
        User user = userModelToUser(userModel);
        user.setCreated(userToBeUpdated.getCreated());
        user.setUpdated(LocalDateTime.now());
        userRepository.delete(userToBeUpdated);
        return userRepository.save(user);

    }

    @Override
    @Transactional
    public void deleteUserByID(long id) throws UserNotFoundException {
        log.info("method deleteUserById is called");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found for this id :: " + id));
        user.setRemoved(LocalDateTime.now());
        userRepository.save(user);


    }


    private UserModel userToUserModel (User user){
        UserModel userModel = new UserModel();
        userModel.setUsername(user.getUsername());
        userModel.setEnabled(user.isEnabled());
        userModel.setId(user.getId());
        userModel.setPassword(user.getPassword());
        userModel.setCafeId(user.getCafe().getId());

        List<Long> roleIds = new ArrayList<>();
        user.getRoles().forEach(x -> roleIds.add(x.getId()));
        userModel.setRoleIds(roleIds);

        return userModel;

    }

    private User userModelToUser (UserModel userModel){
        User user = new User();
        user.setCafe(cafeRepository.getCafeById(userModel.getCafeId()));
        user.setUsername(userModel.getUsername());
        user.setPassword(userModel.getPassword());
        user.setEnabled(userModel.isEnabled());

        List<Role> roles = new ArrayList<>();
        userModel.getRoleIds().forEach(y -> roles.add(roleRepository.getRoleById(y)));

        user.setRoles(roles);

        return user;

    }


}
