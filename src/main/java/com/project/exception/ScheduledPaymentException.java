package com.project.exception;

public class ScheduledPaymentException extends RuntimeException{
    private final String message;

    public ScheduledPaymentException() {
        super("Card is not found!");
        message = "Card is not found!";
    }

    public String getMessage() {
        return message;
    }
}
