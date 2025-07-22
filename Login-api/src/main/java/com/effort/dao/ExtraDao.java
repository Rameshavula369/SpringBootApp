package com.effort.dao;


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
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.effort.entity.Company;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeGroup;
import com.effort.entity.Mail;
import com.effort.entity.ProvisioningFailureDetails;
import com.effort.entity.ProvisioningOTKey;
import com.effort.entity.Subscripton;
import com.effort.entity.TerritoriesMapping;
import com.effort.sqls.Sqls;
import com.effort.util.Api;
import com.effort.util.Log;

import org.springframework.stereotype.Repository;

@Repository
public class ExtraDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String getCompanySetting(long companyId, String key) {
		String value = jdbcTemplate.queryForObject(
				Sqls.SELECT_COMPANY_SETTINGS, new Object[] { key, companyId },
				String.class);
		return value;
	}

	public List<TerritoriesMapping> getMappedTerritoryIds(long empId) {
		List<TerritoriesMapping> territoriesMapping = new ArrayList<TerritoriesMapping>();
		try {
			territoriesMapping = jdbcTemplate.query(
					Sqls.SELECT_EMPLOYEE_MAPPED_TERRITORIES,
					new Object[] { empId },
					new BeanPropertyRowMapper<TerritoriesMapping>(
							TerritoriesMapping.class));
		} catch (Exception e) {
			Log.info(getClass(),
					"No territory mapping found for this employee wit empId: "
							+ empId);
		}

		return territoriesMapping;
	}
	
	
	public Company getCompany(long id) {
		Company company = jdbcTemplate.queryForObject(Sqls.SELECT_COMPANY,
				new Object[] { id }, new BeanPropertyRowMapper<Company>(
						Company.class));
		return company;
	}
	
	
	
	public long insertProvisioningFailureDetails(
			final ProvisioningFailureDetails pfd) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {

				PreparedStatement ps = connection
						.prepareStatement(Sqls.INSERT_PROVISIONING_FAILURE_DETAILS,
								Statement.RETURN_GENERATED_KEYS);
				
				
				ps.setNull(1, Types.INTEGER);
				if( pfd.getCompanyId() != null){
					ps.setLong(1, pfd.getCompanyId());
				}
				ps.setNull(2, Types.BIGINT);
				if(pfd.getEmpId() != null){
					ps.setLong(2, pfd.getEmpId());
				}
				ps.setString(3, pfd.getEmpPhone());
				ps.setString(4, pfd.getImei());
				ps.setString(5, pfd.getUsername());
				ps.setString(6, pfd.getPassword());
				ps.setString(7, pfd.getActivationCode());
				ps.setString(8, pfd.getOverride());
				ps.setString(9, pfd.getIpAddress());
				ps.setInt(10, pfd.getFailureCode());
				ps.setString(11, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				ps.setString(12, pfd.getClientPlatform());
				ps.setString(13, pfd.getClientVersion());
				ps.setString(14, pfd.getOsVersion());
				ps.setString(15, pfd.getVersionCode());
				
				return ps;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();
		pfd.setId(id);

		return id;
		
	}
	
	public Subscripton getActiveSubscripton(long companyId) {
		Subscripton subscripton = jdbcTemplate.queryForObject(
				Sqls.SELECT_ACTIVE_SUBSCRIPTION, new Object[] { companyId },
				new BeanPropertyRowMapper<Subscripton>(Subscripton.class));
		return subscripton;
	}

	public List<EmployeeGroup> getEmployeeGroupOfEmployee(Long... empIds) {
		String ids = Api.toCSV(empIds);
		if (!Api.isEmptyString(ids)) {
			String sql = Sqls.SELECT_EMPLOYEE_GROUP_OF_EMPLOYEES.replace(
					":ids", ids);
			List<EmployeeGroup> employeeGroups = jdbcTemplate.query(sql,
					new Object[] {}, new BeanPropertyRowMapper<EmployeeGroup>(
							EmployeeGroup.class));
			return employeeGroups;
		} else {
			return new ArrayList<EmployeeGroup>();
		}
	}
	
	
	public long insertMail(final Mail mail) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						Sqls.INSERT_MAIL, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, mail.getMailFrom());
				ps.setString(2, mail.getMailTo());
				ps.setString(3, mail.getMailSubject());
				ps.setString(4, mail.getMailBody());
				ps.setString(5, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				ps.setString(6, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				ps.setString(7, "" + mail.getMailBodyType());
				ps.setString(8, "" + mail.getCompanyId());
				ps.setBoolean(9, mail.isVerificationRequired());
				ps.setInt(10, mail.getPriority());
				ps.setInt(11, mail.getMailSentType());
				return ps;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();
		mail.setId(id);

		return id;
	}
	
	public List<ProvisioningOTKey> getActiveProvisioningKeyForEmp(Employee employee) {
		List<ProvisioningOTKey> provisioningOTKeys = jdbcTemplate.query(
				Sqls.SELECT_ACTIVE_PROVISIONING_OTK_BY_EMP,
				new Object[] {employee.getEmpId(),employee.getCompanyId(),employee.getEmpPhone()}, new BeanPropertyRowMapper<ProvisioningOTKey>(
						ProvisioningOTKey.class));
		return provisioningOTKeys;
	}
	public long insertProvisioningOTKey(final ProvisioningOTKey provisioningOTKey) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {

				PreparedStatement ps = connection
						.prepareStatement(Sqls.INSERT_PROVISIONING_OTK,
								Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, provisioningOTKey.getEmpId());
				ps.setInt(2, provisioningOTKey.getCompanyId());
				ps.setString(3, Api.makeNullIfEmpty(provisioningOTKey.getEmpPhone()));
				ps.setString(4, provisioningOTKey.getKey());
				ps.setBoolean(5, provisioningOTKey.isDeleted());
				ps.setString(6, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				ps.setString(7, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));
				
				return ps;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();
		provisioningOTKey.setId(id);

		return id;
	}
	

	public List<ProvisioningOTKey> getExisitngProvisioningOTKeysWithPhoneNumber(
			Employee employee) {
		List<ProvisioningOTKey> pkeys = new ArrayList<ProvisioningOTKey>();
		try {
			pkeys = jdbcTemplate.query(Sqls.SELECT_EXISITNG_KEY_FOR_PHONE_NUM, new Object[]{employee.getEmpPhone(), employee.getEmpId()},
					new BeanPropertyRowMapper<ProvisioningOTKey>(ProvisioningOTKey.class));
		} catch (Exception e) {
			return pkeys;
		}
		return pkeys;
	}
	
	public void deleteExistingProvisioningOTKeys(
			List<ProvisioningOTKey> existingProvisioningOTKeys) {
		String exisitngIds = Api.toCSVFromList(existingProvisioningOTKeys, "id");
		String sql = Sqls.DELETE_EXISITING_PROVISIONING_KEYS.replace(":ids", exisitngIds);
				jdbcTemplate.update(sql, new Object[]{Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis()))});
				
		
	}	
	
	
	public List<ProvisioningOTKey> getActiveProvisioningKeyByEmpPhone(String empPhone) {
		List<ProvisioningOTKey> provisioningOTKeys = jdbcTemplate.query(
				Sqls.SELECT_ACTIVE_PROVISIONING_OTK_BY_EMPPHONE,
				new Object[] {empPhone}, new BeanPropertyRowMapper<ProvisioningOTKey>(
						ProvisioningOTKey.class));
		return provisioningOTKeys;
	}
	

	public void updateActiveProvisioningKeyByEmpPhoneAndKey(String empPhone,String activationCode) {
		jdbcTemplate.update(Sqls.UPDATE_USED_PROVISIONING_OTKEY, 
				new Object[]{
				Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())),empPhone,activationCode});
		
	}

}
