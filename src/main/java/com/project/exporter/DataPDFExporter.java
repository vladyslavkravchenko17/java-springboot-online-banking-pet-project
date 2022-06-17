package com.project.exporter;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.project.domain.*;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@Component
public class DataPDFExporter {

    @Autowired
    private UserRepository userRepository;

    public DataPDFExporter() {
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 2, 227));
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase(" ", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase(" ", font));
        table.addCell(cell);
    }

    private void writeUsersTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 2, 227));
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase("Id", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Full name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Role", font));
        table.addCell(cell);
    }

    private void writeCardsTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 2, 227));
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase("Id", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Owner", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Number", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Type", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Company name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Balance", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Currency", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Credit limit", font));
        table.addCell(cell);
    }

    private void writeTransactionsTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 2, 227));
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase("Id", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date-time", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Sender", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Receiver", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Payed sum", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Received sum", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Conversion type", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Commission sum", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Type", font));
        table.addCell(cell);
    }

    private void writeCreditsTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 2, 227));
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase("Id", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Start date", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Card number", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Received sum", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Sum to repay", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Months", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("%", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Next payment date", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Curr. month", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Current repaid sum", font));
        table.addCell(cell);
    }

    private void writeDepositsTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 2, 227));
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase("Id", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Start date", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Card number", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Payed sum", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Sum to receive", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Months", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("%", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Next payment date", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Cur. month", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Current received sum", font));
        table.addCell(cell);
    }

    private void writeMonthlyReportsTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 2, 227));
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase("Date", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Total gain", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Total income", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Total lose", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Commission income", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Penalty income", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Credit income", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Credit lose", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Deposit income", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Deposit lose", font));
        table.addCell(cell);
    }

    private void writeTransactionTableData(PdfPTable table, Transaction transaction) {
        String senderCurrency = transaction.getConversionType().name().substring(0, 3);
        String receiverCurrency = transaction.getConversionType().name().substring(7, 10);
        table.addCell("Date");
        table.addCell(String.valueOf(transaction.getBeautifulDateTime()));
        if (transaction.getSender().getNumber().matches("[0-9]+")) {
            table.addCell("Sender name");
            User user = userRepository.findById(transaction.getSender().getUserId());
            table.addCell(user.getFirstName() + " " + user.getLastName());
            table.addCell("Sender card number");
            table.addCell(transaction.getSender().getNumber());
        } else {
            table.addCell("Sender");
            table.addCell(transaction.getSender().getNumber());
        }
        if (transaction.getReceiver().getNumber().matches("[0-9]+")) {
            table.addCell("Receiver name");
            User user = userRepository.findById(transaction.getReceiver().getUserId());
            table.addCell(user.getFirstName() + " " + user.getLastName());
            table.addCell("Receiver card number");
            table.addCell(transaction.getReceiver().getNumber());
        } else {
            table.addCell("Receiver");
            table.addCell(transaction.getReceiver().getNumber());
        }
        table.addCell("Payed sum");
        table.addCell(transaction.getPayedSum() + " " + senderCurrency);
        table.addCell("Received sum");
        table.addCell(transaction.getReceivedSum() + " " + receiverCurrency);
        table.addCell("Conversion type");
        table.addCell(transaction.getConversionType().name());
        table.addCell("Conversion rate");
        table.addCell(String.valueOf(transaction.getCurrentConversionRate()));
        table.addCell("Commission %");
        table.addCell(transaction.getCommissionPercent() + "%");
        table.addCell("Commission sum");
        table.addCell(transaction.getCommission() + " " + senderCurrency);
        if (!transaction.getComment().equals("")) {
            table.addCell("Comment");
            table.addCell(transaction.getComment());
        }
        table.addCell("Transaction type");
        table.addCell(transaction.getType().getValue());
    }

    private void writeTransactionsTableData(PdfPTable table, List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            String senderCurrency = transaction.getConversionType().name().substring(0, 3);
            String receiverCurrency = transaction.getConversionType().name().substring(7, 10);
            table.addCell(String.valueOf(transaction.getId()));
            table.addCell(transaction.getBeautifulDateTime());
            table.addCell(transaction.getSender().getNumber());
            table.addCell(transaction.getReceiver().getNumber());
            table.addCell(transaction.getPayedSum() + " " + senderCurrency);
            table.addCell(transaction.getReceivedSum() + " " + receiverCurrency);
            table.addCell(transaction.getConversionType().name());
            table.addCell(transaction.getCommission() + " " + senderCurrency);
            table.addCell(transaction.getType().getValue());
        }
    }

    private void writeUserTableData(PdfPTable table, User user) {
        table.addCell("Id");
        table.addCell(String.valueOf(user.getId()));
        table.addCell("Full name");
        table.addCell(user.getFirstName() + " " + user.getLastName());
        table.addCell("Email");
        table.addCell(user.getEmail());
        table.addCell("Role");
        table.addCell(user.getRole().name());
    }

    private void writeUsersTableData(PdfPTable table, List<User> users) {
        for (User user : users) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getFirstName() + " " + user.getLastName());
            table.addCell(user.getEmail());
            table.addCell(user.getRole().name());
        }
    }

    private void writeCardTableData(PdfPTable table, Card card) {
        table.addCell("Id");
        table.addCell(String.valueOf(card.getId()));
        table.addCell("Number");
        table.addCell(card.getNumber());
        table.addCell("Type");
        table.addCell(card.getType().getValue());
        if (card.getCompanyName() != null) {
            table.addCell("Company name");
            table.addCell(card.getCompanyName());
        }
        table.addCell("Balance");
        table.addCell(card.getBalance() + " " + card.getCurrency().name());
        table.addCell("Currency");
        table.addCell(card.getCurrency().name());
        table.addCell("Credit limit");
        table.addCell(card.getLimit() + " " + card.getCurrency().name());
    }

    private void writeCardsTableData(PdfPTable table, List<Card> cards) {
        for (Card card : cards) {
            table.addCell(String.valueOf(card.getId()));
            User user = userRepository.findById(card.getUserId());
            table.addCell(user.getFirstName() + " " + user.getLastName());
            table.addCell(card.getNumber());
            table.addCell(card.getType().getValue());
            if (card.getCompanyName() != null) {
                table.addCell(card.getCompanyName());
            } else {
                table.addCell("-");
            }
            table.addCell(card.getBalance() + " " + card.getCurrency().name());
            table.addCell(card.getCurrency().name());
            table.addCell(card.getLimit() + " " + card.getCurrency().name());
        }
    }

    private void writeCreditTableData(PdfPTable table, Credit credit) {
        table.addCell("Id");
        table.addCell(String.valueOf(credit.getId()));
        table.addCell("Start date");
        table.addCell(String.valueOf(credit.getStartDate()));
        table.addCell("Card number");
        table.addCell(credit.getCard().getNumber());
        table.addCell("Received sum");
        table.addCell(credit.getCardReceivedSum() + " " + credit.getCard().getCurrency().name());
        table.addCell("Sum to repay");
        table.addCell(credit.getSumToRepay() + " " + credit.getCard().getCurrency().name());
        table.addCell("Amount of months");
        table.addCell(String.valueOf(credit.getMonths()));
        table.addCell("Percent");
        table.addCell(credit.getPercent() + "%");
        table.addCell("Next payment date");
        if (!credit.isRepaid())
            table.addCell(String.valueOf(credit.getNextPayment()));
        else
            table.addCell("Credit is repaid.");
        table.addCell("Current month");
        table.addCell(String.valueOf(credit.getCurrentMonth()));
        table.addCell("Current repaid sum");
        table.addCell(credit.getCurrentRepaidSum() + " " + credit.getCard().getCurrency().name());
    }

    private void writeCreditsTableData(PdfPTable table, List<Credit> credits) {
        for (Credit credit : credits) {
            table.addCell(String.valueOf(credit.getId()));
            table.addCell(String.valueOf(credit.getStartDate()));
            table.addCell(credit.getCard().getNumber());
            table.addCell(credit.getCardReceivedSum() + " " + credit.getCard().getCurrency().name());
            table.addCell(credit.getSumToRepay() + " " + credit.getCard().getCurrency().name());
            table.addCell(String.valueOf(credit.getMonths()));
            table.addCell(credit.getPercent() + "");
            if (!credit.isRepaid())
                table.addCell(String.valueOf(credit.getNextPayment()));
            else
                table.addCell("Credit is repaid.");
            table.addCell(String.valueOf(credit.getCurrentMonth()));
            table.addCell(credit.getCurrentRepaidSum() + " " + credit.getCard().getCurrency().name());
        }
    }

    private void writeDepositTableData(PdfPTable table, Deposit deposit) {
        table.addCell("Id");
        table.addCell(String.valueOf(deposit.getId()));
        table.addCell("Start date");
        table.addCell(String.valueOf(deposit.getStartDate()));
        table.addCell("Card number");
        table.addCell(deposit.getCard().getNumber());
        table.addCell("Payed sum");
        table.addCell(deposit.getCardPayedSum() + " " + deposit.getCard().getCurrency().name());
        table.addCell("Sum to receive");
        table.addCell(deposit.getSumToReceive() + " " + deposit.getCard().getCurrency().name());
        table.addCell("Amount of months");
        table.addCell(String.valueOf(deposit.getMonths()));
        table.addCell("Percent");
        table.addCell(deposit.getPercent() + "%");
        table.addCell("Next payment date");
        if (deposit.isRepaid())
            table.addCell(String.valueOf(deposit.getNextPayment()));
        else
            table.addCell("Deposit is repaid.");
        table.addCell("Current month");
        table.addCell(String.valueOf(deposit.getCurrentMonth()));
        table.addCell("Current received sum");
        table.addCell(deposit.getCurrentReceivedSum() + " " + deposit.getCard().getCurrency().name());
    }

    private void writeDepositsTableData(PdfPTable table, List<Deposit> deposits) {
        for (Deposit deposit : deposits) {
            table.addCell(String.valueOf(deposit.getId()));
            table.addCell(String.valueOf(deposit.getStartDate()));
            table.addCell(deposit.getCard().getNumber());
            table.addCell(deposit.getCardPayedSum() + " " + deposit.getCard().getCurrency().name());
            table.addCell(deposit.getSumToReceive() + " " + deposit.getCard().getCurrency().name());
            table.addCell(String.valueOf(deposit.getMonths()));
            table.addCell(deposit.getPercent() + "");
            if (!deposit.isRepaid())
                table.addCell(String.valueOf(deposit.getNextPayment()));
            else
                table.addCell("Credit is repaid.");
            table.addCell(String.valueOf(deposit.getCurrentMonth()));
            table.addCell(deposit.getCurrentReceivedSum() + " " + deposit.getCard().getCurrency().name());
        }
    }

    private void writeMonthlyIncomesTableData(PdfPTable table, List<IncomeReport> reports) {
        reports.forEach(report -> {
            table.addCell(report.getMonthYear());
            table.addCell(String.valueOf(report.getTotalGain()));
            table.addCell(String.valueOf(report.getTotalIncome()));
            table.addCell(String.valueOf(report.getTotalLose()));
            table.addCell(String.valueOf(report.getCommissionIncome()));
            table.addCell(String.valueOf(report.getPenaltyIncome()));
            table.addCell(String.valueOf(report.getCreditIncome()));
            table.addCell(String.valueOf(report.getCreditLose()));
            table.addCell(String.valueOf(report.getDepositIncome()));
            table.addCell(String.valueOf(report.getDepositLose()));
        });
    }

    private void writeDailyIncomesTableData(PdfPTable table, List<IncomeReport> reports) {
        reports.forEach(report -> {
            table.addCell(String.valueOf(report.getDate()));
            table.addCell(String.valueOf(report.getTotalGain()));
            table.addCell(String.valueOf(report.getTotalIncome()));
            table.addCell(String.valueOf(report.getTotalLose()));
            table.addCell(String.valueOf(report.getCommissionIncome()));
            table.addCell(String.valueOf(report.getPenaltyIncome()));
            table.addCell(String.valueOf(report.getCreditIncome()));
            table.addCell(String.valueOf(report.getCreditLose()));
            table.addCell(String.valueOf(report.getDepositIncome()));
            table.addCell(String.valueOf(report.getDepositLose()));
        });
    }

    private void writeDayIncomeTableData(PdfPTable table, IncomeReport report, String date) {
        table.addCell("Date");
        table.addCell(date);
        table.addCell("Total gain");
        table.addCell(report.getTotalGain() + " UAH");
        table.addCell("Total income");
        table.addCell(report.getTotalIncome() + " UAH");
        table.addCell("Total lose");
        table.addCell(report.getTotalLose() + " UAH");
        table.addCell("Commission income");
        table.addCell(report.getCommissionIncome() + " UAH");
        table.addCell("Penalty income");
        table.addCell(report.getPenaltyIncome() + " UAH");
        table.addCell("Credit income");
        table.addCell(report.getCreditIncome() + " UAH");
        table.addCell("Credit lose");
        table.addCell(report.getCreditLose() + " UAH");
        table.addCell("Deposit income");
        table.addCell(report.getDepositIncome() + " UAH");
        table.addCell("Deposit lose");
        table.addCell(report.getDepositLose() + " UAH");
    }


    public void exportTransaction(HttpServletResponse response, Transaction transaction) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Transaction Receipt", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{4.5f, 8.0f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTransactionTableData(table, transaction);
        document.add(table);
        document.close();
    }

    public void exportTransactions(HttpServletResponse response, List<Transaction> transactions) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Transactions", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f});
        table.setSpacingBefore(10);
        writeTransactionsTableHeader(table);
        writeTransactionsTableData(table, transactions);
        document.add(table);
        document.close();
    }

    public void exportUser(HttpServletResponse response, User user) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("User info", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{4.5f, 8.0f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeUserTableData(table, user);
        document.add(table);
        document.close();
    }

    public void exportUsers(HttpServletResponse response, List<User> users) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Users", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.0f, 4.5f, 3.0f, 2.0f});
        table.setSpacingBefore(10);
        writeUsersTableHeader(table);
        writeUsersTableData(table, users);
        document.add(table);
        document.close();
    }

    public void exportCard(HttpServletResponse response, Card card) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Card info", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{4.5f, 8.0f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeCardTableData(table, card);
        document.add(table);
        document.close();
    }

    public void exportCards(HttpServletResponse response, List<Card> cards) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Cards", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f});
        table.setSpacingBefore(10);
        writeCardsTableHeader(table);
        writeCardsTableData(table, cards);
        document.add(table);
        document.close();
    }

    public void exportCredit(HttpServletResponse response, Credit credit) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Credit info", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{4.5f, 8.0f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeCreditTableData(table, credit);
        document.add(table);
        document.close();
    }

    public void exportCredits(HttpServletResponse response, List<Credit> credits) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Credit info", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 1.5f, 1.0f, 2.0f, 1.5f, 2.0f});
        table.setSpacingBefore(10);
        writeCreditsTableHeader(table);
        writeCreditsTableData(table, credits);
        document.add(table);
        document.close();
    }

    public void exportDeposit(HttpServletResponse response, Deposit deposit) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Deposit info", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{4.5f, 8.0f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeDepositTableData(table, deposit);
        document.add(table);
        document.close();
    }

    public void exportDeposits(HttpServletResponse response, List<Deposit> deposits) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Deposit info", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 1.5f, 1.0f, 2.0f, 1.5f, 2.0f});
        table.setSpacingBefore(10);
        writeDepositsTableHeader(table);
        writeDepositsTableData(table, deposits);
        document.add(table);
        document.close();
    }

    public void exportMonthlyReports(HttpServletResponse response, List<IncomeReport> reports) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Monthly incomes (UAH)", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f});
        table.setSpacingBefore(10);
        writeMonthlyReportsTableHeader(table);
        writeMonthlyIncomesTableData(table, reports);
        document.add(table);
        document.close();
    }

    public void exportDailyReports(HttpServletResponse response, List<IncomeReport> reports) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Daily incomes (UAH)", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{3.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.5f, 2.0f, 2.0f, 2.5f, 2.5f});
        table.setSpacingBefore(10);
        writeMonthlyReportsTableHeader(table);
        writeDailyIncomesTableData(table, reports);
        document.add(table);
        document.close();
    }

    public void exportDayReport(HttpServletResponse response, IncomeReport report) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Day income report (UAH)", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{4.5f, 8.0f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeDayIncomeTableData(table, report, String.valueOf(report.getDate()));
        document.add(table);
        document.close();
    }

    public void exportMonthReport(HttpServletResponse response, IncomeReport monthReport, List<IncomeReport> reports) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Month income report (UAH)", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{4.5f, 8.0f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeDayIncomeTableData(table, monthReport, monthReport.getMonthYear());
        document.add(table);

        Paragraph p2 = new Paragraph("Days reports (UAH)", font);
        p2.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p2);
        PdfPTable table2 = new PdfPTable(10);
        table2.setWidthPercentage(100f);
        table2.setWidths(new float[]{3.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.5f, 2.0f, 2.0f, 2.5f, 2.5f});
        table2.setSpacingBefore(10);
        writeMonthlyReportsTableHeader(table2);
        writeDailyIncomesTableData(table2, reports);
        document.add(table2);

        document.close();
    }

}
