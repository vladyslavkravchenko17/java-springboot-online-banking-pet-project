package com.project.repository;

import com.project.domain.Card;
import com.project.domain.Transaction;
import com.project.domain.User;
import com.project.repository.rowmapper.TransactionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Repository
public class TransactionRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    public TransactionRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void create(Transaction transaction) {
        String sql = "INSERT INTO transaction (id, sender_id, receiver_id, comment, payed_sum, received_sum," +
                "commission_percent, commission, conversion_type, current_conversion_rate, type, date_time)" +
                "VALUES (:id, :sender_id, :receiver_id, :comment, :payed_sum, :received_sum, :commission_percent," +
                ":commission, :conversion_type, :current_conversion_rate, :type, :date_time)";
        long id = generateId();
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("sender_id", transaction.getSender().getId());
        params.put("receiver_id", transaction.getReceiver().getId());
        params.put("comment", transaction.getComment());
        params.put("payed_sum", transaction.getPayedSum());
        params.put("commission_percent", transaction.getCommissionPercent());
        params.put("conversion_type", transaction.getConversionType().name());
        params.put("current_conversion_rate", transaction.getCurrentConversionRate());
        params.put("type", transaction.getType().name());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        params.put("date_time", LocalDateTime.now().format(formatter));
        params.put("received_sum", transaction.getReceivedSum());
        params.put("commission", transaction.getCommission());

        jdbcTemplate.update(sql, params);
    }


    public Transaction findById(long id) {
        String sql = "SELECT * FROM transaction AS t " +
                "LEFT JOIN card AS c1 ON t.sender_id = c1.card_id " +
                "LEFT JOIN card AS c2 ON t.receiver_id = c2.card_id WHERE id = ?";
        Transaction transaction = jdbcTemplate.getJdbcOperations().queryForObject(sql, new Object[]{id}, new TransactionRowMapper());

        return transaction;
    }

    public List<Transaction> findAll() {
        String sql = "SELECT * FROM transaction AS t " +
                "LEFT JOIN card AS c1 ON t.sender_id = c1.card_id " +
                "LEFT JOIN card AS c2 ON t.receiver_id = c2.card_id ORDER BY id DESC";
        List<Transaction> transactions = jdbcTemplate.query(sql, new TransactionRowMapper());

        return transactions;
    }

    public List<Transaction> findAllByNumber(String number) {
        Card currentCard = cardRepository.findByNumber(number);
        if (currentCard != null) {
            List<Transaction> transactions = new ArrayList<>();
            transactions.addAll(findBySender(currentCard.getNumber()));
            transactions.addAll(findByReceiver(currentCard.getNumber()));
            Collections.sort(transactions, Collections.reverseOrder());
            return transactions;
        }
        return null;
    }

    public List<Transaction> findBySender(String senderNumber) {
        List<Transaction> transactions;
        String sql = "SELECT * FROM transaction AS t " +
                "LEFT JOIN card AS c1 ON t.sender_id = c1.card_id " +
                "LEFT JOIN card AS c2 ON t.receiver_id = c2.card_id WHERE c1.number = ? ORDER BY t.id DESC";
        try {
            transactions = jdbcTemplate.getJdbcOperations().query(sql, new Object[]{senderNumber}, new TransactionRowMapper());
            transactions.forEach(transaction -> {
                String name;
                Card card = transaction.getReceiver();
                if (card != null) {
                    if (card.getCompanyName() == null) {
                        User user = userRepository.findById(card.getUserId());
                        name = user.getFirstName() + " " + user.getLastName();
                        if (user.getFirstName() == null)
                        name = "";
                    } else {
                        name = card.getCompanyName();
                    }
                } else name = null;
                transaction.setReceived(false);
                transaction.setPartnerName(name);
            });
            return transactions;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Transaction> findByReceiver(String receiverNumber) {
        List<Transaction> transactions;
        String sql = "SELECT * FROM transaction AS t " +
                "LEFT JOIN card AS c1 ON t.sender_id = c1.card_id " +
                "LEFT JOIN card AS c2 ON t.receiver_id = c2.card_id WHERE c2.number = ? ORDER BY id DESC";
        try {
            transactions = jdbcTemplate.getJdbcOperations().query(sql, new Object[]{receiverNumber}, new TransactionRowMapper());
            transactions.forEach(transaction -> {
                String name;
                Card card = transaction.getSender();
                if (card != null) {
                    if (card.getCompanyName() == null) {
                        User user = userRepository.findById(card.getUserId());
                        name = user.getFirstName() + " " + user.getLastName();
                        if (user.getFirstName() == null)
                            name = "";
                    } else {
                        name = card.getCompanyName();
                    }
                } else name = null;
                transaction.setPartnerName(name);
                transaction.setReceived(true);
            });
            return transactions;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    private long generateId() {
        String sql = "SELECT MAX(id) FROM transaction";
        Long currentId;
        currentId = jdbcTemplate.getJdbcOperations().queryForObject(sql, Long.class);
        if (currentId != null) return currentId + 1;
        else return 1;
    }
}