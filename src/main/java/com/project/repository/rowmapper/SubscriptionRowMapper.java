package com.project.repository.rowmapper;

import com.project.domain.Card;
import com.project.domain.Subscription;
import com.project.domain.SubscriptionInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionRowMapper implements RowMapper<Subscription> {

    @Override
    public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setId(rs.getLong("id"));
        subscription.setUserId(rs.getLong("user_id"));
        subscription.setRepeatable(rs.getBoolean("repeatable"));
        subscription.setEndDate(rs.getString("end_date"));
        SubscriptionInfo info = new SubscriptionInfo();
        info.setId(rs.getLong("subscription_info_id"));
        info.setName(rs.getString("name"));
        info.setDescription(rs.getString("description"));
        info.setSumPerMonth(rs.getDouble("sum_per_month"));
        info.setImageUrl(rs.getString("image_url"));
        subscription.setSubscriptionInfo(info);
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
        subscription.setCard(card);

        return subscription;
    }
}
