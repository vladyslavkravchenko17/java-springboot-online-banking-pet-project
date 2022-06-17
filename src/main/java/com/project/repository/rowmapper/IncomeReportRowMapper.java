package com.project.repository.rowmapper;

import com.project.domain.IncomeReport;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class IncomeReportRowMapper implements RowMapper<IncomeReport> {
    @Override
    public IncomeReport mapRow(ResultSet rs, int rowNum) throws SQLException {
        IncomeReport incomeReport = new IncomeReport();
        incomeReport.setId(rs.getLong("id"));
        incomeReport.setCommissionIncome(rs.getDouble("commission_income"));
        incomeReport.setCreditIncome(rs.getDouble("credit_income"));
        incomeReport.setPenaltyIncome(rs.getDouble("penalty_income"));
        incomeReport.setDepositIncome(rs.getDouble("deposit_income"));
        incomeReport.setCreditLose(rs.getDouble("credit_lose"));
        incomeReport.setDepositLose(rs.getDouble("deposit_lose"));
        String date = rs.getString("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        incomeReport.setDate(LocalDate.parse(date, formatter));

        return incomeReport;
    }
}
