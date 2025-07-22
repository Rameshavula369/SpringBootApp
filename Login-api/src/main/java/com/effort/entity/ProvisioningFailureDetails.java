package com.effort.entity;


public class ProvisioningFailureDetails {

	
	
	public static final int INVALID_ACTIVATIONCODE=1;
	public static final int INVALID_UNAME_PASSWORD=2;
	public static final int INVALID_SIGNUP=3;
	public static final int INVALID_IMEI=4;
		
	private Long id;
	private Long empId;
	private String empPhone;
	private String imei;
	private String activationCode;
	private String override;
	private String ipAddress;
	private String createdTime;
	private String username;
	private String password;
	private int failureCode;
	private String clientPlatform;
	private String clientVersion;
	private String osVersion;
	private String productCode;
	private String versionCode;
	private Integer companyId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmpPhone() {
		return empPhone;
	}
	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public String getOverride() {
		return override;
	}
	public void setOverride(String override) {
		this.override = override;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getFailureCode() {
		return failureCode;
	}
	public void setFailureCode(int failureCode) {
		this.failureCode = failureCode;
	}
	public String getClientPlatform() {
		return clientPlatform;
	}
	public void setClientPlatform(String clientPlatform) {
		this.clientPlatform = clientPlatform;
	}
	public String getClientVersion() {
		return clientVersion;
	}
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	
}
