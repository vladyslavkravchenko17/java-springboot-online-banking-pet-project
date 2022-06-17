package com.project.repository;

import com.project.domain.Subscription;
import com.project.domain.SubscriptionInfo;
import com.project.repository.rowmapper.SubscriptionInfoRowMapper;
import com.project.repository.rowmapper.SubscriptionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SubscriptionRepository {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SubscriptionRepository(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Subscription> findAll() {
        String sql = "SELECT * FROM subscription LEFT JOIN subscription_info ON info_id = subscription_info_id " +
                "LEFT JOIN card ON card_id = subscription_card_id";
        List<Subscription> subscriptions = jdbcTemplate.query(sql, new SubscriptionRowMapper());
        return subscriptions;
    }

    public List<Subscription> findByUserId(Long id) {
        String sql = "SELECT * FROM subscription LEFT JOIN subscription_info ON info_id = subscription_info_id " +
                "LEFT JOIN card ON card_id = subscription_card_id WHERE subscription_user_id = ?";
        List<Subscription> subscriptions = jdbcTemplate.query(sql, new Object[]{id}, new SubscriptionRowMapper());
        return subscriptions;
    }

    public Subscription findById(long id) {
        String sql = "SELECT * FROM subscription LEFT JOIN subscription_info ON info_id = subscription_info_id " +
                "LEFT JOIN card ON card_id = subscription_card_id WHERE id = ?";
        try {
            Subscription subscription = jdbcTemplate.queryForObject(sql, new Object[]{id}, new SubscriptionRowMapper());
            return subscription;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<SubscriptionInfo> findAllInfo(){
        List<SubscriptionInfo> infos;
        String sql = "SELECT * FROM subscription_info ORDER BY sum_per_month";
        infos = jdbcTemplate.query(sql, new SubscriptionInfoRowMapper());
        return infos;
    }


    public SubscriptionInfo findInfoById(long id) {
            String sql = "SELECT * FROM subscription_info WHERE subscription_info_id = ?";
            try {
                SubscriptionInfo info = jdbcTemplate.queryForObject(sql, new Object[]{id}, new SubscriptionInfoRowMapper());
                return info;
            } catch (EmptyResultDataAccessException e) {
                return null;
            }
    }

    public void createSubscription(Subscription subscription){
        String sql = "INSERT INTO subscription (id, subscription_user_id, subscription_card_id, info_id, end_date, repeatable) " +
                "VALUES (:id, :subscription_user_id, :subscription_card_id, :info_id, :end_date, :repeatable)";

        Map<String, Object> params = new HashMap<>();
        params.put("id", generateId("subscription", "id"));
        params.put("subscription_user_id", subscription.getUserId());
        params.put("subscription_card_id", subscription.getCard().getId());
        params.put("info_id", subscription.getServiceId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        params.put("end_date", formatter.format(subscription.getEndDate()));
        params.put("repeatable", subscription.isRepeatable());

        namedJdbcTemplate.update(sql, params);
    }

    public void saveSubscription(Subscription subscription) {
        String sql = "UPDATE subscription SET subscription_card_id = :subscription_card_id, repeatable = :repeatable WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("subscription_card_id", subscription.getCard().getId());
        params.put("repeatable", subscription.isRepeatable());
        params.put("id", subscription.getId());

        namedJdbcTemplate.update(sql, params);
    }

    public void deleteSubscription(long id){
        String sql = "DELETE FROM subscription WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        namedJdbcTemplate.update(sql, params);
    }

    private long generateId(String table, String columnId) {
        String sql = "SELECT MAX(" + columnId + ") FROM " + table;
        Long currentId;
        currentId = jdbcTemplate.queryForObject(sql, Long.class);
        if (currentId != null) return currentId + 1;
        else return 1;
    }

}
