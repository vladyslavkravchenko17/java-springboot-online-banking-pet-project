package com.project.repository.rowmapper;

import com.project.domain.Card;
import com.project.domain.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionRowMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(rs.getLong("id"));
        transaction.setPayedSum(rs.getDouble("payed_sum"));
        transaction.setReceivedSum(rs.getDouble("received_sum"));
        transaction.setCommissionPercent(rs.getInt("commission_percent"));
        transaction.setCommission(rs.getDouble("commission"));
        transaction.setComment(rs.getString("comment"));
        transaction.setConversionType(rs.getString("conversion_type"));
        transaction.setCurrentConversionRate(rs.getDouble("current_conversion_rate"));
        transaction.setType(rs.getString("type"));
        String date = rs.getString("date_time");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        transaction.setDateTime(LocalDateTime.parse(date, formatter));
        Card card1 = new Card();
        card1.setUserId(rs.getLong(13));
        card1.setNumber(rs.getString(14));
        card1.setBalance(rs.getDouble(15));
        card1.setCurrency(rs.getString(16));
        card1.setId(rs.getLong(17));
        card1.setType(rs.getString(18));
        card1.setCompanyName(rs.getString(19));
        card1.setLimit(rs.getInt(20));
        card1.setMinCreditSum(rs.getInt(21));
        transaction.setSender(card1);
        Card card2 = new Card();
        card2.setUserId(rs.getLong(22));
        card2.setNumber(rs.getString(23));
        card2.setBalance(rs.getDouble(24));
        card2.setCurrency(rs.getString(25));
        card2.setId(rs.getLong(26));
        card2.setType(rs.getString(27));
        card2.setCompanyName(rs.getString(28));
        card2.setLimit(rs.getInt(29));
        card2.setMinCreditSum(rs.getInt(30));
        transaction.setReceiver(card2);

        return transaction;
    }
}
