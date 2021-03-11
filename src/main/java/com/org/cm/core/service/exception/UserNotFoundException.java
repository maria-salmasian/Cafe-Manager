package com.org.cm.core.service.exception;

public class UserNotFoundException extends NotFoundException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
