package com.project.scheduler;

import com.project.domain.*;
import com.project.repository.CardRepository;
import com.project.repository.CreditDepositRepository;
import com.project.repository.SubscriptionRepository;
import com.project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class SchedulePaymentJob {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CreditDepositRepository creditDepositRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Scheduled(cron = "0 * * * * *")
    public void payScheduled() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String nowFormat = now.format(formatter);
        List<PlannedPayment> plannedPayments = cardRepository.findAllPlannedPayments();
        plannedPayments.forEach(payment -> {
            LocalDateTime paymentDateTime = payment.getPaymentDateTime();
            String paymentDateTimeFormat = payment.getPaymentDateTime().format(formatter);
            if (paymentDateTimeFormat.equals(nowFormat)) {
                Transaction transaction = new Transaction();
                transaction.setReceiver(payment.getReceiver());
                transaction.setSender(payment.getSender());
                transaction.setReceivedSum(payment.getSum());
                transaction.setComment(payment.getDescription());
                paymentService.sendMoney(transaction);
                if (payment.isRepeatable()) {
                    LocalDateTime nextPayment = paymentDateTime.plusDays(payment.getPeriod());
                    payment.setPaymentDateTime(nextPayment);
                    cardRepository.savePlannedPayment(payment);
                } else {
                    cardRepository.deletePlannedPayment(payment.getId());
                }
            }
        });
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void repayCredits() {
        List<Credit> credits = creditDepositRepository.findAllCredits();
        LocalDate now = LocalDate.now();
        credits.forEach(credit -> {
            if (!credit.isRepaid() && credit.getNextPayment().equals(now)) {
                if (credit.getCurrentMonth() + 1 == credit.getMonths()) {
                    credit.setRepaid(true);
                    Card card = credit.getCard();
                    card.replenishLimit((int) credit.getCardReceivedSum());
                    cardRepository.save(card);
                }
                paymentService.repayCredit(credit);
            }
        });
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void repayDeposits() {
        List<Deposit> deposits = creditDepositRepository.findAllDeposits();
        LocalDate now = LocalDate.now();
        deposits.forEach(deposit -> {
            if (!deposit.isRepaid() && deposit.getNextPayment().equals(now)) {
                if (deposit.getCurrentMonth() + 1 == deposit.getMonths()) {
                    deposit.setRepaid(true);
                }
                paymentService.repayDeposit(deposit);
            }
        });
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void payPenalties() {
        List<Card> cards = cardRepository.findAll();

        cards.forEach(card -> {
            if (card.getBalance() < 0) {
                paymentService.penalty(card.getNumber(), "Bank", 150);
            }
        });
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void repaySubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        subscriptions.forEach(s -> {
            if (s.getEndDate().equals(LocalDate.now())) {
                if (s.isRepeatable()) {
                    paymentService.continueSubscription(s);
                } else {
                    subscriptionRepository.deleteSubscription(s.getId());
                }
            }
        });
    }
}
