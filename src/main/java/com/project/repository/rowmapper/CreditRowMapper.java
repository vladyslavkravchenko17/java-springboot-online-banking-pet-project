package com.project.repository.rowmapper;

import com.project.domain.Card;
import com.project.domain.Credit;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreditRowMapper implements RowMapper<Credit> {

    @Override
    public Credit mapRow(ResultSet rs, int rowNum) throws SQLException {
        Credit credit = new Credit();

        credit.setId(rs.getLong("id"));
        credit.setCardReceivedSum(rs.getDouble("received_sum"));
        credit.setMonths(rs.getInt("months"));
        credit.setPercent(rs.getInt("percent"));
        credit.setSumPerMonth((rs.getDouble("sum_per_month")));
        credit.setSumToRepay(rs.getDouble("sum_to_repay"));
        credit.setCurrentRepaidSum(rs.getDouble("current_repaid_sum"));
        credit.setCurrentMonth(rs.getInt("current_month"));
        String startDate = rs.getString("start_date");
        String nextPayment = rs.getString("next_payment");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        credit.setNextPayment(LocalDate.parse(nextPayment, formatter));
        credit.setStartDate(LocalDate.parse(startDate, formatter));
        credit.setRepaid(rs.getBoolean("is_repaid"));
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
        credit.setCard(card);



        return credit;
    }
}
