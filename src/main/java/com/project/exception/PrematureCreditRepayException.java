package com.project.exception;

public class PrematureCreditRepayException extends RuntimeException{
    private final String message;

    public PrematureCreditRepayException() {
        super("Not enough money!");
        message = "Not enough money!";
    }

    public String getMessage() {
        return message;
    }
}
