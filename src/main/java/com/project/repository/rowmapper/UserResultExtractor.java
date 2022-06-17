package com.project.repository.rowmapper;

import com.project.domain.Card;
import com.project.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserResultExtractor implements ResultSetExtractor<List<User>> {

    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, User> map = new HashMap<>();

        while (rs.next()) {
            Long id = rs.getLong("id");
            User user = map.get(id);
            if (user == null) {
                user = new User();
                user.setId(id);
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setActive(rs.getBoolean("active"));
                user.setRole(rs.getString("role"));
                user.setActivationCode(rs.getString("activation_code"));

                map.put(id, user);
            }

            String number = rs.getString("number");
            if (number != null) {
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
                user.addCard(card);
            }
        }
        return new ArrayList<>(map.values());
    }
}
