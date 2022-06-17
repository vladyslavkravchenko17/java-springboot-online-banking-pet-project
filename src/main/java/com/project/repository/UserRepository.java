package com.project.repository;

import com.project.domain.User;
import com.project.repository.rowmapper.UserResultExtractor;
import com.project.repository.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User findById(long id) {
        String sql = "SELECT * FROM users LEFT JOIN card ON user_id = id WHERE id = ?";
        try {
            List<User> query = jdbcTemplate.query(sql, new UserResultExtractor(), id);
            User user = query.get(0);
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users LEFT JOIN card ON user_id = id WHERE email = ?";
        try {
            List<User> query = jdbcTemplate.query(sql, new UserResultExtractor(), email);
            if (!query.isEmpty()){
                User user = query.get(0);
                return user;
            }
            return null;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User findByActivationCode(String code) {
        String sql = "SELECT * FROM users WHERE activation_code = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{code}, new UserRowMapper());
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<User> findAll() {
        List<User> users;
        String sql = "SELECT * FROM users LEFT JOIN card ON user_id = id WHERE id > -1 ORDER BY id";
        users = jdbcTemplate.query(sql, new UserResultExtractor());
        return users;
    }

    public void createUser(User user) {
        String sql = "INSERT INTO users (id, first_name, last_name, email, password, active, role, activation_code)" +
                "VALUES (:id, :first_name, :last_name, :email, :password, :active, :role, :activation_code)";
        long id = generateId();
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("first_name", user.getFirstName());
        params.put("last_name", user.getLastName());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("active", user.isActive());
        params.put("role", user.getRole().name());
        params.put("activation_code", user.getActivationCode());
        namedJdbcTemplate.update(sql, params);
        user.setId(id);
    }

    public void save(User user) {
        String sql = "UPDATE users SET first_name = :first_name, last_name = :last_name, email = :email, password = :password," +
                "active = :active, role = :role, activation_code = :activation_code WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", user.getId());
        params.put("first_name", user.getFirstName());
        params.put("last_name", user.getLastName());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("active", user.isActive());
        params.put("role", user.getRole().name());
        params.put("activation_code", user.getActivationCode());
        namedJdbcTemplate.update(sql, params);
    }

    private long generateId() {
        String sql = "SELECT MAX(id) FROM users";
        Long currentId;
        currentId = jdbcTemplate.queryForObject(sql, Long.class);
        if (currentId != null) return currentId + 1;
        else return 1;
    }
}
