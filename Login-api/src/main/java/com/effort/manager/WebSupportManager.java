package com.effort.manager;

import org.springframework.beans.factory.annotation.Autowired;

import com.effort.dao.ExtraSupportDao;


import org.springframework.stereotype.Service;


@Service
public class WebSupportManager {
	
	@Autowired
	private ExtraSupportDao extraSupportDao;
	
	public int updateEmployeeEncryptionKey(Long empId, String encryptionKey){
		return extraSupportDao.updateEmployeeEncryptionKey(empId, encryptionKey);
	}

}
