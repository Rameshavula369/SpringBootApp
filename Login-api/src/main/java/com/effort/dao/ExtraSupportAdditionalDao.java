package com.effort.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.effort.sqls.Sqls;
import com.effort.util.Api;
import com.effort.util.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import org.springframework.stereotype.Repository;

@Repository
public class ExtraSupportAdditionalDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void updateEffortTokenInProvisioningsForGivenEmpIdAndEmpCode(
			long empId,String effortToken) 
	{
		try
		{
			String sql = Sqls.UPDATE_EFFORT_TOKEN_IN_PROVISIONINS_FOR_GIVEN_EMP_ID_AND_EMP_CODE;
			jdbcTemplate.update(sql, new Object[]{effortToken,effortToken,
					Api.getDateTimeInUTC(new Date(System.currentTimeMillis())),
					empId});
		}
		catch(Exception e )
		{
			Log.info(this.getClass(), "error in updateEffortTokenInProvisioningsForGivenEmpIdAndEmpCode() >> "+e.toString());
			e.printStackTrace();
		}
	}
	
	
	public void insertAuditForActivationCodeResend(final String empPhoneWithISD,
			final String ipAddress) 
	{
		final String sql = Sqls.INSERT_ACTIVATION_CODE_RESEND_AUDIT_LOG;
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						sql,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, empPhoneWithISD);
				ps.setString(2, ipAddress);
				ps.setString(3, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				
				return ps;
			}
		});
		
	}
	
	public Long getPreviuosResendActivationCodeAuditCountByPhone(String empPhone, Long minutes) 
	{
		String sql = Sqls.SELECT_PREVIOUS_RESEND_ACTIVATION_AUDIT_COUNT_BY_PHONE_NO;
		return jdbcTemplate.queryForObject(sql, new Object[]{empPhone, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())), minutes}, Long.class);
	}
	
	
	public Long getPreviuosResendActivationCodeAuditCountByIpAddress(String ipAddress, Long minutes) 
	{
		String sql = Sqls.SELECT_PREVIOUS_RESEND_ACTIVATION_AUDIT_COUNT_BY_IPADDRESS;
		return jdbcTemplate.queryForObject(sql, new Object[]{ipAddress, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())), minutes}, Long.class);
	}
}
