package com.project.domain;

public enum TransactionType {
    CARD_TO_CARD("Card replenishment."),
    CASH_TO_CARD("Card deposit via ATM."),
    CARD_TO_CASH("Cash withdraw via ATM."),
    CREDIT("Credit."),
    DEPOSIT("Deposit."),
    CREDIT_REPAYMENT("Credit repayment."),
    DEPOSIT_REPAYMENT("Deposit repayment."),
    NON_PAYMENT_PENALTY("Penalty for non-payment."),
    SUBSCRIPTION_PURCHASE("Subscription purchase."),
    COMMISSION("Commission.");

    private final String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getValue(){
        return type;
    }
}
