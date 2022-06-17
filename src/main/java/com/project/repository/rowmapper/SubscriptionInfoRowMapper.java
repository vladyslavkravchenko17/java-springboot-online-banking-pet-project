package com.project.repository.rowmapper;

import com.project.domain.SubscriptionInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionInfoRowMapper implements RowMapper<SubscriptionInfo> {
    @Override
    public SubscriptionInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        SubscriptionInfo info = new SubscriptionInfo();
        info.setId(rs.getLong("subscription_info_id"));
        info.setName(rs.getString("name"));
        info.setDescription(rs.getString("description"));
        info.setSumPerMonth(rs.getDouble("sum_per_month"));
        info.setImageUrl(rs.getString("image_url"));

        return info;
    }
}
