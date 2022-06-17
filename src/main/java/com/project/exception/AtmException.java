package com.project.exception;

public class AtmException extends RuntimeException{
    private final String message;

    public AtmException() {
        super("Not enough money!");
        message = "Not enough money!";
    }

    public String getMessage() {
        return message;
    }
}