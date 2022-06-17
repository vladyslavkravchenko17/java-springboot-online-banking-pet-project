package com.project.domain;

import java.time.LocalDate;

public class Deposit {
    private long id;
    private Card card;
    private double cardPayedSum;
    private int months;
    private double sumPerMonth;
    private int percent;
    private double SumToReceive;
    private double currentReceivedSum;
    private int currentMonth;
    private LocalDate startDate;
    private LocalDate nextPayment;
    private boolean isRepaid;

    public void repay() {
        currentReceivedSum += sumPerMonth;
        currentMonth++;
        nextPayment = nextPayment.plusMonths(1);
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCardPayedSum() {
        return cardPayedSum;
    }

    public void setCardPayedSum(double cardPayedSum) {
        this.cardPayedSum = cardPayedSum;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public double getSumPerMonth() {
        return sumPerMonth;
    }

    public void setSumPerMonth(double sumPerMonth) {
        this.sumPerMonth = sumPerMonth;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public double getSumToReceive() {
        return SumToReceive;
    }

    public void setSumToReceive(double sumToReceive) {
        SumToReceive = sumToReceive;
    }

    public double getCurrentReceivedSum() {
        return currentReceivedSum;
    }

    public void setCurrentReceivedSum(double currentReceivedSum) {
        this.currentReceivedSum = currentReceivedSum;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    public LocalDate getNextPayment() {
        return nextPayment;
    }

    public void setNextPayment(LocalDate nextPayment) {
        this.nextPayment = nextPayment;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public boolean isRepaid() {
        return isRepaid;
    }

    public void setRepaid(boolean repaid) {
        isRepaid = repaid;
    }
}