package com.project.repository.rowmapper;

import com.project.domain.Card;
import com.project.domain.Deposit;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DepositRowMapper implements RowMapper<Deposit> {

    @Override
    public Deposit mapRow(ResultSet rs, int rowNum) throws SQLException {
        Deposit deposit = new Deposit();

        deposit.setId(rs.getLong("id"));
        deposit.setCardPayedSum(rs.getDouble("card_payed_sum"));
        deposit.setMonths(rs.getInt("months"));
        deposit.setPercent(rs.getInt("percent"));
        deposit.setSumPerMonth((rs.getDouble("sum_per_month")));
        deposit.setSumToReceive(rs.getDouble("sum_to_receive"));
        deposit.setCurrentReceivedSum(rs.getDouble("current_received_sum"));
        deposit.setCurrentMonth(rs.getInt("current_month"));
        String startDate = rs.getString("start_date");
        String nextPayment = rs.getString("next_payment");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        deposit.setNextPayment(LocalDate.parse(nextPayment, formatter));
        deposit.setStartDate(LocalDate.parse(startDate, formatter));
        deposit.setRepaid(rs.getBoolean("is_repaid"));
        Card card = new Card();
        card.setId(rs.getLong("card_id"));
        card.setUserId(rs.getLong("user_id"));
        card.setNumber(rs.getString("number"));
        card.setBalance(rs.getDouble("balance"));
        card.setCurrency(rs.getString("currency"));
        card.setType(rs.getString("type"));
        card.setCompanyName(rs.getString("company_name"));
        card.setLimit(rs.getInt("credit_limit"));
        card.setMinCreditSum(rs.getInt("min_credit"));
        deposit.setCard(card);


        return deposit;
    }
}
