package com.project.exception;

public class ActivationCodeNotFoundException extends RuntimeException{
    private final String message;

    public ActivationCodeNotFoundException() {
        super("Activation code is not found!");
        message = "Activation code is not found!";
    }

    public String getMessage() {
        return message;
    }
}
