package com.project.domain;

import java.time.LocalDate;

public class Credit {
    private long id;
    private Card card;
    private double cardReceivedSum;
    private int months;
    private double sumPerMonth;
    private int percent;
    private double SumToRepay;
    private double currentRepaidSum;
    private int currentMonth;
    private LocalDate startDate;
    private LocalDate nextPayment;
    private boolean isRepaid;

    public void repay() {
        currentRepaidSum += sumPerMonth;
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

    public double getCardReceivedSum() {
        return cardReceivedSum;
    }

    public void setCardReceivedSum(double cardReceivedSum) {
        this.cardReceivedSum = cardReceivedSum;
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

    public double getSumToRepay() {
        return SumToRepay;
    }

    public void setSumToRepay(double sumToRepay) {
        SumToRepay = sumToRepay;
    }

    public double getCurrentRepaidSum() {
        return currentRepaidSum;
    }

    public void setCurrentRepaidSum(double currentRepaidSum) {
        this.currentRepaidSum = currentRepaidSum;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    public boolean isRepaid() {
        return isRepaid;
    }

    public void setRepaid(boolean repaid) {
        isRepaid = repaid;
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
}
