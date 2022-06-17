package com.project.repository.rowmapper;

import com.project.domain.Card;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccountRowMapper implements RowMapper<Card> {

    @Override
    public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
        Card card = new Card();
        card.setId(rs.getLong("id"));
        card.setUserId(null);
        card.setNumber(rs.getString("number"));
        card.setBalance(rs.getInt("balance"));
        card.setCurrency(rs.getString("currency"));

        return card;
    }
}
