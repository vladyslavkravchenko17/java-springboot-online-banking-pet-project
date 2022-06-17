package com.project.repository;

import com.project.domain.Card;
import com.project.domain.PlannedPayment;
import com.project.repository.rowmapper.CardRowMapper;
import com.project.repository.rowmapper.PlannedPaymentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CardRepository {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CardRepository(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Card findByNumber(String number) {
        String sql = "SELECT * FROM card WHERE number = ?";
        try {
            Card card = jdbcTemplate.queryForObject(sql, new Object[]{number}, new CardRowMapper());
            return card;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Card findById(long id) {
        String sql = "SELECT * FROM card WHERE card_id = ?";
        try {
            Card card = jdbcTemplate.queryForObject(sql, new Object[]{id}, new CardRowMapper());
            return card;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Card findByCompanyName(String companyName) {
        String sql = "SELECT * FROM card WHERE company_name = ?";
        try {
            Card card = jdbcTemplate.queryForObject(sql, new Object[]{companyName}, new CardRowMapper());
            return card;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Card> findAll() {
        List<Card> cards;
        String sql = "SELECT * FROM card WHERE NOT type = 'BANK_ACCOUNT' ORDER BY card_id DESC";
        cards = jdbcTemplate.query(sql, new CardRowMapper());

        return cards;
    }

    public List<PlannedPayment> findAllPlannedPayments() {
        List<PlannedPayment> plannedPayments;
        String sql = "SELECT * FROM planned_payments AS p " +
                "LEFT JOIN card AS c1 ON p.sender_id = c1.card_id " +
                "LEFT JOIN card AS c2 ON p.receiver_id = c2.card_id ";
        plannedPayments = jdbcTemplate.query(sql, new PlannedPaymentRowMapper());

        return plannedPayments;
    }

    public List<PlannedPayment> findPlannedPaymentsByNumber(long id) {
        List<PlannedPayment> plannedPayments;
        String sql = "SELECT * FROM planned_payments AS p " +
                "LEFT JOIN card AS c1 ON p.sender_id = c1.card_id " +
                "LEFT JOIN card AS c2 ON p.receiver_id = c2.card_id " +
                " WHERE sender_id = ?";
        plannedPayments = jdbcTemplate.query(sql, new Object[]{id}, new PlannedPaymentRowMapper());


        return plannedPayments;
    }

    public PlannedPayment findPlannedPaymentById(long id) {
        String sql = "SELECT * FROM planned_payments AS p " +
                "LEFT JOIN card AS c1 ON p.sender_id = c1.card_id " +
                "LEFT JOIN card AS c2 ON p.receiver_id = c2.card_id " +
                " WHERE id = ?";
        try {
            PlannedPayment payment = jdbcTemplate.queryForObject(sql, new Object[]{id}, new PlannedPaymentRowMapper());
            return payment;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void savePlannedPayment(PlannedPayment plannedPayment) {
        String sql = "UPDATE planned_payments SET " +
                "sender_id = :sender_id, receiver_id = :receiver_id, description = :description, sum = :sum, " +
                "is_repeatable = :is_repeatable, date_time = :date_time, period_in_days = :period_in_days WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", plannedPayment.getId());
        params.put("description", plannedPayment.getDescription());
        params.put("sender_id", plannedPayment.getSender().getId());
        params.put("receiver_id", plannedPayment.getReceiver().getId());
        params.put("sum", plannedPayment.getSum());
        params.put("is_repeatable", plannedPayment.isRepeatable());
        LocalDateTime localDateTime = plannedPayment.getPaymentDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        params.put("date_time", localDateTime.format(formatter));
        params.put("period_in_days", plannedPayment.getPeriod());

        namedJdbcTemplate.update(sql, params);
    }

    public void createPlannedPayment(PlannedPayment plannedPayment) {
        String sql = "INSERT INTO planned_payments (id, description, sender_id, receiver_id, sum, date_time, is_repeatable, " +
                "period_in_days) VALUES (:id, :description, :sender_id, :receiver_id, :sum, :date_time, :is_repeatable, " +
                ":period_in_days)";
        Map<String, Object> params = new HashMap<>();
        params.put("id", generateId("planned_payments", "id"));
        params.put("description", plannedPayment.getDescription());
        params.put("sender_id", plannedPayment.getSender().getId());
        params.put("receiver_id", plannedPayment.getReceiver().getId());
        params.put("sum", plannedPayment.getSum());
        params.put("is_repeatable", plannedPayment.isRepeatable());
        LocalDateTime localDateTime = plannedPayment.getPaymentDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String timeFormat = localDateTime.format(formatter);
        params.put("date_time", timeFormat);
        if (plannedPayment.isRepeatable()) {
            params.put("period_in_days", plannedPayment.getPeriod());
        } else {
            params.put("period_in_days", 0);
        }

        namedJdbcTemplate.update(sql, params);
    }

    public void deletePlannedPayment(long id) {
        String sql = "DELETE FROM planned_payments WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        namedJdbcTemplate.update(sql, params);
    }

    public void create(Card card) {
        String sql = "INSERT INTO card (card_id, user_id, number, balance, currency, type, company_name, credit_limit, min_credit) " +
                "VALUES (:card_id, :user_id, :number , :balance, :currency, :type, :company_name, :credit_limit, :min_credit)";
        Map<String, Object> params = new HashMap<>();
        params.put("card_id", generateId("card", "card_id"));
        params.put("user_id", card.getUserId());
        params.put("number", generateUniqueNumber());
        params.put("balance", 0);
        params.put("currency", card.getCurrency().name());
        params.put("credit_limit", card.getLimit());
        params.put("min_credit", card.getMinCreditSum());
        params.put("type", card.getType().name());
        if (card.getCompanyName() != null) {
            params.put("company_name", card.getCompanyName());
        } else {
            params.put("company_name", null);
        }


        namedJdbcTemplate.update(sql, params);
    }

    public void save(Card card) {
        String sql = "UPDATE card SET balance = :balance, currency = :currency, credit_limit = :credit_limit " +
                "WHERE card_id = :card_id";
        Map<String, Object> params = new HashMap<>();
        params.put("card_id", card.getId());
        params.put("balance", card.getBalance());
        params.put("currency", card.getCurrency().name());
        params.put("credit_limit", card.getLimit());

        namedJdbcTemplate.update(sql, params);
    }

    private String generateCardNumber() {
        String newNumber = "499217";
        newNumber += 100000009 + (int) (Math.random() * 999999999);
        newNumber += "4";

        return newNumber;
    }

    private String generateUniqueNumber() {
        String uniqueNumber;
        do {
            uniqueNumber = generateCardNumber();
        } while (findByNumber(uniqueNumber) != null);

        return uniqueNumber;
    }

    private long generateId(String table, String columnId) {
        String sql = "SELECT MAX(" + columnId + ") FROM " + table;
        Long currentId;
        currentId = jdbcTemplate.queryForObject(sql, Long.class);
        if (currentId != null) return currentId + 1;
        else return 1;
    }
}
