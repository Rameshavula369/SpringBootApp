package com.effort.manager;

import org.springframework.beans.factory.annotation.Autowired;

import com.effort.dao.EmployeeDao;
import com.effort.entity.Employee;


import org.springframework.stereotype.Service;


@Service
public class WebAdditionalSupportExtraManager {

	@Autowired
	private EmployeeDao employeeDao;
	
	public Employee getEmployeeBasicDetailsByEmpId(String empId) {
		return employeeDao.getEmployeeBasicDetailsByEmpId(empId);
	}
}
