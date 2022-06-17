package com.project.exception;

public class CreditLimitException  extends RuntimeException{
    private final String message;

    public CreditLimitException() {
        super("Follow credit limits!");
        message = "Follow credit limits!";
    }

    public String getMessage() {
        return message;
    }
}
