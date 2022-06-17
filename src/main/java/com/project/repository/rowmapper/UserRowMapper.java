package com.project.repository.rowmapper;

import com.project.domain.Card;
import com.project.domain.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setActive(rs.getBoolean("active"));
        user.setRole(rs.getString("role"));
        user.setActivationCode(rs.getString("activation_code"));
        List<Card> cards = new ArrayList<>();
        user.setCards(cards);

        return user;
    }
}