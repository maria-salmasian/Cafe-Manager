//package com.org.cm.ws.converter;
//
//import com.org.cm.infrastructure.utils.enumeration.RoleName;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class IdToRoleConverter implements Converter<String, RoleName> {
//
//    @Override
//    public RoleName convert(String source) {
//        return RoleName.getById(Integer.parseInt(source));
//    }
//}