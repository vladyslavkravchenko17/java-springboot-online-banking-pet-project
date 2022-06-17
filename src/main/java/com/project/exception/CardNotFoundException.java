package com.project.exception;

public class CardNotFoundException extends RuntimeException{
    private final String message;

    public CardNotFoundException() {
        super("Card not found!");
        message = "Card not found!";
    }

    public String getMessage() {
        return message;
    }
}

