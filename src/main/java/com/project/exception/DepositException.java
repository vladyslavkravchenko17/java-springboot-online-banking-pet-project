package com.project.exception;

public class DepositException extends RuntimeException{
    private final String message;

    public DepositException() {
        super("Not enough money!");
        message = "Not enough money!";
    }

    public String getMessage() {
        return message;
    }
}
