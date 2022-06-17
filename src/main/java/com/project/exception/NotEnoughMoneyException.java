package com.project.exception;

public class NotEnoughMoneyException extends RuntimeException{
    private final String message;

    public NotEnoughMoneyException() {
        super("Not enough money!");
        message = "Not enough money!";
    }

    public String getMessage() {
        return message;
    }
}
