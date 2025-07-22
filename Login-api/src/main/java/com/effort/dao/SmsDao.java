package com.effort.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.effort.entity.OutgoingSMS;
import com.effort.sqls.Sqls;
import com.effort.util.Api; 

import org.springframework.stereotype.Repository;

@Repository
public class SmsDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public long saveOutgoingSms(final OutgoingSMS outgoingSMS) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						Sqls.INSERT_OUTGOING_SMS, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, outgoingSMS.getMsisdn());
				ps.setString(2, outgoingSMS.getMessage());
				ps.setString(3, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				ps.setInt(4, outgoingSMS.getStatus());
				ps.setInt(5, outgoingSMS.getCompanyId());
				ps.setInt(6, outgoingSMS.getSmsType());
				ps.setBoolean(7, outgoingSMS.isSendSmsViaWhatsaap());
				ps.setInt(8,outgoingSMS.getSendSmsType());
				return ps;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();
		outgoingSMS.setId(id);

		return id;
	}
	 
	
}
