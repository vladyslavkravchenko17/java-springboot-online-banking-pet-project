package com.project.domain;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class Transaction implements Comparable<Transaction> {
    private long id;

    private Card sender;
    @NotNull
    @Size(min = 16, max = 16, message = "Card number contains 16 digits!")
    private String receiverNumber;
    private Card receiver;
    private String comment;
    private double payedSum;
    @NotNull
    @DecimalMin(value = "1.00", message = "Sum must be greater than 0!")
    private double receivedSum;
    private int commissionPercent;
    private double commission;
    private ConversionType conversionType;
    private double currentConversionRate;
    private TransactionType type;
    private LocalDateTime dateTime;

    private boolean received;
    private String partnerName;

    @Override
    public int compareTo(Transaction otherTransaction) {
        if (getDateTime().isAfter(otherTransaction.getDateTime())) return 1;
        if (getDateTime().isBefore(otherTransaction.getDateTime())) return -1;
        return 0;
    }

    public String getBeautifulDateTime() {
        String day = String.valueOf(dateTime.getDayOfMonth());
        String month = String.valueOf(dateTime.getMonthValue());
        String minute = String.valueOf(dateTime.getMinute());
        if (dateTime.getDayOfMonth() < 10) day = "0" + day;
        if (dateTime.getMonthValue() < 10) month = "0" + month;
        if (dateTime.getMinute() < 10) minute = "0" + minute;
        return day + "." + month + "." + dateTime.getYear() + " "
                + dateTime.getHour() + ":" + minute;
    }

    public ConversionType getConversionType() {
        return conversionType;
    }

    public void setConversionType(String conversionType) {
        this.conversionType = ConversionType.valueOf(conversionType);
    }

    public double getCurrentConversionRate() {
        return currentConversionRate;
    }

    public void setCurrentConversionRate(double currentConversionRate) {
        this.currentConversionRate = currentConversionRate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public int getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(int commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = TransactionType.valueOf(type);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public double getPayedSum() {
        return payedSum;
    }

    public void setPayedSum(double payedSum) {
        this.payedSum = payedSum;
    }

    public double getReceivedSum() {
        return receivedSum;
    }

    public void setReceivedSum(double receivedSum) {
        this.receivedSum = receivedSum;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

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
}
