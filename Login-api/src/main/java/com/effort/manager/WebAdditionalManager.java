package com.effort.manager;

import org.springframework.beans.factory.annotation.Autowired;

import com.effort.dao.EmployeeDao;


import org.springframework.stereotype.Service;


@Service
public class WebAdditionalManager {

	@Autowired
	private EmployeeDao employeeDao;
	
	public void updateEmployeeWithIMEI(String code, long empId) {

		employeeDao.updateEmployeeWithIMEI(code, empId);

	}
}
