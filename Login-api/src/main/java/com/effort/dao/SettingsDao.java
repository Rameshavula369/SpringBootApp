package com.effort.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.effort.sqls.Sqls;

import org.springframework.stereotype.Repository;

@Repository
public class SettingsDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String getCompanySetting(long companyId, String key){
		
//		String value = null;
//		try{
		String	value = jdbcTemplate.queryForObject(Sqls.SELECT_COMPANY_SETTINGS, new Object[] { key, companyId }, String.class);
//		}catch(Exception e){
			
//		}
		return value;
	}
	
	public String getGlobalSettings(String key){
		String smsc = jdbcTemplate.queryForObject(Sqls.SELECT_GLOBAL_SETTINGS, new Object[] {key}, String.class);
		return smsc;
	}
	
}
