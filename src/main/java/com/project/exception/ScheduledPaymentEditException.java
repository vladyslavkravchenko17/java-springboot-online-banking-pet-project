package com.project.exception;

public class ScheduledPaymentEditException extends RuntimeException{
    private final String message;
    private final long id;

    public ScheduledPaymentEditException(long id) {
        super("Card is not found!");
        message = "Card is not found!";
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
