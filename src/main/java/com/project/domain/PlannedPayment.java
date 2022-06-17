package com.project.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class PlannedPayment {
    private long id;
    private String description;
    private Card sender;
    @NotNull
    @Size(min = 16, max = 16, message = "Card number contains 16 digits!")
    private String receiverNumber;
    private Card receiver;
    @DecimalMin(value = "1.00", message = "Sum must be greater than 0!")
    private double sum;
    private String receiverName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime paymentDateTime;
    private int periodInDays;
    private boolean repeatable;

    public Card getSender() {
        return sender;
    }

    public void setSender(Card sender) {
        this.sender = sender;
    }

    public Card getReceiver() {
        return receiver;
    }

    public void setReceiver(Card receiver) {
        this.receiver = receiver;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getPeriod() {
        return periodInDays;
    }

    public void setPeriod(int periodInDays) {
        this.periodInDays = periodInDays;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(LocalDateTime paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    @Override
    public String toString() {
        return "PlannedPayment{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", sum=" + sum +
                ", paymentDateTime=" + paymentDateTime +
                ", periodInDays=" + periodInDays +
                ", repeatable=" + repeatable +
                '}';
    }
}
