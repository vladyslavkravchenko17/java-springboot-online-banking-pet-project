package com.project.domain;

import java.io.Serializable;

public class Card implements Serializable, Comparable<Card>  {
    private long id;
    private Long userId;
    private String number;
    private double balance;
    private MoneyCurrency currency;
    private CardType type;
    private String companyName;
    private double limit;
    private double minCreditSum;


    public void replenishLimit(int sum){limit += sum;}

    public void replenishBalance(double sum) {
        balance += sum;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public double getMinCreditSum() {
        return minCreditSum;
    }

    public void setMinCreditSum(double minCreditSum) {
        this.minCreditSum = minCreditSum;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public CardType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = CardType.valueOf(type);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public MoneyCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = MoneyCurrency.valueOf(currency);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", userId=" + userId +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                ", currency=" + currency +
                ", type=" + type +
                ", companyName='" + companyName + '\'' +
                ", limit=" + limit +
                ", minCreditSum=" + minCreditSum +
                '}';
    }

    @Override
    public int compareTo(Card o) {
        if (id > o.getId()){
            return 1;
        } else if (id < o.getId()) {
            return -1;
        }
        return 0;
    }
}
