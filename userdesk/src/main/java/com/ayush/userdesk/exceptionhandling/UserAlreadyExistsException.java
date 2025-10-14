package com.ayush.userdesk.exceptionhandling;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException() {
        super("user already exists");
    }
}
