package com.project.repository;

import com.project.domain.Credit;
import com.project.domain.Deposit;
import com.project.repository.rowmapper.CreditRowMapper;
import com.project.repository.rowmapper.DepositRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CreditDepositRepository {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CreditDepositRepository(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Credit findCreditById(long id) {
        String sql = "SELECT * FROM credit LEFT JOIN card ON credit_card_id = card_id WHERE id = ?";
        try {
            Credit credit = jdbcTemplate.queryForObject(sql, new Object[]{id}, new CreditRowMapper());
            return credit;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Deposit findDepositById(long id) {
        String sql = "SELECT * FROM deposit LEFT JOIN card ON deposit_card_id = card_id WHERE id = ?";
        try {
            Deposit deposit = jdbcTemplate.queryForObject(sql, new Object[]{id}, new DepositRowMapper());
            return deposit;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Credit> findAllCreditsByNumber(String cardNumber) {
        List<Credit> credits;
        String sql = "SELECT * FROM credit LEFT JOIN card ON credit_card_id = card_id WHERE number = ? ORDER BY is_repaid";
        credits = jdbcTemplate.query(sql, new Object[]{cardNumber}, new CreditRowMapper());

        return credits;
    }

    public List<Credit> findAllCredits() {
        List<Credit> credits;
        String sql = "SELECT * FROM credit LEFT JOIN card ON credit_card_id = card_id ORDER BY id DESC";
        credits = jdbcTemplate.query(sql, new CreditRowMapper());

        return credits;
    }

    public List<Deposit> findDepositsByDate(String date) {
        String sql = "SELECT * FROM deposit LEFT JOIN card ON deposit_card_id = card_id WHERE start_date = ?";
        List<Deposit> deposits = jdbcTemplate.query(sql, new Object[]{date}, new DepositRowMapper());
        return deposits;
    }

    public List<Credit> findCreditsByDate(String date) {
        String sql = "SELECT * FROM credit LEFT JOIN card ON credit_card_id = card_id WHERE start_date = ?";
        List<Credit> credits = jdbcTemplate.query(sql, new Object[]{date}, new CreditRowMapper());
        return credits;
    }

    public void save(Credit credit) {
        String sql = "UPDATE credit SET current_repaid_sum = :current_repaid_sum, current_month = :current_month, " +
                "next_payment = :next_payment, is_repaid = :is_repaid WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", credit.getId());
        params.put("current_repaid_sum", credit.getCurrentRepaidSum());
        params.put("current_month", credit.getCurrentMonth());
        params.put("is_repaid", credit.isRepaid());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (credit.isRepaid()){
            params.put("next_payment", credit.getNextPayment().minusMonths(1).format(formatter));
        } else {
            params.put("next_payment", credit.getNextPayment().format(formatter));
        }

        namedJdbcTemplate.update(sql, params);
    }

    public void create(Credit credit) {
        String sql = "INSERT INTO credit (id, credit_card_id, received_sum, months, percent, sum_per_month, sum_to_repay, " +
                "current_repaid_sum, current_month, start_date, next_payment, is_repaid) " +
                "VALUES (:id, :credit_card_id, :received_sum, :months, :percent, :sum_per_month, :sum_to_repay, " +
                ":current_repaid_sum, :current_month, :start_date, :next_payment, :is_repaid)";

        Map<String, Object> params = new HashMap<>();
        params.put("id", generateId("credit", "id"));
        params.put("credit_card_id", credit.getCard().getId());
        params.put("received_sum", credit.getCardReceivedSum());
        params.put("months", credit.getMonths());
        params.put("percent", credit.getPercent());
        double sumToRepay = credit.getCardReceivedSum() * credit.getPercent() / 100 + credit.getCardReceivedSum();
        params.put("sum_to_repay", sumToRepay);
        params.put("sum_per_month", sumToRepay / credit.getMonths());
        params.put("current_repaid_sum", 0);
        params.put("current_month", 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        params.put("start_date", LocalDate.now().format(formatter));
        params.put("next_payment", LocalDate.now().plusMonths(1).format(formatter));
        params.put("is_repaid", false);

        namedJdbcTemplate.update(sql, params);
    }

    public List<Deposit> findAllDepositsByNumber(String cardNumber) {
        List<Deposit> deposits;
        String sql = "SELECT * FROM deposit LEFT JOIN card ON deposit_card_id = card_id WHERE number = ? ORDER BY is_repaid";
        deposits = jdbcTemplate.query(sql, new Object[]{cardNumber}, new DepositRowMapper());

        return deposits;
    }

    public List<Deposit> findRepaidDeposits() {
        String sql = "SELECT * FROM deposit LEFT JOIN card ON deposit_card_id = card_id WHERE is_repaid = ?";
        List<Deposit> deposits = jdbcTemplate.query(sql, new Object[]{true}, new DepositRowMapper());
        return deposits;
    }

    public List<Credit> findRepaidCredits() {
        String sql = "SELECT * FROM credit LEFT JOIN card ON credit_card_id = card_id WHERE is_repaid = ?";
        List<Credit> credits = jdbcTemplate.query(sql, new Object[]{true}, new CreditRowMapper());
        return credits;
    }

    public List<Deposit> findAllDeposits() {
        List<Deposit> deposits;
        String sql = "SELECT * FROM deposit LEFT JOIN card ON deposit_card_id = card_id ORDER BY id DESC";
        deposits = jdbcTemplate.query(sql, new DepositRowMapper());

        return deposits;
    }

    public void save(Deposit deposit) {
        String sql = "UPDATE deposit SET current_received_sum = :current_received_sum, current_month = :current_month, " +
                "next_payment = :next_payment, is_repaid = :is_repaid WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", deposit.getId());
        params.put("current_received_sum", deposit.getCurrentReceivedSum());
        params.put("current_month", deposit.getCurrentMonth());
        params.put("is_repaid", deposit.isRepaid());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (deposit.isRepaid()){
            params.put("next_payment", deposit.getNextPayment().minusMonths(1).format(formatter));
        } else {
            params.put("next_payment", deposit.getNextPayment().format(formatter));
        }

        namedJdbcTemplate.update(sql, params);
    }

    public void create(Deposit deposit) {
        String sql = "INSERT INTO deposit (id, deposit_card_id, card_payed_sum, months, percent, sum_per_month, sum_to_receive, " +
                "current_received_sum, current_month, start_date, next_payment, is_repaid) " +
                "VALUES (:id, :deposit_card_id, :card_payed_sum, :months, :percent, :sum_per_month, :sum_to_receive, " +
                ":current_received_sum, :current_month, :start_date, :next_payment, :is_repaid)";

        Map<String, Object> params = new HashMap<>();
        params.put("id", generateId("deposit", "id"));
        params.put("deposit_card_id", deposit.getCard().getId());
        params.put("card_payed_sum", deposit.getCardPayedSum());
        params.put("months", deposit.getMonths());
        params.put("percent", deposit.getPercent());
        double sumToReceive = deposit.getCardPayedSum() * deposit.getPercent() / 100 + deposit.getCardPayedSum();
        params.put("sum_to_receive", sumToReceive);
        params.put("sum_per_month", sumToReceive / deposit.getMonths());
        params.put("current_received_sum", 0);
        params.put("current_month", 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        params.put("start_date", LocalDate.now().format(formatter));
        params.put("next_payment", LocalDate.now().plusMonths(1).format(formatter));
        params.put("is_repaid", false);

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
