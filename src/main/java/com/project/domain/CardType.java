package com.project.domain;

public enum CardType {
    DEBIT_CARD("Debit Card"),
    CREDIT_CARD("Credit Card"),
    COMPANY_CARD("Company Card"),
    BANK_ACCOUNT("Bank account");

    private final String type;

    CardType(String type) {
        this.type = type;
    }

    public String getValue(){
        return type;
    }
}
