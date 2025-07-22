package com.effort.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.effort.entity.ITunesPurchase;
import com.effort.sqls.Sqls;
import com.effort.util.Log;

import org.springframework.stereotype.Repository;

@Repository
public class InAppDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public boolean isPurchaseRecordAvailableForEMp(Long empId) {
	    Integer count = jdbcTemplate.queryForObject(
	        Sqls.SELECT_ITUNE_PURCHASES_RECORDS_COUNT_FOR_EMP,
	        Integer.class,
	        empId
	    );

	    return count != null && count > 0;
	}

	
	public void updateUdidCodeForEmp(long empId, String code) {
		try {
			jdbcTemplate.update(Sqls.UPDATE_UDID_CODE_FOR_EMP, new Object[]{code, empId});
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString());
		}
	
	}
	public ITunesPurchase getITunePurchaseRecord(long empId) {
		ITunesPurchase iTunesPurchase = null;
		try{
			iTunesPurchase = jdbcTemplate.queryForObject(Sqls.SELECT_ITUNE_PURCHASE_RECORD, new Object[]{empId},
					new BeanPropertyRowMapper<ITunesPurchase>(ITunesPurchase.class));
		}catch(Exception e){
			return null;
		}
		return iTunesPurchase;
	}

	public int insertITunesPurchase(ITunesPurchase iTunesPurchase) {
		return jdbcTemplate.update(
				Sqls.INSERT_ITUNES_PURCHASE,
				new Object[] { iTunesPurchase.getEmpId(),
						iTunesPurchase.getUdid(),
						iTunesPurchase.getAppStoreProductId(),
						iTunesPurchase.getTransactionId(),
						iTunesPurchase.getPurchaseTime(),
						iTunesPurchase.getExpiryTime(),
						iTunesPurchase.getItemId(),
						iTunesPurchase.getReceiptData(),
						iTunesPurchase.getTrial(), iTunesPurchase.isStatus(),
						iTunesPurchase.getStatusMessage(),
						iTunesPurchase.getAppStoreResponse()});
	}
	
}
