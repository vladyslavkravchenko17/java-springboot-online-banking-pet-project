package com.project.service;

import com.project.domain.*;
import com.project.exception.*;
import com.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PaymentService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CreditDepositRepository creditDepositRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Value("${commission.send}")
    private int commissionPercent;

    @Value("${commission.atm}")
    private int atmCommissionPercent;

    public void penalty(String sender, String receiver, double sum) {
        Card senderCard = cardRepository.findByNumber(sender);
        Card receiverCard = cardRepository.findByNumber(receiver);
        String conversionTypeName = senderCard.getCurrency().name() + "_TO_UAH";
        ConversionType conversionType = ConversionType.valueOf(conversionTypeName);
        double currentConversionRate = Double.parseDouble(conversionType.getValue());

        Transaction transaction = new Transaction();
        transaction.setComment("");
        transaction.setSender(senderCard);
        transaction.setReceiver(receiverCard);
        transaction.setPayedSum(sum);
        transaction.setReceivedSum(sum);
        transaction.setCommissionPercent(0);
        transaction.setCommission(0);
        transaction.setConversionType(conversionTypeName);
        transaction.setCurrentConversionRate(currentConversionRate);
        transaction.setType("NON_PAYMENT_PENALTY");
        senderCard.replenishBalance(-sum);

        payToBank(sum, senderCard.getCurrency().name(), TransactionType.NON_PAYMENT_PENALTY);
        cardRepository.save(senderCard);
        transactionRepository.create(transaction);
    }

    public void sendMoney(Transaction transaction) {
        Card senderCard = transaction.getSender();
        Card receiverCard = transaction.getReceiver();
        if (receiverCard == null) {
            throw new CardNotFoundException();
        }
        if (transaction.getSender().getNumber().equals(transaction.getReceiver().getNumber())) {
            throw new SameCardException();
        }
        double commissionSum = transaction.getReceivedSum() * commissionPercent / 100;
        if (senderCard.getBalance() < transaction.getReceivedSum() + commissionSum) {
            throw new NotEnoughMoneyException();
        }
        String conversionTypeName = senderCard.getCurrency().name() + "_TO_" + receiverCard.getCurrency().name();
        ConversionType conversionType = ConversionType.valueOf(conversionTypeName);
        double currentConversionRate = Double.parseDouble(conversionType.getValue());
        double receivedSum = transaction.getReceivedSum() * currentConversionRate;
        double payedSum = transaction.getReceivedSum() + commissionSum;

        transaction.setPayedSum(payedSum);
        transaction.setReceivedSum(receivedSum);
        transaction.setCommissionPercent(commissionPercent);
        transaction.setCommission(commissionSum);
        transaction.setConversionType(conversionTypeName);
        transaction.setCurrentConversionRate(currentConversionRate);
        transaction.setType("CARD_TO_CARD");

        senderCard.replenishBalance(-payedSum);
        receiverCard.replenishBalance(receivedSum);
        payToBank(commissionSum, senderCard.getCurrency().name(), TransactionType.COMMISSION);

        cardRepository.save(senderCard);
        cardRepository.save(receiverCard);
        transactionRepository.create(transaction);
    }

    public void withdraw(Card card, double sum) {
        String conversionTypeName = "UAH_TO_" + card.getCurrency().name();
        ConversionType conversionType = ConversionType.valueOf(conversionTypeName);
        double currentConversionRate = Double.parseDouble(conversionType.getValue());
        double commissionSum = (sum * atmCommissionPercent / 100) * currentConversionRate;
        double payedSum = sum * currentConversionRate + commissionSum;
        if (payedSum > card.getBalance()) {
            throw new AtmException();
        }
        double receivedSum = sum * currentConversionRate;

        Transaction transaction = new Transaction();
        transaction.setComment("");
        transaction.setPayedSum(payedSum);
        transaction.setReceivedSum(receivedSum);
        transaction.setSender(card);
        transaction.setReceiver(bankAccountRepository.findATM());
        transaction.setCommissionPercent(atmCommissionPercent);
        transaction.setCommission(commissionSum);
        transaction.setConversionType(conversionTypeName);
        transaction.setCurrentConversionRate(currentConversionRate);
        transaction.setType("CARD_TO_CASH");

        card.replenishBalance(-payedSum);
        payToBank(commissionSum, card.getCurrency().name(), TransactionType.COMMISSION);

        cardRepository.save(card);
        transactionRepository.create(transaction);
    }

    public void deposit(Card card, double sum) {
        Transaction transaction = new Transaction();
        String conversionTypeName = "UAH_TO_" + card.getCurrency().name();
        ConversionType conversionType = ConversionType.valueOf(conversionTypeName);
        double currentConversionRate = Double.parseDouble(conversionType.getValue());
        double commissionSum = sum * atmCommissionPercent / 100;
        double receivedSum = (sum - commissionSum) * currentConversionRate;

        transaction.setComment("");
        transaction.setPayedSum(sum);
        transaction.setReceivedSum(receivedSum);
        transaction.setSender(bankAccountRepository.findATM());
        transaction.setReceiver(card);
        transaction.setCommissionPercent(atmCommissionPercent);
        transaction.setCommission(commissionSum);
        transaction.setConversionType(conversionTypeName);
        transaction.setCurrentConversionRate(Double.parseDouble(conversionType.getValue()));
        transaction.setType("CASH_TO_CARD");

        card.replenishBalance(receivedSum);
        payToBank(commissionSum, card.getCurrency().name(), TransactionType.COMMISSION);

        cardRepository.save(card);
        transactionRepository.create(transaction);
    }

    public void createScheduledPayment(PlannedPayment plannedPayment, String date, String time, boolean repeatable, int period) {
        if (cardRepository.findByNumber(plannedPayment.getReceiverNumber()) == null) {
            throw new ScheduledPaymentException();
        }
        String dateTimeString = date + " " + time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        plannedPayment.setPaymentDateTime(dateTime);
        plannedPayment.setRepeatable(repeatable);
        plannedPayment.setPeriod(period);
        cardRepository.createPlannedPayment(plannedPayment);
    }

    public void saveScheduledPayment(PlannedPayment plannedPayment) {
        if (cardRepository.findByNumber(plannedPayment.getReceiverNumber()) == null) {
            throw new ScheduledPaymentEditException(plannedPayment.getId());
        }
        cardRepository.savePlannedPayment(plannedPayment);
    }

    public void createCredit(Credit credit) {
        Card card = credit.getCard();
        if (card.getLimit() >= credit.getCardReceivedSum() && card.getMinCreditSum() <= credit.getCardReceivedSum()) {
            creditDepositRepository.create(credit);
            card.replenishBalance(credit.getCardReceivedSum());
            cardRepository.save(card);

            Transaction transaction = new Transaction();
            transaction.setSender(bankAccountRepository.findBank());
            transaction.setReceiver(card);
            transaction.setPayedSum(credit.getCardReceivedSum());
            transaction.setReceivedSum(credit.getCardReceivedSum());
            transaction.setCommission(0);
            transaction.setCommissionPercent(0);
            transaction.setComment("");
            transaction.setType("CREDIT");
            transaction.setConversionType(card.getCurrency() + "_TO_" + card.getCurrency());
            transaction.setCurrentConversionRate(1);

            card.replenishLimit((int) -credit.getCardReceivedSum());
            payToBank(-credit.getCardReceivedSum(), card.getCurrency().name(), TransactionType.CREDIT);

            cardRepository.save(card);
            transactionRepository.create(transaction);
        } else {
            throw new CreditLimitException();
        }
    }

    public void createDeposit(Deposit deposit) {
        Card card = deposit.getCard();
        if (card.getBalance() >= deposit.getCardPayedSum()) {
            creditDepositRepository.create(deposit);
            card.replenishBalance(-deposit.getCardPayedSum());

            Transaction transaction = new Transaction();
            transaction.setSender(card);
            transaction.setReceiver(bankAccountRepository.findBank());
            transaction.setPayedSum(deposit.getCardPayedSum());
            transaction.setReceivedSum(deposit.getCardPayedSum());
            transaction.setCommission(0);
            transaction.setCommissionPercent(0);
            transaction.setComment("");
            transaction.setType("DEPOSIT");
            transaction.setConversionType(card.getCurrency() + "_TO_" + card.getCurrency());
            transaction.setCurrentConversionRate(1);

            payToBank(deposit.getCardPayedSum(), card.getCurrency().name(), TransactionType.DEPOSIT);

            cardRepository.save(card);
            transactionRepository.create(transaction);
        } else throw new DepositException();
    }

    public void repayCredit(Credit credit) {
        Card card = credit.getCard();
        Transaction transaction = new Transaction();
        transaction.setSender(card);
        transaction.setReceiver(bankAccountRepository.findBank());
        transaction.setPayedSum(credit.getSumPerMonth());
        transaction.setReceivedSum(credit.getSumPerMonth());
        transaction.setCommission(0);
        transaction.setCommissionPercent(0);
        transaction.setComment("");
        transaction.setType("CREDIT_REPAYMENT");
        transaction.setConversionType(card.getCurrency() + "_TO_" + card.getCurrency());
        transaction.setCurrentConversionRate(1);

        credit.repay();
        card.replenishBalance(-credit.getSumPerMonth());
        payToBank(credit.getSumPerMonth(), card.getCurrency().name(), TransactionType.CREDIT_REPAYMENT);

        cardRepository.save(card);
        creditDepositRepository.save(credit);
        transactionRepository.create(transaction);
    }

    public void prematureCreditRepay(Credit credit) {
        Card card = credit.getCard();
        double remainingSum = credit.getSumToRepay() - credit.getCurrentRepaidSum();
        if (card.getBalance() < remainingSum) {
            throw new PrematureCreditRepayException();
        }
        credit.setSumPerMonth(remainingSum);
        repayCredit(credit);
        credit.setRepaid(true);
        card.replenishLimit((int) credit.getSumToRepay());
        creditDepositRepository.save(credit);
        cardRepository.save(card);
    }

    public void repayDeposit(Deposit deposit) {
        Card card = deposit.getCard();
        Transaction transaction = new Transaction();
        transaction.setSender(bankAccountRepository.findBank());
        transaction.setReceiver(card);
        transaction.setPayedSum(deposit.getSumPerMonth());
        transaction.setReceivedSum(deposit.getSumPerMonth());
        transaction.setCommission(0);
        transaction.setCommissionPercent(0);
        transaction.setComment("");
        transaction.setType("DEPOSIT_REPAYMENT");
        transaction.setConversionType(card.getCurrency() + "_TO_" + card.getCurrency());
        transaction.setCurrentConversionRate(1);

        deposit.repay();
        card.replenishBalance(deposit.getSumPerMonth());
        payToBank(-deposit.getSumPerMonth(), card.getCurrency().name(), TransactionType.DEPOSIT_REPAYMENT);

        cardRepository.save(card);
        creditDepositRepository.save(deposit);
        transactionRepository.create(transaction);
    }

    public void prematureDepositReturn(Deposit deposit) {
        double remainingSum = deposit.getCardPayedSum() - deposit.getCurrentReceivedSum();
        deposit.setSumPerMonth(remainingSum);
        repayDeposit(deposit);
        deposit.setRepaid(true);
        creditDepositRepository.save(deposit);

    }

    public void purchaseSubscription(SubscriptionInfo info, boolean repeatable, User user, String cardNumber, double sum) {
        Card card = cardRepository.findByNumber(cardNumber);
        if (card.getBalance() < sum) {
            throw new SubscriptionException();
        }
        String conversionTypeName = "UAH_TO_" + card.getCurrency().name();
        ConversionType conversionType = ConversionType.valueOf(conversionTypeName);
        double currentConversionRate = Double.parseDouble(conversionType.getValue());
        double payedSum = sum * currentConversionRate;

        Subscription subscription = new Subscription();
        subscription.setSubscriptionInfo(info);
        subscription.setUserId(user.getId());
        subscription.setCard(card);
        subscription.setRepeatable(repeatable);
        subscription.setEndDate(LocalDate.now().plusMonths(1));

        Transaction transaction = new Transaction();
        transaction.setSender(cardRepository.findByNumber(cardNumber));
        transaction.setReceiver(bankAccountRepository.findSubscriptionInfoCard(info.getName()));
        transaction.setPayedSum(payedSum);
        transaction.setReceivedSum(sum);
        transaction.setCommission(0);
        transaction.setCommissionPercent(0);
        transaction.setComment("");
        transaction.setType("SUBSCRIPTION_PURCHASE");
        String transactionConversion = card.getCurrency().name() + "_TO_UAH";
        ConversionType transactionConversionType = ConversionType.valueOf(transactionConversion);
        transaction.setConversionType(String.valueOf(transactionConversionType));
        transaction.setCurrentConversionRate(Double.parseDouble(transactionConversionType.getValue()));

        card.replenishBalance(-payedSum);

        subscriptionRepository.createSubscription(subscription);
        transactionRepository.create(transaction);
        cardRepository.save(card);
    }

    public void continueSubscription(Subscription subscription) {
        Card card = cardRepository.findByNumber(subscription.getCardNumber());
        SubscriptionInfo info = subscription.getSubscriptionInfo();
        card.replenishBalance(-info.getSumPerMonth());
        subscription.setEndDate(subscription.getEndDate().plusMonths(1));

        Transaction transaction = new Transaction();
        transaction.setSender(subscription.getCard());
        transaction.setReceiver(bankAccountRepository.findSubscriptionInfoCard(info.getName()));
        transaction.setPayedSum(info.getSumPerMonth());
        transaction.setReceivedSum(info.getSumPerMonth());
        transaction.setCommission(0);
        transaction.setCommissionPercent(0);
        transaction.setComment("");
        transaction.setType("SUBSCRIPTION_PURCHASE");
        ConversionType conversionType = ConversionType.valueOf(card.getCurrency() + "_TO_UAH");
        transaction.setConversionType(String.valueOf(conversionType));
        transaction.setCurrentConversionRate(Double.parseDouble(conversionType.getValue()));

        transactionRepository.create(transaction);
        cardRepository.save(card);
        subscriptionRepository.saveSubscription(subscription);
    }

    private void payToBank(double sum, String currency, TransactionType type) {
        ConversionType conversionType = ConversionType.valueOf(currency + "_TO_UAH");
        double conversionRate = Double.parseDouble(conversionType.getValue());
        double sumToPay = sum * conversionRate;
        Card bankAccount = bankAccountRepository.getBankAccount();
        bankAccount.replenishBalance(sumToPay);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        IncomeReport todayReport = bankAccountRepository.findIncomeByDate(formatter.format(LocalDate.now()));
        todayReport.replenish(sumToPay, type);

        bankAccountRepository.save(bankAccount);
        bankAccountRepository.saveReport(todayReport);
    }
}
