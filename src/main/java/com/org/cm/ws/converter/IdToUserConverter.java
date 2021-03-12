//package com.org.cm.ws.converter;
//
//import com.org.cm.infrastructure.entity.User;
//import com.org.cm.infrastructure.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.core.convert.converter.Converter;
//
//@Component
//public class IdToUserConverter implements Converter<Long, User> {
//    @Autowired
//    UserRepository userRepository;
//    @Override
//    public User convert(Long source) {
//        return userRepository.getUserById((source));
//    }
//}