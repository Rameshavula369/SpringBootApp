package com.effort.entity;



import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.effort.settings.ServiceCallConstants;
import com.effort.util.Api;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class WebUser implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int TYPE_EMPLOYEE = 1;
	public static final int TYPE_CUSTOMER = 2;
	public static final int TYPE_CUSTOM_ENTITY = 3;

	private String username;
	private String password;
	private String email;
	private boolean enabled;
	private Long empId;
	private String empFirstName;
	private String empLastName;
	private Long customerId;
	private String customerName;
	private Integer companyId;
	private int type;
	private Integer manager;
	// kiran code for timezone
	private String timezoneName;
	private Integer tzo;
	private Integer timezoneId;
	private String customerIds;

	
	private String ipAddress;
	private boolean oppUser;
	private String oppUserName;
	private Integer concurrentLoginCount;
	private String browserInfo;
	

	
	
	private Set<String> accessibleEmpIdsList;
	private String lastAccessibleEmpIdsListUpdateTime;
	private ServiceCallConstants serviceCallConstants=new  ServiceCallConstants();
	
	private String salt;
	
	private Long customEntityId;
	private String customEntityName;
	
	private boolean hasWebAndConfiguratorAppAccess;
	private boolean hasConfiguratorAccess;
	private boolean enableExternalIntegration;
	private String spoorsIntegrationPath;
	
	private Long appId;
	
	private String empMailIdForWork;
	

	private String empName;
	
	private String empGroupIds;
	
	private int webUserType;
	
	private boolean userWithNoReportsAccess;
	private boolean enableGoogleDataStudio;
	
	private boolean adminUser;
	private String adminUserLoginEmail;
	private String source;
	private String logText;
	private Long companySubscriptionPlanId;
	
	private String loggedEmpName;
	List<Long> filtEmpGroupIds;
	private boolean skipWelcomePage;
	private long empTypeId = 1;
	private boolean webLoginInfo;
	private Long rootEmpId;
	private boolean enableLeftMenu;
	private boolean restrictConfiguratorAccess;
	private String empNo;
	private Collection<GrantedAuthority> authorities;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpFirstName() {
		return empFirstName;
	}

	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	public String getEmpLastName() {
		return empLastName;
	}

	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	public String getEmpName() {
		String empName = "";

		if (!Api.isEmptyString(getEmpFirstName())) {
			empName += getEmpFirstName();
		}

		if (!Api.isEmptyString(getEmpLastName())) {
			if (!Api.isEmptyString(empName)) {
				empName += (" " + getEmpLastName());
			} else {
				empName += getEmpLastName();
			}
		}

		return empName;
	}

	public String toCSV() {
		return "[username=" + username + ", password=" + password + ", empId="
				+ empId + ", empFirstName=" + empFirstName + ", customerId="
				+ customerId + ", customerName=" + customerName
				+ ", companyId=" + companyId + ", type=" + type + ", enabled="
				+ enabled + "]";
	}

	public Integer getManager() {
		return manager;
	}

	public void setManager(Integer manager) {
		this.manager = manager;
	}

	public String getTimezoneName() {
		return timezoneName;
	}

	public void setTimezoneName(String timezoneName) {
		this.timezoneName = timezoneName;
	}

	public Integer getTzo() {
		return tzo;
	}

	public void setTzo(Integer tzo) {
		this.tzo = tzo;
	}

	public Integer getTimezoneId() {
		return timezoneId;
	}

	public void setTimezoneId(Integer timezoneId) {
		this.timezoneId = timezoneId;
	}

	public String getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(String customerIds) {
		this.customerIds = customerIds;
	}


	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public boolean isOppUser() {
		return oppUser;
	}

	public void setOppUser(boolean oppUser) {
		this.oppUser = oppUser;
	}

	public String getOppUserName() {
		return oppUserName;
	}

	public void setOppUserName(String oppUserName) {
		this.oppUserName = oppUserName;
	}

	public Integer getConcurrentLoginCount() {
		return concurrentLoginCount;
	}

	public void setConcurrentLoginCount(Integer concurrentLoginCount) {
		this.concurrentLoginCount = concurrentLoginCount;
	}

	public Set<String> getAccessibleEmpIdsList() {
		return accessibleEmpIdsList;
	}

	public void setAccessibleEmpIdsList(Set<String> accessibleEmpIdsList) {
		this.accessibleEmpIdsList = accessibleEmpIdsList;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getLastAccessibleEmpIdsListUpdateTime() {
		return lastAccessibleEmpIdsListUpdateTime;
	}

	public void setLastAccessibleEmpIdsListUpdateTime(
			String lastAccessibleEmpIdsListUpdateTime) {
		this.lastAccessibleEmpIdsListUpdateTime = lastAccessibleEmpIdsListUpdateTime;
	}

	public Long getCustomEntityId() {
		return customEntityId;
	}

	public void setCustomEntityId(Long customEntityId) {
		this.customEntityId = customEntityId;
	}

	public String getCustomEntityName() {
		return customEntityName;
	}

	public void setCustomEntityName(String customEntityName) {
		this.customEntityName = customEntityName;
	}

	public boolean isHasWebAndConfiguratorAppAccess() {
		return hasWebAndConfiguratorAppAccess;
	}

	public void setHasWebAndConfiguratorAppAccess(
			boolean hasWebAndConfiguratorAppAccess) {
		this.hasWebAndConfiguratorAppAccess = hasWebAndConfiguratorAppAccess;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getEmpMailIdForWork() {
		return empMailIdForWork;
	}

	public void setEmpMailIdForWork(String empMailIdForWork) {
		this.empMailIdForWork = empMailIdForWork;
	}



	public boolean isHasConfiguratorAccess() {
		return hasConfiguratorAccess;
	}

	public void setHasConfiguratorAccess(boolean hasConfiguratorAccess) {
		this.hasConfiguratorAccess = hasConfiguratorAccess;
	}

	public boolean isEnableExternalIntegration() {
		return enableExternalIntegration;
	}

	public void setEnableExternalIntegration(boolean enableExternalIntegration) {
		this.enableExternalIntegration = enableExternalIntegration;
	}

	public String getSpoorsIntegrationPath() {
		return spoorsIntegrationPath;
	}

	public void setSpoorsIntegrationPath(String spoorsIntegrationPath) {
		this.spoorsIntegrationPath = spoorsIntegrationPath;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpGroupIds() {
		return empGroupIds;
	}

	public void setEmpGroupIds(String empGroupIds) {
		this.empGroupIds = empGroupIds;
	}

	public int getWebUserType() {
		return webUserType;
	}

	public void setWebUserType(int webUserType) {
		this.webUserType = webUserType;
	}

	public boolean isUserWithNoReportsAccess() {
		return userWithNoReportsAccess;
	}

	public void setUserWithNoReportsAccess(boolean userWithNoReportsAccess) {
		this.userWithNoReportsAccess = userWithNoReportsAccess;
	}

	public boolean isEnableGoogleDataStudio() {
		return enableGoogleDataStudio;
	}

	public void setEnableGoogleDataStudio(boolean enableGoogleDataStudio) {
		this.enableGoogleDataStudio = enableGoogleDataStudio;
	}

	public String getBrowserInfo() {
		return browserInfo;
	}

	public void setBrowserInfo(String browserInfo) {
		this.browserInfo = browserInfo;
	}

	public boolean isAdminUser() {
		return adminUser;
	}

	public void setAdminUser(boolean adminUser) {
		this.adminUser = adminUser;
	}

	public String getAdminUserLoginEmail() {
		return adminUserLoginEmail;
	}

	public void setAdminUserLoginEmail(String adminUserLoginEmail) {
		this.adminUserLoginEmail = adminUserLoginEmail;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLogText() {
		if(logText == null) {
			return "";
		}
		return logText;
	}

	public void setLogText(String logText) {
		if(logText == null) {
			logText = "";
		}
		this.logText = logText;
	}

	public Long getCompanySubscriptionPlanId() {
		return companySubscriptionPlanId;
	}

	public void setCompanySubscriptionPlanId(Long companySubscriptionPlanId) {
		this.companySubscriptionPlanId = companySubscriptionPlanId;
	}

	public String getLoggedEmpName() {
		return loggedEmpName;
	}

	public void setLoggedEmpName(String loggedEmpName) {
		this.loggedEmpName = loggedEmpName;
	}

	public List<Long> getFiltEmpGroupIds() {
		return filtEmpGroupIds;
	}

	public void setFiltEmpGroupIds(List<Long> filtEmpGroupIds) {
		this.filtEmpGroupIds = filtEmpGroupIds;
	}
	public boolean isSkipWelcomePage() {
		return skipWelcomePage;
	}

	public void setSkipWelcomePage(boolean skipWelcomePage) {
		this.skipWelcomePage = skipWelcomePage;
	}

	public long getEmpTypeId() {
		return empTypeId;
	}

	public void setEmpTypeId(long empTypeId) {
		this.empTypeId = empTypeId;
	}

	public boolean isWebLoginInfo() {
		return webLoginInfo;
	}

	public void setWebLoginInfo(boolean webLoginInfo) {
		this.webLoginInfo = webLoginInfo;
	}

	public Long getRootEmpId() {
		return rootEmpId;
	}

	public void setRootEmpId(Long rootEmpId) {
		this.rootEmpId = rootEmpId;
	}

	public boolean isEnableLeftMenu() {
		return enableLeftMenu;
	}

	public void setEnableLeftMenu(boolean enableLeftMenu) {
		this.enableLeftMenu = enableLeftMenu;
	}

	public boolean isRestrictConfiguratorAccess() {
		return restrictConfiguratorAccess;
	}

	public void setRestrictConfiguratorAccess(boolean restrictConfiguratorAccess) {
		this.restrictConfiguratorAccess = restrictConfiguratorAccess;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	
	public ServiceCallConstants getServiceCallConstants() {
		return serviceCallConstants;
	}
	
	public boolean hasRole(String role) {
		for (GrantedAuthority auth : authorities) {
			if (role.equals(auth.getAuthority())) {
				return true;
			}
		}

		return false;
	}
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}
