package com.project.domain;

public enum ConversionType {
    USD_TO_USD("1.00"),
    EUR_TO_EUR("1.00"),
    UAH_TO_UAH("1.00"),

    USD_TO_EUR("0.95"),
    EUR_TO_USD("1.06"),

    USD_TO_UAH("30.22"),
    UAH_TO_USD("0.033"),

    EUR_TO_UAH("31.90"),
    UAH_TO_EUR("0.031");

    private final String rate;

    ConversionType(String rate) {
        this.rate = rate;
    }

    public String getValue(){
        return rate;
    }

}
