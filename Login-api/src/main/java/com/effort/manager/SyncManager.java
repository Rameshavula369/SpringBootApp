package com.effort.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.effort.dao.EmployeeDao;
import com.effort.entity.Employee;
@Service
public class SyncManager {
	
	@Autowired
	private EmployeeDao employeeDao;
	
	public List<Employee> getEmployeeByPhone(String phoneNo) {
		return employeeDao.getEmployeeByPhoneNo(phoneNo);
	}
}
