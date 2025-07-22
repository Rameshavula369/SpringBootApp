package com.effort.manager;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.effort.dao.ExtraDao;
import com.effort.entity.Employee;
import com.effort.entity.ProvisioningOTKey;
import com.effort.util.Api;

@Service
public class WebExtraManager {

	@Autowired
	private ExtraDao extraDao;
	
	public String getProvisioningOTKByEmp(Employee employee)
	{
		List<ProvisioningOTKey> provisioningOTKeys = getActiveProvisioningKeyForEmp(employee);
		String activationcode = "";
		if(provisioningOTKeys!=null && provisioningOTKeys.size()>0)
		{
			activationcode = provisioningOTKeys.get(0).getKey();
		}
		else
		{
			if(employee.getEmpPhone()!=null && !employee.getEmpPhone().trim().equalsIgnoreCase(""))
			{
				
				List<ProvisioningOTKey> existingProvisioningOTKeys= getExisitngProvisioningOTKeysWithPhoneNumber(employee); 
				
				if(existingProvisioningOTKeys != null && !existingProvisioningOTKeys.isEmpty()){
					deleteExistingProvisioningOTKeys(existingProvisioningOTKeys);
				}
				
				activationcode = Api.getRandomNumber(6);
				ProvisioningOTKey provisioningOTKey = new ProvisioningOTKey();
				provisioningOTKey.setEmpId(employee.getEmpId());
				provisioningOTKey.setCompanyId(employee.getCompanyId());
				provisioningOTKey.setKey(activationcode);
				provisioningOTKey.setDeleted(false);
				provisioningOTKey.setEmpPhone(employee.getEmpPhone());
							
				insertProvisioningOTKey(provisioningOTKey);
			}
		}
		
		return activationcode;
	}
	
	public List<ProvisioningOTKey> getExisitngProvisioningOTKeysWithPhoneNumber(
			Employee employee) {
		
		return extraDao.getExisitngProvisioningOTKeysWithPhoneNumber(employee);
	}
	
	public Long  insertProvisioningOTKey(ProvisioningOTKey provisioningOTKey) {
		
		Long id=extraDao.insertProvisioningOTKey(provisioningOTKey);
		return id;
	}
	
	
	public List<ProvisioningOTKey> getActiveProvisioningKeyForEmp(Employee employee) {
		return extraDao.getActiveProvisioningKeyForEmp(employee);
	}
	
	public void deleteExistingProvisioningOTKeys(
			List<ProvisioningOTKey> existingProvisioningOTKeys) {
		extraDao.deleteExistingProvisioningOTKeys(existingProvisioningOTKeys);
	}
	
	
	public List<ProvisioningOTKey> getActiveProvisioningKeyByEmpPhone(String empPhone) {
		return extraDao.getActiveProvisioningKeyByEmpPhone(empPhone);
	}
	
	public void updateActiveProvisioningKeyByEmpPhoneAndKey(String empPhone,String activationCode) {
		extraDao.updateActiveProvisioningKeyByEmpPhoneAndKey(empPhone,activationCode);

	}
}
