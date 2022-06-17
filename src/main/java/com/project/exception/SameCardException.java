package com.project.exception;

public class SameCardException extends RuntimeException{
    private final String message;

    public SameCardException() {
        super("You cannot transfer money to card you pay!");
        message = "You cannot transfer money to card you pay!";
    }

    public String getMessage() {
        return message;
    }
}
