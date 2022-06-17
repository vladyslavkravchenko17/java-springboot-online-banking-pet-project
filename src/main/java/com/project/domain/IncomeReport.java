package com.project.domain;

import java.time.LocalDate;

public class IncomeReport {
    private long id;
    private LocalDate date;
    private double commissionIncome;
    private double creditIncome;
    private double penaltyIncome;
    private double depositIncome;
    private double depositLose;
    private double creditLose;
    private String monthYear;

    public void replenish(double sum, TransactionType type) {
        switch(type) {
            case CREDIT:
                creditLose -= sum;
                break;
            case CREDIT_REPAYMENT:
                creditIncome += sum;
                break;
            case DEPOSIT:
                depositIncome += sum;
                break;
            case DEPOSIT_REPAYMENT:
                depositLose -= sum;
                break;
            case NON_PAYMENT_PENALTY:
                penaltyIncome += sum;
                break;
            case COMMISSION:
                commissionIncome += sum;
                break;
        }
    }

    public double getTotalGain(){
        return getTotalIncome() - getTotalLose();
    }

    public double getTotalIncome(){
        return commissionIncome + penaltyIncome + depositIncome + creditIncome;
    }

    public double getTotalLose(){
        return depositLose + creditLose;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public double getPenaltyIncome() {
        return penaltyIncome;
    }

    public void setPenaltyIncome(double penaltyIncome) {
        this.penaltyIncome = penaltyIncome;
    }

    public double getDepositIncome() {
        return depositIncome;
    }

    public void setDepositIncome(double depositIncome) {
        this.depositIncome = depositIncome;
    }

    public double getDepositLose() {
        return depositLose;
    }

    public void setDepositLose(double depositLose) {
        this.depositLose = depositLose;
    }

    public double getCreditLose() {
        return creditLose;
    }

    public void setCreditLose(double creditLose) {
        this.creditLose = creditLose;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getCommissionIncome() {
        return commissionIncome;
    }

    public void setCommissionIncome(double commissionIncome) {
        this.commissionIncome = commissionIncome;
    }

    public double getCreditIncome() {
        return creditIncome;
    }

    public void setCreditIncome(double creditIncome) {
        this.creditIncome = creditIncome;
    }

    @Override
    public String toString() {
        return "IncomeReport{" +
                "id=" + id +
                ", date=" + date +
                ", commissionIncome=" + commissionIncome +
                ", creditIncome=" + creditIncome +
                ", penaltyIncome=" + penaltyIncome +
                ", depositIncome=" + depositIncome +
                ", depositLose=" + depositLose +
                ", creditLose=" + creditLose +
                ", monthYear='" + monthYear + '\'' +
                '}';
    }
}
