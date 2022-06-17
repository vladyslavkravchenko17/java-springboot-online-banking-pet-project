package com.project.repository;

import com.project.domain.Card;
import com.project.domain.IncomeReport;
import com.project.repository.rowmapper.CardRowMapper;
import com.project.repository.rowmapper.IncomeReportRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Repository
public class BankAccountRepository {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BankAccountRepository(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<IncomeReport> findAllReports(){
        String sql = "SELECT * FROM income_report ORDER BY date";
        try {
            List<IncomeReport> incomeReports = jdbcTemplate.query(sql, new IncomeReportRowMapper());
            return incomeReports;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<IncomeReport> findAllMonthReports() {
        List<IncomeReport> reports = new ArrayList<>();
        Date min = findMinDate();
        Date max = findMaxDate();
        min.setDate(1);
        max.setDate(2);
        while (min.before(max)) {
            LocalDate localMin = min.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            IncomeReport report = findMonthReport(localMin.getMonthValue(), localMin.getYear());
            String month = String.valueOf(localMin.getMonthValue());
            if (localMin.getMonthValue() < 10){
                month = 0 + month;
            }
            report.setMonthYear(localMin.getYear() + "-" + month);
            reports.add(report);
            min.setMonth(min.getMonth() + 1);
        }

        return reports;
    }

    public List<IncomeReport> findIncomesByMonth(int month, int year) {
        String sql = "SELECT * FROM income_report WHERE EXTRACT(MONTH FROM income_report.date) = :month " +
                "AND EXTRACT(YEAR FROM income_report.date) = :year ORDER BY id";
        try {
            List<IncomeReport> reports = namedJdbcTemplate.query(sql,
                    new MapSqlParameterSource()
                            .addValue("month", month)
                            .addValue("year", year)
                    , new IncomeReportRowMapper());
            return reports;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public IncomeReport findIncomeByDate(String date) {
        String sql = "SELECT * FROM income_report WHERE date = ?";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date dateFormat = formatter.parse(date);
            IncomeReport incomeReport = jdbcTemplate.queryForObject(sql, new Object[]{dateFormat}, new IncomeReportRowMapper());
            return incomeReport;
        } catch (EmptyResultDataAccessException | ParseException e) {
            return null;
        }
    }

    public void createReport(LocalDate date) {
        String sql = "INSERT into income_report (id, date, commission_income, credit_income, deposit_income, penalty_income, " +
                "deposit_lose, credit_lose) VALUES (:id, :date, :commission_income, :credit_income, :deposit_income, " +
                ":penalty_income, :deposit_lose, :credit_lose)";
        Map<String, Object> params = new HashMap<>();
        params.put("id", generateId("income_report", "id"));
        params.put("commission_income", 0.00);
        params.put("credit_income", 0.00);
        params.put("deposit_income", 0.00);
        params.put("penalty_income", 0.00);
        params.put("deposit_lose", 0.00);
        params.put("credit_lose", 0.00);
        params.put("date", Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        namedJdbcTemplate.update(sql, params);
    }

    public void saveReport(IncomeReport incomeReport) {
        String sql = "UPDATE income_report SET commission_income = :commission_income, credit_income = :credit_income, " +
                "deposit_income = :deposit_income, penalty_income = :penalty_income, deposit_lose = :deposit_lose, " +
                "credit_lose = :credit_lose WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", incomeReport.getId());
        params.put("commission_income", incomeReport.getCommissionIncome());
        params.put("credit_income", incomeReport.getCreditIncome());
        params.put("deposit_income", incomeReport.getDepositIncome());
        params.put("penalty_income", incomeReport.getPenaltyIncome());
        params.put("deposit_lose", incomeReport.getDepositLose());
        params.put("credit_lose", incomeReport.getCreditLose());

        namedJdbcTemplate.update(sql, params);
    }

    public Card getBankAccount() {
        String sql = "SELECT * FROM card WHERE card_id = -2";
        try {
            Card card = jdbcTemplate.queryForObject(sql, new CardRowMapper());
            return card;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void save(Card bankAccount) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", bankAccount.getId());
        params.put("balance", bankAccount.getBalance());

        namedJdbcTemplate.update(
                "UPDATE card SET balance = :balance " +
                        "WHERE card_id = :id",
                params);
    }

    public IncomeReport findMonthReport(int month, int year){
        IncomeReport report = new IncomeReport();
        report.setCommissionIncome(findCommissionIncome(month, year));
        report.setPenaltyIncome(findPenaltyIncome(month, year));
        report.setCreditIncome(findCreditIncome(month, year));
        report.setCreditLose(findCreditLose(month, year));
        report.setDepositIncome(findDepositIncome(month, year));
        report.setDepositLose(findDepositLose(month, year));
        report.setMonthYear(year + "-" + month);

        return report;
    }

    private Double findCommissionIncome(int month, int year) {
        String sql = "SELECT SUM(commission_income) FROM income_report WHERE EXTRACT(MONTH FROM income_report.date) = :month " +
                "AND EXTRACT(YEAR FROM income_report.date) = :year";
        try {
            Double income = namedJdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource()
                            .addValue("month", month)
                            .addValue("year", year)
                    , Double.class);
            return income;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private Double findCreditIncome(int month, int year) {
        String sql = "SELECT SUM(credit_income) FROM income_report WHERE EXTRACT(MONTH FROM income_report.date) = :month " +
                "AND EXTRACT(YEAR FROM income_report.date) = :year";
        try {
            Double income = namedJdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource()
                            .addValue("month", month)
                            .addValue("year", year)
                    , Double.class);
            return income;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private Double findCreditLose(int month, int year) {
        String sql = "SELECT SUM(credit_lose) FROM income_report WHERE EXTRACT(MONTH FROM income_report.date) = :month " +
                "AND EXTRACT(YEAR FROM income_report.date) = :year";
        try {
            Double income = namedJdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource()
                            .addValue("month", month)
                            .addValue("year", year)
                    , Double.class);
            return income;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private Double findDepositIncome(int month, int year) {
        String sql = "SELECT SUM(deposit_income) FROM income_report WHERE EXTRACT(MONTH FROM income_report.date) = :month " +
                "AND EXTRACT(YEAR FROM income_report.date) = :year";
        try {
            Double income = namedJdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource()
                            .addValue("month", month)
                            .addValue("year", year)
                    , Double.class);
            return income;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private Double findDepositLose(int month, int year) {
        String sql = "SELECT SUM(deposit_lose) FROM income_report WHERE EXTRACT(MONTH FROM income_report.date) = :month " +
                "AND EXTRACT(YEAR FROM income_report.date) = :year";
        try {
            Double income = namedJdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource()
                            .addValue("month", month)
                            .addValue("year", year)
                    , Double.class);
            return income;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private Double findPenaltyIncome(int month, int year) {
        String sql = "SELECT SUM(penalty_income) FROM income_report WHERE EXTRACT(MONTH FROM income_report.date) = :month " +
                "AND EXTRACT(YEAR FROM income_report.date) = :year";
        try {
            Double income = namedJdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource()
                            .addValue("month", month)
                            .addValue("year", year)
                    , Double.class);
            return income;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Card findATM() {
        String sql = "SELECT * FROM card WHERE card_id = -1";
        try {
            Card card = jdbcTemplate.queryForObject(sql, new CardRowMapper());
            return card;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Card findBank() {
        String sql = "SELECT * FROM card WHERE card_id = -2";
        try {
            Card card = jdbcTemplate.queryForObject(sql, new CardRowMapper());
            return card;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Card findSubscriptionInfoCard(String name) {
        String sql = "SELECT * FROM card WHERE number = ?";
        try {
            Card card = jdbcTemplate.queryForObject(sql, new Object[]{name}, new CardRowMapper());
            return card;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private long generateId(String table, String columnId) {
        String sql = "SELECT MAX(" + columnId + ") FROM " + table;
        Long currentId;
        currentId = jdbcTemplate.queryForObject(sql, Long.class);
        if (currentId != null) return currentId + 1;
        else return 1;
    }

    private Date findMinDate() {
        String sql = "SELECT date from income_report WHERE id = (SELECT MIN(id) FROM income_report)";
        Date minDate = jdbcTemplate.queryForObject(sql, Date.class);

        return minDate;
    }

    private Date findMaxDate() {
        String sql = "SELECT date from income_report  WHERE id = (SELECT MAX(id) FROM income_report)";
        Date maxDate = jdbcTemplate.queryForObject(sql, Date.class);

        return maxDate;
    }
}
