package com.project.exception;


public class UserAlreadyExistsException extends RuntimeException{
    private final String message;

    public UserAlreadyExistsException() {
        super("User already exists");
        message = "User already exists";
    }

    public String getMessage() {
        return message;
    }
}
