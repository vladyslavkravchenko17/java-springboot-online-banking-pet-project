package com.project.exception;

public class CreditNotAgreeException extends RuntimeException{
    private final String message;

    public CreditNotAgreeException() {
        super("You should agree with credit rules!");
        message = "You should agree with credit rules!";
    }

    public String getMessage() {
        return message;
    }
}
