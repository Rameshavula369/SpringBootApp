package com.effort.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.effort.sqls.Sqls;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class AuditDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int auditEmpTerritories(String territoryIds, long auditParent) {
		return jdbcTemplate.update(Sqls.UPDATE_EMPLOYEE_AUDIT_LOG_FOR_EMP_TERRITORY,
				new Object[] { territoryIds,auditParent});
		
	}

	
	public int auditEmpGroupForEmployee(String empMappedGroupIds,String ipAddress, long auditParent) {
		return jdbcTemplate.update(Sqls.UPDATE_EMPLOYEE_AUDIT_LOG_FOR_EMP_GROUP,
				new Object[] { empMappedGroupIds,ipAddress,auditParent});
		
	}
	
	public int auditEmployeeAccessSettings(final long empId, final long auditParent, final long by, final String time){
		return jdbcTemplate.update(Sqls.AUDIT_EMPLOYEE_ACCESS_SETTINGS,
				new Object[] { auditParent, by, time, empId });
	}
	
	public int auditProvisionings(final long empId, final long auditParent, final long by, final String time){
		return jdbcTemplate.update(Sqls.AUDIT_PROVISIONINGS,
				new Object[] { auditParent, by, time, empId });
	}
	public int auditUsers(final long empId, final long auditParent, final long by, final String time){
		return jdbcTemplate.update(Sqls.AUDIT_USERS,
				new Object[] { auditParent, by, time, empId });
	}
	public int auditEmployeeSettings(final long empId, final long auditParent, final long by, final String time){
		return jdbcTemplate.update(Sqls.AUDIT_EMPLOYEE_SETTINGS,
				new Object[] { auditParent, by, time, empId });
	}
	public int auditSettingsLog(final long empId, final long auditParent, final long by, final String time){
		return jdbcTemplate.update(Sqls.INSERT_INTO_SETTINGS_AUDIT_LOG,
				new Object[] { auditParent,time,by, empId });
	}
	
	
	public long auditEmployee(final long empId, final long by, final String time, final String ipAddress, final String oppUserName){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(Sqls.AUDIT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
				ps.setNull(1, Types.BIGINT);
				ps.setLong(2, by);
				ps.setString(3, time);
				ps.setString(4, ipAddress);
				ps.setString(5, oppUserName);
				ps.setLong(6, empId);
				return ps;
			}
		}, keyHolder);
		
		long id = keyHolder.getKey().longValue();
		
		return id;
	}
}
