package com.effort.manager;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.effort.dao.AuditDao;
import com.effort.dao.EmployeeDao;
import com.effort.dao.ExtraDao;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeGroup;
import com.effort.entity.TerritoriesMapping;
import com.effort.entity.WebUser;
import com.effort.util.Api;


import org.springframework.stereotype.Service;


@Service
public class WebManager {
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private ExtraDao extraDao;
	
	@Autowired
	private AuditDao auditDao; 
	
	public WebUser getWebUserByUsernameEmp(String userName) {
		return employeeDao.getWebUserByUsernameEmp(userName);
	}
	
	public List<String> getWebUserAuthorities(String userName) {
		return employeeDao.getWebUserAuthorities(userName);
	}

	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void logEmployeeAudit(long empId, long by,String ipAddress, String oppUserName) {
		String time = Api
				.getDateTimeInUTC(new Date(System.currentTimeMillis()));

		long auditParent = auditDao.auditEmployee(empId, by, time, ipAddress, oppUserName);
		/* Modified By Tushar
		 * 2016-02-09 
		 * Add emp group in the employee audit log
		 */
		List<EmployeeGroup> employeeGroups = getEmployeeGroupOfEmployee(empId);
		String empMappedGroupIds = "";
		try {
			 empMappedGroupIds = Api.toCSV(employeeGroups, "empGroupId");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<TerritoriesMapping> employeeTerritoryMaps = getMappedTerritoryIds(empId);
		String employeeTerritoryMapIds = "";
		try {
			employeeTerritoryMapIds = Api.toCSV(employeeTerritoryMaps, "territoryId");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		auditDao.auditEmpTerritories(employeeTerritoryMapIds,auditParent);
		auditDao.auditEmpGroupForEmployee(empMappedGroupIds,ipAddress,auditParent);
		auditDao.auditEmployeeAccessSettings(empId, auditParent, by, time);
		//auditDao.auditEmployeeCustomerMap(empId, auditParent, by, time, ipAddress, oppUserName);
		auditDao.auditProvisionings(empId, auditParent, by, time);
		auditDao.auditUsers(empId, auditParent, by, time);
		auditDao.auditEmployeeSettings(empId, auditParent, by, time);
		auditDao.auditSettingsLog(empId, auditParent, by, time);

	}
	
	
	public List<EmployeeGroup> getEmployeeGroupOfEmployee(Long... empIds) {
		return extraDao.getEmployeeGroupOfEmployee(empIds);
	} 
	
	public List<TerritoriesMapping> getMappedTerritoryIds(long empId) {

		List<TerritoriesMapping> employeeTerritoyMaps = extraDao
				.getMappedTerritoryIds(empId);
		return employeeTerritoyMaps;
	}
	
	public Employee getEmployee(String empId) {
		return employeeDao.getEmployee(empId);
	}

}
