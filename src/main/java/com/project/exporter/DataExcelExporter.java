package com.project.exporter;

import com.project.domain.*;
import com.project.repository.UserRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class DataExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    @Autowired
    private UserRepository userRepository;

    public DataExcelExporter() {
    }

    private void writeHeaderLine(String headerName) {
        sheet = workbook.createSheet(headerName);
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "           ", style);
        createCell(row, 1, "                       ", style);
    }

    private void writeUsersHeaderLine() {
        sheet = workbook.createSheet("Users");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Id", style);
        createCell(row, 1, "Full name", style);
        createCell(row, 2, "Email", style);
        createCell(row, 3, "Role", style);
    }

    private void writeCardsHeaderLine() {
        sheet = workbook.createSheet("Cards");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Id", style);
        createCell(row, 1, "Owner", style);
        createCell(row, 2, "Number", style);
        createCell(row, 3, "Type", style);
        createCell(row, 4, "Company name", style);
        createCell(row, 5, "Balance", style);
        createCell(row, 6, "Currency", style);
        createCell(row, 7, "Credit limit", style);
    }

    private void writeTransactionsHeaderLine() {
        sheet = workbook.createSheet("Transactions");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Id", style);
        createCell(row, 1, "Date-time", style);
        createCell(row, 2, "Sender", style);
        createCell(row, 3, "Sender number", style);
        createCell(row, 4, "Receiver", style);
        createCell(row, 5, "Receiver number", style);
        createCell(row, 6, "Payed sum", style);
        createCell(row, 7, "Received sum", style);
        createCell(row, 8, "Conversion type", style);
        createCell(row, 9, "Commission sum", style);
        createCell(row, 10, "Comment", style);
        createCell(row, 11, "Type", style);
    }

    private void writeCreditsHeaderLine() {
        sheet = workbook.createSheet("Credits");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Id", style);
        createCell(row, 1, "Start date", style);
        createCell(row, 2, "Card number", style);
        createCell(row, 3, "Received sum", style);
        createCell(row, 4, "Sum to repay", style);
        createCell(row, 5, "Months", style);
        createCell(row, 6, "%", style);
        createCell(row, 7, "Next payment date", style);
        createCell(row, 8, "Curr. month", style);
        createCell(row, 9, "Current repaid sum", style);
    }

    private void writeDepositsHeaderLine() {
        sheet = workbook.createSheet("Deposits");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Id", style);
        createCell(row, 1, "Start date", style);
        createCell(row, 2, "Card number", style);
        createCell(row, 3, "Payed sum", style);
        createCell(row, 4, "Sum to receive", style);
        createCell(row, 5, "Months", style);
        createCell(row, 6, "%", style);
        createCell(row, 7, "Next payment date", style);
        createCell(row, 8, "Curr. month", style);
        createCell(row, 9, "Current received sum", style);
    }

    private void writeDailyReportsHeaderLine() {
        sheet = workbook.createSheet("Daily reports");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Date", style);
        createCell(row, 1, "Total gain", style);
        createCell(row, 2, "Total income", style);
        createCell(row, 3, "Total lose", style);
        createCell(row, 4, "Commission income", style);
        createCell(row, 5, "Penalty income", style);
        createCell(row, 6, "Credit income", style);
        createCell(row, 7, "Credit lose", style);
        createCell(row, 8, "Deposit income", style);
        createCell(row, 9, "Deposit lose", style);
    }

    private void writeUserDataLines(User user) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        Row row1 = sheet.createRow(2);
        createCell(row1, 1, "Id", style);
        createCell(row1, 2, String.valueOf(user.getId()), style);
        Row row2 = sheet.createRow(3);
        createCell(row2, 1, "Full name", style);
        createCell(row2, 2, user.getFirstName() + " " + user.getLastName(), style);
        Row row3 = sheet.createRow(4);
        createCell(row3, 1, "Email", style);
        createCell(row3, 2, user.getEmail(), style);
        Row row4 = sheet.createRow(5);
        createCell(row4, 1, "Role", style);
        createCell(row4, 2, user.getRole().name(), style);
    }

    private void writeUsersDataLines(List<User> users) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (User user : users) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(user.getId()), style);
            createCell(row, columnCount++, user.getFirstName() + " " + user.getLastName(), style);
            createCell(row, columnCount++, user.getEmail(), style);
            createCell(row, columnCount++, user.getRole().name(), style);
        }
    }

    private void writeCardDataLines(Card card) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        int rowCount = 2;
        Row row1 = sheet.createRow(rowCount);
        createCell(row1, 1, "Id", style);
        createCell(row1, 2, String.valueOf(card.getId()), style);
        rowCount++;
        Row row2 = sheet.createRow(rowCount);
        createCell(row2, 1, "Number", style);
        createCell(row2, 2, card.getNumber(), style);
        rowCount++;
        Row row3 = sheet.createRow(rowCount);
        createCell(row3, 1, "Type", style);
        createCell(row3, 2, card.getType().getValue(), style);
        if (card.getCompanyName() != null) {
            rowCount++;
            Row row4 = sheet.createRow(rowCount);
            createCell(row4, 1, "Company name", style);
            createCell(row4, 2, card.getCompanyName(), style);
        }
        rowCount++;
        Row row4 = sheet.createRow(rowCount);
        createCell(row4, 1, "Balance", style);
        createCell(row4, 2, card.getBalance() + " " + card.getCurrency().name(), style);
        rowCount++;
        Row row5 = sheet.createRow(rowCount);
        createCell(row5, 1, "Currency", style);
        createCell(row5, 2, card.getCurrency().name(), style);
        rowCount++;
        Row row6 = sheet.createRow(rowCount);
        createCell(row6, 1, "Credit limit", style);
        createCell(row6, 2, card.getLimit() + " " + card.getCurrency().name(), style);
    }

    private void writeCardsDataLines(List<Card> cards) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Card card : cards) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(card.getId()), style);
            User user = userRepository.findById(card.getUserId());
            createCell(row, columnCount++, user.getFirstName() + " " + user.getLastName(), style);
            createCell(row, columnCount++, card.getNumber(), style);
            createCell(row, columnCount++, card.getType().getValue(), style);
            if (card.getCompanyName() != null)
                createCell(row, columnCount++, card.getCompanyName(), style);
            else
                createCell(row, columnCount++, "-", style);
            createCell(row, columnCount++, card.getBalance() + " " + card.getCurrency().name(), style);
            createCell(row, columnCount++, card.getCurrency().name(), style);
            createCell(row, columnCount++, card.getLimit() + " " + card.getCurrency().name(), style);
        }
    }

    private void writeTransactionDataLines(Transaction transaction) {
        String senderCurrency = transaction.getConversionType().name().substring(0, 3);
        String receiverCurrency = transaction.getConversionType().name().substring(7, 10);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        int rowCount = 2;
        Row row0 = sheet.createRow(rowCount);
        createCell(row0, 1, "Id", style);
        createCell(row0, 2, String.valueOf(transaction.getId()), style);
        rowCount++;
        Row row1 = sheet.createRow(rowCount);
        createCell(row1, 1, "Date", style);
        createCell(row1, 2, String.valueOf(transaction.getBeautifulDateTime()), style);
        if (transaction.getSender().getNumber().matches("[0-9]+")) {
            User user = userRepository.findById(transaction.getSender().getUserId());
            rowCount++;
            Row row2 = sheet.createRow(rowCount);
            createCell(row2, 1, "Sender name", style);
            createCell(row2, 2, user.getFirstName() + " " + user.getLastName(), style);
            rowCount++;
            Row row3 = sheet.createRow(rowCount);
            createCell(row3, 1, "Sender card number", style);
            createCell(row3, 2, transaction.getSender().getNumber(), style);
        } else {
            rowCount++;
            Row row2 = sheet.createRow(rowCount);
            createCell(row2, 1, "Sender", style);
            createCell(row2, 2, transaction.getSender().getNumber(), style);
        }
        if (transaction.getReceiver().getNumber().matches("[0-9]+")) {
            User user = userRepository.findById(transaction.getReceiver().getUserId());
            rowCount++;
            Row row2 = sheet.createRow(rowCount);
            createCell(row2, 1, "Receiver name", style);
            createCell(row2, 2, user.getFirstName() + " " + user.getLastName(), style);
            rowCount++;
            Row row3 = sheet.createRow(rowCount);
            createCell(row3, 1, "Receiver card number", style);
            createCell(row3, 2, transaction.getReceiver().getNumber(), style);
        } else {
            rowCount++;
            Row row2 = sheet.createRow(rowCount);
            createCell(row2, 1, "Receiver", style);
            createCell(row2, 2, transaction.getReceiver().getNumber(), style);
        }
        rowCount++;
        Row row2 = sheet.createRow(rowCount);
        createCell(row2, 1, "Payed sum", style);
        createCell(row2, 2, transaction.getPayedSum() + " " + senderCurrency, style);
        rowCount++;
        Row row3 = sheet.createRow(rowCount);
        createCell(row3, 1, "Received sum", style);
        createCell(row3, 2, transaction.getReceivedSum() + " " + receiverCurrency, style);
        rowCount++;
        Row row4 = sheet.createRow(rowCount);
        createCell(row4, 1, "Conversion type", style);
        createCell(row4, 2, transaction.getConversionType().name(), style);
        rowCount++;
        Row row5 = sheet.createRow(rowCount);
        createCell(row5, 1, "Conversion rate", style);
        createCell(row5, 2, String.valueOf(transaction.getConversionType().getValue()), style);
        rowCount++;
        Row row6 = sheet.createRow(rowCount);
        createCell(row6, 1, "Commission %", style);
        createCell(row6, 2, transaction.getCommissionPercent() + "%", style);
        rowCount++;
        Row row7 = sheet.createRow(rowCount);
        createCell(row7, 1, "Commission sum", style);
        createCell(row7, 2, transaction.getCommission() + " " + senderCurrency, style);
        if (transaction.getComment() != null) {
            rowCount++;
            Row row8 = sheet.createRow(rowCount);
            createCell(row8, 1, "Comment", style);
            createCell(row8, 2, transaction.getComment(), style);
        }
        rowCount++;
        Row row8 = sheet.createRow(rowCount);
        createCell(row8, 1, "Transaction type", style);
        createCell(row8, 2, transaction.getType().getValue(), style);
    }

    private void writeTransactionsDataLines(List<Transaction> transactions) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Transaction transaction : transactions) {
            String senderCurrency = transaction.getConversionType().name().substring(0, 3);
            String receiverCurrency = transaction.getConversionType().name().substring(7, 10);
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(transaction.getId()), style);
            createCell(row, columnCount++, transaction.getBeautifulDateTime(), style);


            if (transaction.getSender().getNumber().matches("[0-9]+")) {
                User user = userRepository.findById(transaction.getSender().getUserId());
                createCell(row, columnCount++, user.getFirstName() + " " + user.getLastName(), style);
                createCell(row, columnCount++, transaction.getSender().getNumber(), style);
            } else {
                createCell(row, columnCount++, transaction.getSender().getNumber(), style);
                createCell(row, columnCount++, "-", style);
            }
            if (transaction.getReceiver().getNumber().matches("[0-9]+")) {
                User user = userRepository.findById(transaction.getReceiver().getUserId());
                createCell(row, columnCount++, user.getFirstName() + " " + user.getLastName(), style);
                createCell(row, columnCount++, transaction.getReceiver().getNumber(), style);
            } else {
                createCell(row, columnCount++, transaction.getReceiver().getNumber(), style);
                createCell(row, columnCount++, "-", style);
            }
            createCell(row, columnCount++, transaction.getPayedSum() + " " + senderCurrency, style);
            createCell(row, columnCount++, transaction.getReceivedSum() + " " + receiverCurrency, style);
            createCell(row, columnCount++, transaction.getConversionType().name(), style);
            createCell(row, columnCount++, transaction.getCommission() + " " + senderCurrency, style);
            if (transaction.getComment() != null)
                createCell(row, columnCount++, transaction.getComment(), style);
            else
                createCell(row, columnCount++, "-",style);
            createCell(row, columnCount++, transaction.getType().getValue(), style);

        }
    }

    private void writeCreditDataLines(Credit credit) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        int rowCount = 2;
        Row row1 = sheet.createRow(rowCount);
        createCell(row1, 1, "Id", style);
        createCell(row1, 2, String.valueOf(credit.getId()), style);
        rowCount++;
        Row row2 = sheet.createRow(rowCount);
        createCell(row2, 1, "Start date", style);
        createCell(row2, 2, String.valueOf(credit.getStartDate()), style);
        rowCount++;
        Row row3 = sheet.createRow(rowCount);
        createCell(row3, 1, "Card number", style);
        createCell(row3, 2, credit.getCard().getNumber(), style);
        rowCount++;
        Row row4 = sheet.createRow(rowCount);
        createCell(row4, 1, "Received sum", style);
        createCell(row4, 2, credit.getCardReceivedSum() + " " + credit.getCard().getCurrency().name(), style);
        rowCount++;
        Row row5 = sheet.createRow(rowCount);
        createCell(row5, 1, "Sum to repay", style);
        createCell(row5, 2, credit.getSumToRepay() + " " + credit.getCard().getCurrency().name(), style);
        rowCount++;
        Row row6 = sheet.createRow(rowCount);
        createCell(row6, 1, "Amount of months", style);
        createCell(row6, 2, String.valueOf(credit.getMonths()), style);
        rowCount++;
        Row row7 = sheet.createRow(rowCount);
        createCell(row7, 1, "Percent", style);
        createCell(row7, 2, credit.getPercent() + "%", style);
        rowCount++;
        Row row8 = sheet.createRow(rowCount);
        createCell(row8, 1, "Next payment date", style);
        if (credit.isRepaid())
            createCell(row8, 2, "Credit is repaid.", style);
        else
            createCell(row8, 2, String.valueOf(credit.getNextPayment()), style);
        rowCount++;
        Row row9 = sheet.createRow(rowCount);
        createCell(row9, 1, "Current month", style);
        createCell(row9, 2, String.valueOf(credit.getCurrentMonth()), style);
        rowCount++;
        Row row10 = sheet.createRow(rowCount);
        createCell(row10, 1, "Current repaid sum", style);
        createCell(row10, 2, credit.getCurrentRepaidSum() + " " + credit.getCard().getCurrency().name(), style);
    }

    private void writeCreditsDataLines(List<Credit> credits) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Credit credit : credits) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(credit.getId()), style);
            createCell(row, columnCount++, String.valueOf(credit.getStartDate()), style);
            createCell(row, columnCount++, credit.getCard().getNumber(), style);
            createCell(row, columnCount++, credit.getCardReceivedSum() + " " + credit.getCard().getCurrency().name(), style);
            createCell(row, columnCount++, credit.getSumToRepay() + " " + credit.getCard().getCurrency().name(), style);
            createCell(row, columnCount++, String.valueOf(credit.getMonths()), style);
            createCell(row, columnCount++, credit.getPercent(), style);
            if (credit.isRepaid())
                createCell(row, columnCount++, "Credit is repaid.", style);
            else
                createCell(row, columnCount++, String.valueOf(credit.getNextPayment()), style);
            createCell(row, columnCount++, String.valueOf(credit.getCurrentMonth()), style);
            createCell(row, columnCount++, credit.getCurrentRepaidSum() + " " + credit.getCard().getCurrency().name(), style);
        }
    }

    private void writeDepositDataLines(Deposit deposit) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        int rowCount = 2;
        Row row1 = sheet.createRow(rowCount);
        createCell(row1, 1, "Id", style);
        createCell(row1, 2, String.valueOf(deposit.getId()), style);
        rowCount++;
        Row row2 = sheet.createRow(rowCount);
        createCell(row2, 1, "Start date", style);
        createCell(row2, 2, String.valueOf(deposit.getStartDate()), style);
        rowCount++;
        Row row3 = sheet.createRow(rowCount);
        createCell(row3, 1, "Card number", style);
        createCell(row3, 2, deposit.getCard().getNumber(), style);
        rowCount++;
        Row row4 = sheet.createRow(rowCount);
        createCell(row4, 1, "Payed sum", style);
        createCell(row4, 2, deposit.getCardPayedSum() + " " + deposit.getCard().getCurrency().name(), style);
        rowCount++;
        Row row5 = sheet.createRow(rowCount);
        createCell(row5, 1, "Sum to receive", style);
        createCell(row5, 2, deposit.getSumToReceive() + " " + deposit.getCard().getCurrency().name(), style);
        rowCount++;
        Row row6 = sheet.createRow(rowCount);
        createCell(row6, 1, "Amount of months", style);
        createCell(row6, 2, String.valueOf(deposit.getMonths()), style);
        rowCount++;
        Row row7 = sheet.createRow(rowCount);
        createCell(row7, 1, "Percent", style);
        createCell(row7, 2, deposit.getPercent() + "%", style);
        rowCount++;
        Row row8 = sheet.createRow(rowCount);
        createCell(row8, 1, "Next payment date", style);
        if (deposit.isRepaid())
            createCell(row8, 2, "Deposit is repaid.", style);
        else
            createCell(row8, 2, String.valueOf(deposit.getNextPayment()), style);
        rowCount++;
        Row row9 = sheet.createRow(rowCount);
        createCell(row9, 1, "Current month", style);
        createCell(row9, 2, String.valueOf(deposit.getCurrentMonth()), style);
        rowCount++;
        Row row10 = sheet.createRow(rowCount);
        createCell(row10, 1, "Current received sum", style);
        createCell(row10, 2, deposit.getCurrentReceivedSum() + " " + deposit.getCard().getCurrency().name(), style);
    }

    private void writeDepositsDataLines(List<Deposit> deposits) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Deposit deposit : deposits) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(deposit.getId()), style);
            createCell(row, columnCount++, String.valueOf(deposit.getStartDate()), style);
            createCell(row, columnCount++, deposit.getCard().getNumber(), style);
            createCell(row, columnCount++, deposit.getCardPayedSum() + " " + deposit.getCard().getCurrency().name(), style);
            createCell(row, columnCount++, deposit.getSumToReceive() + " " + deposit.getCard().getCurrency().name(), style);
            createCell(row, columnCount++, String.valueOf(deposit.getMonths()), style);
            createCell(row, columnCount++, deposit.getPercent(), style);
            if (deposit.isRepaid())
                createCell(row, columnCount++, "Deposit is repaid.", style);
            else
                createCell(row, columnCount++, String.valueOf(deposit.getNextPayment()), style);
            createCell(row, columnCount++, String.valueOf(deposit.getCurrentMonth()), style);
            createCell(row, columnCount++, deposit.getCurrentReceivedSum() + " " + deposit.getCard().getCurrency().name(), style);
        }
    }

    private void writeDayIncomeDataLines(IncomeReport report, String date) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        int rowCount = 2;
        Row row1 = sheet.createRow(rowCount);
        createCell(row1, 1, "Date", style);
        createCell(row1, 2, date, style);
        rowCount++;
        Row row2 = sheet.createRow(rowCount);
        createCell(row2, 1, "Total gain", style);
        createCell(row2, 2, report.getTotalGain() + " UAH", style);
        rowCount++;
        Row row3 = sheet.createRow(rowCount);
        createCell(row3, 1, "Total income", style);
        createCell(row3, 2, report.getTotalIncome() + " UAH", style);
        rowCount++;
        Row row4 = sheet.createRow(rowCount);
        createCell(row4, 1, "Total lose", style);
        createCell(row4, 2, report.getTotalLose() + " UAH", style);
        rowCount++;
        Row row5 = sheet.createRow(rowCount);
        createCell(row5, 1, "Commission income", style);
        createCell(row5, 2, report.getCommissionIncome() + " UAH", style);
        rowCount++;
        Row row6 = sheet.createRow(rowCount);
        createCell(row6, 1, "Penalty income", style);
        createCell(row6, 2, report.getPenaltyIncome() + " UAH", style);
        rowCount++;
        Row row7 = sheet.createRow(rowCount);
        createCell(row7, 1, "Credit income", style);
        createCell(row7, 2, report.getCreditIncome() + " UAH", style);
        rowCount++;
        Row row8 = sheet.createRow(rowCount);
        createCell(row8, 1, "Credit lose", style);
        createCell(row8, 2, report.getCreditLose() + " UAH", style);
        rowCount++;
        Row row9 = sheet.createRow(rowCount);
        createCell(row9, 1, "Deposit income", style);
        createCell(row9, 2, report.getDepositIncome() + " UAH", style);
        rowCount++;
        Row row10 = sheet.createRow(rowCount);
        createCell(row10, 1, "Deposit lose", style);
        createCell(row10, 2, report.getDepositLose() + " UAH", style);
    }

    private void writeDailyIncomesDataLines(List<IncomeReport> reports) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (IncomeReport report : reports) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(report.getDate()), style);
            createCell(row, columnCount++, report.getTotalGain() + " UAH", style);
            createCell(row, columnCount++, report.getTotalIncome() + " UAH", style);
            createCell(row, columnCount++, report.getTotalLose() + " UAH", style);
            createCell(row, columnCount++, report.getCommissionIncome() + " UAH", style);
            createCell(row, columnCount++, report.getPenaltyIncome() + " UAH", style);
            createCell(row, columnCount++, report.getCreditIncome() + " UAH", style);
            createCell(row, columnCount++, report.getCreditLose() + " UAH", style);
            createCell(row, columnCount++, report.getDepositIncome() + " UAH", style);
            createCell(row, columnCount++, report.getDepositLose() + " UAH", style);
        }
    }

    private void writeMonthlyIncomesDataLines(List<IncomeReport> reports) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (IncomeReport report : reports) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(report.getMonthYear()), style);
            createCell(row, columnCount++, report.getTotalGain() + " UAH", style);
            createCell(row, columnCount++, report.getTotalIncome() + " UAH", style);
            createCell(row, columnCount++, report.getTotalLose() + " UAH", style);
            createCell(row, columnCount++, report.getCommissionIncome() + " UAH", style);
            createCell(row, columnCount++, report.getPenaltyIncome() + " UAH", style);
            createCell(row, columnCount++, report.getCreditIncome() + " UAH", style);
            createCell(row, columnCount++, report.getCreditLose() + " UAH", style);
            createCell(row, columnCount++, report.getDepositIncome() + " UAH", style);
            createCell(row, columnCount++, report.getDepositLose() + " UAH", style);
        }
    }


    public void exportUser(HttpServletResponse response, User user) throws IOException {
        workbook = new XSSFWorkbook();
        writeHeaderLine("User");
        writeUserDataLines(user);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportUsers(HttpServletResponse response, List<User> users) throws IOException {
        workbook = new XSSFWorkbook();
        writeUsersHeaderLine();
        writeUsersDataLines(users);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportCard(HttpServletResponse response, Card card) throws IOException {
        workbook = new XSSFWorkbook();
        writeHeaderLine("Card");
        writeCardDataLines(card);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportCards(HttpServletResponse response, List<Card> cards) throws IOException {
        workbook = new XSSFWorkbook();
        writeCardsHeaderLine();
        writeCardsDataLines(cards);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportTransaction(HttpServletResponse response, Transaction transaction) throws IOException {
        workbook = new XSSFWorkbook();
        writeHeaderLine("Transaction");
        writeTransactionDataLines(transaction);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportTransactions(HttpServletResponse response, List<Transaction> transactions) throws IOException {
        workbook = new XSSFWorkbook();
        writeTransactionsHeaderLine();
        writeTransactionsDataLines(transactions);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportCredit(HttpServletResponse response, Credit credit) throws IOException {
        workbook = new XSSFWorkbook();
        writeHeaderLine("Credit");
        writeCreditDataLines(credit);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportCredits(HttpServletResponse response, List<Credit> credits) throws IOException {
        workbook = new XSSFWorkbook();
        writeCreditsHeaderLine();
        writeCreditsDataLines(credits);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportDeposit(HttpServletResponse response, Deposit deposit) throws IOException {
        workbook = new XSSFWorkbook();
        writeHeaderLine("Deposit");
        writeDepositDataLines(deposit);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportDeposits(HttpServletResponse response, List<Deposit> deposits) throws IOException {
        workbook = new XSSFWorkbook();
        writeDepositsHeaderLine();
        writeDepositsDataLines(deposits);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportDayIncome(HttpServletResponse response, IncomeReport report) throws IOException {
        workbook = new XSSFWorkbook();
        writeHeaderLine("Day income");
        writeDayIncomeDataLines(report, String.valueOf(report.getDate()));
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportDailyReports(HttpServletResponse response, List<IncomeReport> reports) throws IOException {
        workbook = new XSSFWorkbook();
        writeDailyReportsHeaderLine();
        writeDailyIncomesDataLines(reports);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportMonthlyReports(HttpServletResponse response, List<IncomeReport> reports) throws IOException {
        workbook = new XSSFWorkbook();
        writeDailyReportsHeaderLine();
        writeMonthlyIncomesDataLines(reports);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportMonthReport(HttpServletResponse response, IncomeReport monthReport, List<IncomeReport> reports) throws IOException {
        workbook = new XSSFWorkbook();
        writeHeaderLine("Month income");
        writeDayIncomeDataLines(monthReport, monthReport.getMonthYear());
        writeDailyReportsHeaderLine();
        writeDailyIncomesDataLines(reports);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
}
