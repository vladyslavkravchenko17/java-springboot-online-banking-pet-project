package com.project.exception;

public class SubscriptionException extends RuntimeException{
    private final String message;

    public SubscriptionException() {
        super("Not enough money!");
        message = "Not enough money!";
    }

    public String getMessage() {
        return message;
    }
}
