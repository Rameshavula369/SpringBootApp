package com.effort.entity;


import java.io.Serializable;
import java.util.List;

import com.effort.util.Api;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author spoors
 *
 */
public class EmployeeGroup implements Serializable{
	private long empGroupId;
	private long companyId;
	private String empGroupName;
	private long empGroupTypeId = 1;
	private String externalId;
	private String apiUserId;
	private boolean selected;
	private String empIds;
	private String employeeGroupEmployess;
	private List<Long> employees;
	private boolean mapeed;
	private int unassignedWorksCount;
	
	public long getEmpGroupId() {
		return empGroupId;
	}
	public void setEmpGroupId(long empGroupId) {
		this.empGroupId = empGroupId;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getEmpGroupName() {
		return empGroupName;
	}
	public void setEmpGroupName(String empGroupName) {
		this.empGroupName = empGroupName;
	}
	public long getEmpGroupTypeId() {
		return empGroupTypeId;
	}
	public void setEmpGroupTypeId(long empGroupTypeId) {
		this.empGroupTypeId = empGroupTypeId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	@JsonIgnore
	public String getApiUserId() {
		return apiUserId;
	}
	@JsonIgnore
	public void setApiUserId(String apiUserId) {
		this.apiUserId = apiUserId;
	}
	public List<Long> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Long> employees) {
		this.employees = employees;
	}
	
	@JsonIgnore
	public boolean isMapeed() {
		return mapeed;
	}
	@JsonIgnore
	public void setMapeed(boolean mapeed) {
		this.mapeed = mapeed;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof EmployeeGroup){
			EmployeeGroup employeeGroup = (EmployeeGroup) obj;
			return employeeGroup.getEmpGroupId() == this.empGroupId;
		} else {
			return super.equals(obj);
		}
	}
	
	public String toCSV() {
		return "EmployeeGroup [empGroupId=" + empGroupId + ", companyId="
				+ companyId + ", empGroupName=" + empGroupName
				+ ", externalId=" + externalId + ", apiUserId=" + apiUserId
				+ ", employees=" + employees + "]";
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getEmpIds() {
		return empIds;
	}
	public void setEmpIds(String empIds) {
		this.empIds = empIds;
	}
	
	public List<Long> getEmployeeIds(){
		return Api.csvToLongList(this.empIds);
	}
	
	public String getEmployeeGroupEmployess() {
		return employeeGroupEmployess;
	}
	public void setEmployeeGroupEmployess(String employeeGroupEmployess) {
		this.employeeGroupEmployess = employeeGroupEmployess;
	}
	
	public int getUnassignedWorksCount() {
		return unassignedWorksCount;
	}
	public void setUnassignedWorksCount(int unassignedWorksCount) {
		this.unassignedWorksCount = unassignedWorksCount;
	}
	@Override
	public String toString() {
		return "EmployeeGroup [empGroupId=" + empGroupId + ", companyId=" + companyId + ", empGroupName=" + empGroupName
				+ ", empGroupTypeId=" + empGroupTypeId + ", externalId=" + externalId + ", apiUserId=" + apiUserId
				+ ", selected=" + selected + ", empIds=" + empIds + ", employeeGroupEmployess=" + employeeGroupEmployess
				+ ", employees=" + employees + ", mapeed=" + mapeed + "]";
	}
	
}
