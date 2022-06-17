package com.project.repository.rowmapper;

import com.project.domain.Card;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardRowMapper implements RowMapper<Card> {

    @Override
    public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
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

        return card;
    }
}
