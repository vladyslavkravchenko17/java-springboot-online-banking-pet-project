package com.project.repository.rowmapper;

import com.project.domain.Card;
import com.project.domain.PlannedPayment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlannedPaymentRowMapper implements RowMapper<PlannedPayment> {
    @Override
    public PlannedPayment mapRow(ResultSet rs, int rowNum) throws SQLException {
        PlannedPayment payment = new PlannedPayment();
        payment.setId(rs.getLong("id"));
        payment.setSum(rs.getDouble("sum"));
        payment.setDescription(rs.getString("description"));
        payment.setRepeatable(rs.getBoolean("is_repeatable"));
        payment.setPeriod(rs.getInt("period_in_days"));
        String dateTimeString = rs.getString("date_time");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        payment.setPaymentDateTime(dateTime);
        Card card1 = new Card();
        card1.setUserId(rs.getLong(9));
        card1.setNumber(rs.getString(10));
        card1.setBalance(rs.getDouble(11));
        card1.setCurrency(rs.getString(12));
        card1.setId(rs.getLong(13));
        card1.setType(rs.getString(14));
        card1.setCompanyName(rs.getString(15));
        card1.setLimit(rs.getInt(16));
        card1.setMinCreditSum(rs.getInt(17));
        payment.setSender(card1);
        Card card2 = new Card();
        card2.setUserId(rs.getLong(18));
        card2.setNumber(rs.getString(19));
        card2.setBalance(rs.getDouble(20));
        card2.setCurrency(rs.getString(21));
        card2.setId(rs.getLong(22));
        card2.setType(rs.getString(23));
        card2.setCompanyName(rs.getString(24));
        card2.setLimit(rs.getInt(25));
        card2.setMinCreditSum(rs.getInt(26));
        payment.setReceiver(card2);

        return payment;
    }
}
