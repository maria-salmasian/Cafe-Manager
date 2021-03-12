//package com.org.cm.ws.converter;
//
//
//import com.org.cm.infrastructure.entity.CafeTable;
//import com.org.cm.infrastructure.entity.User;
//import com.org.cm.infrastructure.repository.CafeTableRepository;
//import com.org.cm.infrastructure.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class IdToTableConverter implements Converter<Long, CafeTable> {
//    @Autowired
//    CafeTableRepository cafeTableRepository;
//    @Override
//    public CafeTable convert(Long source) {
//        return cafeTableRepository.findCafeTableById((source));
//    }
//}