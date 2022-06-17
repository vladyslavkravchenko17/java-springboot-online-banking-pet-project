package com.project.domain;

public enum MoneyCurrency {
    USD("$"),
    EUR("€"),
    UAH("₴");

    private final String currency;

    MoneyCurrency(String currency) {
        this.currency = currency;
    }

    public String getValue(){
        return currency;
    }
}
