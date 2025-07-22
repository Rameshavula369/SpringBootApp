package com.effort.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.effort.sqls.Sqls;
import com.effort.util.Api;

import org.springframework.stereotype.Repository;

@Repository
public class ExtraSupportDao {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int updateEmployeeEncryptionKey(Long empId, String encryptionKey){
		return jdbcTemplate.update(
				Sqls.UPDATE_EMPLOYEE_ENCRYPTION_KEY,
				new Object[] {encryptionKey,Api.getDateTimeInUTC(new Date(System.currentTimeMillis())),empId});
	}
	

}
