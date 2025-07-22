package com.effort.entity;



public class Provisioning {
	private long id;
	private long empId;
	private String code;
	private String pendingCode;
	private String createTime;
	private String modifiedTime;
	
	private String effortToken;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPendingCode() {
		return pendingCode;
	}
	public void setPendingCode(String pendingCode) {
		this.pendingCode = pendingCode;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getEffortToken() {
		return effortToken;
	}
	public void setEffortToken(String effortToken) {
		this.effortToken = effortToken;
	}
	
	
	
}
