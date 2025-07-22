package com.effort.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.effort.entity.SmsActivationTemplate;
import com.effort.sqls.Sqls;
@Repository
public class SyncDao {


	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public SmsActivationTemplate getSmsActivationTemplate(String companyId) {
		SmsActivationTemplate smsActivationTemplate = null;
		try {
			smsActivationTemplate = jdbcTemplate.queryForObject(Sqls.SELECT_SMS_ACTIVATION_TEMPLATE_FOR_COMPANY,
					new Object[] { companyId },
					new BeanPropertyRowMapper<SmsActivationTemplate>(SmsActivationTemplate.class));
			return smsActivationTemplate;
		} catch (Exception e) {
			return smsActivationTemplate;
		}
	}
	
}
