package com.effort.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.effort.entity.Employee;
import com.effort.entity.Provisioning;
import com.effort.entity.WebUser;
import com.effort.sqls.Sqls;
import com.effort.util.Api;
import com.effort.util.Log;
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
public class EmployeeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Employee getManagerBasicDetails(String empId, String companyId) {
		Employee employee = null;
		try {
			employee = jdbcTemplate.queryForObject(Sqls.SELECT_MANAGER_BASIC_DETAILS_BY_ID, new Object[] { empId,companyId },
					new BeanPropertyRowMapper<Employee>(Employee.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}
	
	public Employee getEmployeeBasicDetailsByEmpId(String empId) {
		Employee employee = new Employee();
	    try {
	        return jdbcTemplate.queryForObject(
	            Sqls.SELECT_EMPLOYEE_BASIC_DETAILS_BY_ID,
	            new Object[] { empId },
	            new BeanPropertyRowMapper<>(Employee.class)
	        );
	    } catch (EmptyResultDataAccessException e) {
	        Log.info(getClass(), "No employee found for empId: " + empId);
	        return employee; // No employee found
	    } catch (Exception e) {
	        Log.info(getClass(), "Exception in getEmployeeBasicDetailsByEmpId, empId: " + empId);
	        Log.info(getClass(), "Exception: " + e.getMessage());
	        e.printStackTrace();
	        return employee;
	    }
	}

	public Employee getExpiredEmployee(String empId, String companyId) {
		Employee employee = null;
		try {
			employee = jdbcTemplate.queryForObject(Sqls.SELECT_EXPIRED_EMPLOYEE_BY_ID, new Object[] { empId,companyId },
					new BeanPropertyRowMapper<Employee>(Employee.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}
	
	
	public WebUser getWebUserByUsernameEmp(String userName) {
		WebUser webUser = jdbcTemplate.queryForObject(Sqls.SELECT_WEB_USER_BY_USERNAME_EMP, new Object[] { userName },
				new BeanPropertyRowMapper<WebUser>(WebUser.class));
		webUser.setType(WebUser.TYPE_EMPLOYEE);
		return webUser;
	}
	
	
	public List<String> getWebUserAuthorities(String userName) {
		List<String> authorities = jdbcTemplate.queryForList(Sqls.SELECT_WEB_USER_AUTHORITIES,
				new Object[] { userName }, String.class);
		return authorities;
	}

	
	public long insertProvisioning(final Provisioning provisioning) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(Sqls.INSERT_PROVISIONING,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, provisioning.getEmpId());
				ps.setString(2, provisioning.getCode());
				ps.setString(3, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				ps.setString(4, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				return ps;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();
		provisioning.setId(id);

		return id;
	}
	public Provisioning getProvisioningByCodeORPendingCode(String code) {
		Provisioning provisioning = jdbcTemplate.queryForObject(Sqls.SELECT_PROVISIONING_BY_CODE_OR_PENDING_CODE,
				new Object[] { code, code }, new BeanPropertyRowMapper<Provisioning>(Provisioning.class));
		return provisioning;
	}
	
	public int updateProvisioning(Provisioning provisioning) {
		return jdbcTemplate.update(Sqls.UPDATE_PROVISIONING,
				new Object[] { provisioning.getEmpId(), provisioning.getCode(), provisioning.getPendingCode(),
						Api.getDateTimeInUTC(new Date(System.currentTimeMillis())), provisioning.getId() });
	}
	
	public Provisioning getProvisioningByEmpId(long empId) {
		Provisioning provisioning = jdbcTemplate.queryForObject(Sqls.SELECT_PROVISIONING_BY_EMP_ID,
				new Object[] { empId }, new BeanPropertyRowMapper<Provisioning>(Provisioning.class));
		return provisioning;
	}
	
	public Employee getEmployee(String id) {
	    try {
	        List<Employee> employees = jdbcTemplate.query(
	            Sqls.SELECT_EMPLOYEE_BY_ID,
	            new Object[] { id },
	            new BeanPropertyRowMapper<>(Employee.class)
	        );
	        return employees.isEmpty() ? null : employees.get(0);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	
	public void updateEmployeeWithIMEI(String code, long empId) {
		jdbcTemplate.update(Sqls.UPDATE_EMPLOYEE_IMEI,
				new Object[] { code, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())), empId });

	}
	
	public long insertProvisioningWithEffortToken(final Provisioning provisioning) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(Sqls.INSERT_PROVISIONING_WITH_EFFORT_TOKEN,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, provisioning.getEmpId());
				ps.setString(2, provisioning.getCode());
				ps.setString(3, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				ps.setString(4, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				if (Api.isEmptyString(provisioning.getEffortToken())) {
					ps.setNull(5, Types.VARCHAR);
				} else {
					ps.setString(5, provisioning.getEffortToken());
				}
				return ps;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();
		provisioning.setId(id);

		return id;
	}
	
	public int deleteProvisioning(long empId) {
		return jdbcTemplate.update(Sqls.DELETE_PROVISIONING, new Object[] { empId });
	}
	
	public int updateProvisioningWithEffortToken(Provisioning provisioning) {
		return jdbcTemplate.update(Sqls.UPDATE_PROVISIONING_WITH_EFFORT_TOKEN,
				new Object[] { provisioning.getEmpId(), provisioning.getCode(), provisioning.getPendingCode(),
						provisioning.getEffortToken(), Api.getDateTimeInUTC(new Date(System.currentTimeMillis())),
						provisioning.getId() });
	}
	
	public Employee getActiveEmployeeByImei(String imei) {
		Employee employee = null;
		try {
			employee = jdbcTemplate.queryForObject(Sqls.SELECT_ACTIVE_EMPLOYEE_BY_IMEI, new Object[] { imei },
					new BeanPropertyRowMapper<Employee>(Employee.class));
		} catch (Exception e) {
			return employee;
		}
		return employee;
	}
	
	public List<Employee> getEmployeeByPhoneNo(String phoneNo) {
		String sql = Sqls.SELECT_EMPLOYEE_BY_PHONE_NO;
		List<Employee> employees = jdbcTemplate.query(sql, new Object[] {phoneNo},
				new BeanPropertyRowMapper<Employee>(Employee.class));
		return employees;
	}
	
	public Employee getActiveEmployeeByPhoneNo(String phoneNo) {
		String sql = Sqls.SELECT_ACTIVE_EMPLOYEE_BY_PHONE_NO;
		Employee employee = jdbcTemplate.queryForObject(sql, new Object[] { phoneNo },
				new BeanPropertyRowMapper<Employee>(Employee.class));
		return employee;
	}

}
