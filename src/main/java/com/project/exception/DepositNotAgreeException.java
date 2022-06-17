package com.project.exception;

public class DepositNotAgreeException extends RuntimeException{
    private final String message;

    public DepositNotAgreeException() {
        super("You should agree with deposit rules!");
        message = "You should agree with deposit rules!";
    }

    public String getMessage() {
        return message;
    }
}
