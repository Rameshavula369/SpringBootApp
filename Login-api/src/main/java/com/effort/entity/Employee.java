package com.effort.entity;




import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.effort.util.Api;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final long TYPE_FIELD = 1;
	public static final long TYPE_BACK_OFFICE = 2;
	public static final int SYSTEM_OPERATION_NOT_PERFORMED =0;
	public static final int SYSTEM_OPERATION_PERFORMED =1;
	public static final int SYSTEM_OPERATION_IN_PROGRESS =5;
	public static final int SYSTEM_OPERATION_FAILED =-2;
	
	public static final int TYPE_GIG_USER = 1;
	
	public static final int ACTIVE =1;
	public static final int DISABLED =0;
	//these constants for airtel 
	public static String EMP_DESIGNATION_TM="TM";
	public static String EMP_DESIGNATION_ZM="ZM";
	public static String EMP_DESIGNATION_FSE="FSE";
	public static String EMP_DESIGNATION_ZSM="ZSM";
	
	//Constants for LTF CSR
	public static String EMP_DESIGNATION_ADMIN = "ADMIN";
	public static String EMP_DESIGNATION_CEO = "CEO";
	public static String EMP_DESIGNATION_GROUP_HEAD = "CEO AND PM";
	public static String EMP_DESIGNATION_FINANCE = "FINANCE";
	public static String EMP_DESIGNATION_PROJECT = "PM";
	public static String EMP_DESIGNATION_CSR_HEAD = "CSR HEAD";
	public static String EMP_DESIGNATION_CSR_PM = "CSR PM";
	
	public static final int MOBILE_APP_ACCESS_ONLY = 0; //Mobile App Access
	public static final int WEB_USER_ONLY = 1; //Web & Mobile App Access
	public static final int CONFIGURATOR_ONLY = 2;
	public static final int WEB_USER_AND_CONFIGURATOR = 3; //All Access
	
	public static final int WEB_USER_ONLY_AND_NO_MOBILE_APP_ACCESS = 4; //Web App Only
	public static final int WEB_USER_ONLY_WITHOUT_REPORTS_AND_NO_MOBILE_APP_ACCESS = 5; //Web App Only Without Reports
	public static final int WEBLITE_ONLY_AND_NO_MOBILE_APP_ACCESS = 6; //Weblite Only
	public static final int WEB_USER_CONFIGURATOR_AND_WEBLITE = 7;
	
	
	public final static int EMPLOYEE_ID=1;
	public final static int EMPLOYEE_FIRST_NAME=2;
	public final static int EMPLOYEE_LAST_NAME=3;
	public final static int EMPLOYEE_PHONE=4;
	public final static int EMPLOYEE_EMAIL=5;
	public final static int EMPLOYEE_MANAGER_ID=6;
	public final static int EMPLOYEE_HOME_LOCATION=7;
	public final static int EMPLOYEE_WORK_LOCATION=8;
	public final static int EMPLOYEE_IS_MANGER=9;
	public final static int EMPLOYEE_STREET = 10;
	public final static int EMPLOYEE_AREA = 11;
	public final static int EMPLOYEE_CITY = 12;
	public final static int EMPLOYEE_DISTRICT = 13;
	public final static int EMPLOYEE_PINCODE = 14;
	public final static int EMPLOYEE_LANDMARK = 15;
	public final static int EMPLOYEE_COUNTRY = 16;
	public final static int EMPLOYEE_STATE = 17;

	private long empId;
	private String clientEmpId;
	private int companyId;
	private Long calendarId;
	private String empNo;
	private String empFirstName;
	private String empLastName;
	private long empTypeId = 1;
	private String empPhone;
	private String empAddressStreet;
	private String empAddressArea;
	private String empAddressCity;
	private String empAddressDistrict;
	private String empAddressPincode;
	private String empAddressLandMark;
	private String empAddressState;
	private String empAddressCountry;
	private String empEmail;
	private String imei;
	private String homeLat;
	private String homeLong;
	private String workLat;
	private String workLong;
	private Long managerId;
	private String managerNo;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Integer timezoneId; // timezone
	@JsonProperty(access = Access.READ_ONLY)
	private String timeZoneDisplayName;
	//@JsonProperty(access = Access.WRITE_ONLY)
	private Integer timezoneOffset;
	private int rank;
	private Boolean isUpdate;
	private String empTypeName;
	private String empMappedGroupIds;
	private String empMappedGroupNames;
	private String empCheckInTarget;
	private boolean employeeCheckList = false;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String checksum;
	
	private String empFiltName;
	private String lastSignoutTime;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String accepted;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String rejected;
	private String jobLabel = "Job";
	private boolean openMap = false;
	private String pushId;

	private String lastSyncTime;

	private boolean canOverRideMaxJsonLimit;
	private Boolean provisioning;
		private boolean manager;
		
		@JsonProperty(access = Access.READ_ONLY)
		private int status=-1;
		
		private int createWebUser = 0;
		private int type;
		@JsonProperty(access = Access.READ_ONLY)
		private String lasteSyncTime;
		private boolean directAccess;
		
		private boolean avialableForJob;
		private double distanceFormJob;
		private String lastTrackTime;
		
		private boolean createWebPanelAccount;
		private boolean allCustomerInSync;
		private String skillEntityIds;
		private Long empFormId;
		private String skillEntityIdentifiers;
		
		private boolean onLeave=false;
		
		private boolean onWeekOff=false;
		private boolean onHoliday=false;
		
		private String createdDate;
		private String modifiedDate;
		

		private String lastActivityTime;
		
		private Long appId=(long) 0;
		
		private String empStatus= "-1";
		private Long empBranchEntityId;
		private String lastKnownLocation;
		private String lastKnownLocationTime;
		// added Employee Photo variables
			private Long empMediaId=0l;
			
		private Long empGroupId;
		
		private String empType;
		
		private String provisioningStatus;
		
		private String empGroupIds;
		
		private Integer empIsdCode;
		
		
		private String customerId;
		private String knoxPackages;
		
		private String effortUsername;
		private String effortPassword;
		
		private boolean adUser;
		private String adUsername;
		private String adServerCode;
		
		private String encryptionKey;
		
		private Boolean isMapped=false;
		private String empBranchIds;
		private String webUserStatus;
		
		
		private String mapToSettingModifiedTime;
		
		private Long territoryId;// used for employee filter in grid 
		private String territoryIds;// used for employee filter in grid , used for employee export
		
		private String fieldsListFilteringMap;
		private String location;
		
		private boolean keepSessionAlive;
		private String territoryNos;		
		
		@JsonProperty(access = Access.WRITE_ONLY)
		private String error;
		
		private String deviceUpdatedLastSyncTime; // deviceUpdatedLastSyncTime in EmployeeDevices table
	
		//This effortToken key is used only while init request by the client.
		private String effortToken;
		private String empMappedTerritoryNames;
		
		private String role;
		
		private String managerName;
		
		private Integer sourceOfAction;
		
		private boolean enableMultiUserLogin = false;
		
		private boolean blockedByOpsForSendingCustomerToOfflineUse = false;
		
		private String roleIds;
		private int row;
		private String adNameId;
		private String managerIds;
		private int empStructure;
		private List<Long> allUnderEmployees;
		private String zoneId;
		
		private String createdTime;
		private Long employeeCreatedBy;
		private Long employeeModifiedBy;
		private String createdByName;
		

		
		private Integer manuallyBlocked;
		private Integer autoBlocked;
		private Integer blockedDueToListItemExpiry;
		
		private String circleTypeTerritoryIds;
		private String signInTerritoryNos;
		private boolean generateQrCode = false; //used for generating QR code, userName & password
		private boolean eligibleForMobileAccess = false;
		private String authority;

		private String batteryLevel;
		private String marutiZoneValue;
		private Long durationInSeconds;
		private Double distanceInMeters;
		private String distanceText;
		private String durationText;
		
		private Integer employeeSyncHoldTimeInSecs;
		private String stateEntityId;
		private String cityEntityId;
		private Long empMapCustomerId;
		
		//blocked and disabled variable are used for EmployeesAutoBlockedAndDisabled
		private Short blocked;
		private Short disabled;
		
		private String marutiZonefieldValue;
		private String liveMonitorSummaryIcon;
		private Double travelTime;
		private int serviceType;
		private Integer sourceOfModification;
		
		private int enableResolveDistanceForActivityLocations;
		private boolean inviteEmployee;
		private String signInTime;
		private String signInSignOutDiff;
		private boolean empGraphFilter;
		private String graphFilterEmpIds;
		private String employeeNote;
		private String managerNote;
		private String appliedON;
		private long incompleteWorksCount;

		private String empJoiningDate;
		private String empProhibitionPeriod;
		
		private boolean active;
		private String employeesLabel;
		private String accessType;
		private boolean skipWelcomePage;
		
		private String 	expiryDate;
		private Integer notifyBefore;
		private String 	notifyToEmails;
		private Integer isExpired;
		private String  expiredDate;
		private int employeeMentType;
		private boolean fromCompanyCreation;
		private String subscriptionTime;
		private String expiryTime;
		
		private String webUserUserName;
		private String webUserPasswordEncrypted;
		private Integer appExpiryLogoutDuration;
		private boolean webLoginInfo;
		private String employeeMentStatusType = "-1";
		private Boolean isSignedIn;
		private String isSignedInToday;
		private String expiredListItems;
		
		private boolean blockMobileActivities;
		private boolean managerValidation;
		
		private double employeeUsageInfo;
		private boolean stopPushNotificationsAfterWorkingHours = true;
		
		private boolean enableOrDisable;
		private int employeeLeaveCount;
		private int employeeDayPlanCount;
		private boolean webUserActive;
		@JsonProperty(access = Access.READ_ONLY)
		private Timestamp createTime;
		private Timestamp modifyTime;
		
		public Integer getSourceOfAction() {
			return sourceOfAction;
		}
		public void setSourceOfAction(Integer sourceOfAction) {
			this.sourceOfAction = sourceOfAction;
		}
		
		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		
		public int getCreateWebUser() {
			return createWebUser;
		}

		public void setCreateWebUser(int createWebUser) {
			this.createWebUser = createWebUser;
		}
		
		

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}
		
		public String getCreatedDate() {
			
			return createdDate;
		}
		
		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}
		
		
		public void setModifiedDate(String modifiedDate) {
			this.modifiedDate = modifiedDate;
		}
		
		
		public String getSkillEntityIdentifiers() {
			return skillEntityIdentifiers;
		}
		public void setSkillEntityIdentifiers(String skillEntityIdentifiers) {
			this.skillEntityIdentifiers = skillEntityIdentifiers;
		}
		public String getSkillEntityIds() {
			return skillEntityIds;
		}
		public void setSkillEntityIds(String skillEntityIds) {
			this.skillEntityIds = skillEntityIds;
		}
		
		public Long getEmpFormId() {
			return empFormId;
		}
		public void setEmpFormId(Long empFormId) {
			this.empFormId = empFormId;
		}
	public String getTimeZoneDisplayName() {
	    return timeZoneDisplayName;
	}
	
	public void setTimeZoneDisplayName(String timeZoneDisplayName) {
	    this.timeZoneDisplayName = timeZoneDisplayName;
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	public long getId() {
		return getEmpId();
	}
	
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public String getClientEmpId() {
		return clientEmpId;
	}
	public void setClientEmpId(String clientEmpId) {
		this.clientEmpId = clientEmpId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public Long getCalendarId() {
		return calendarId;
	}
	public void setCalendarId(Long calendarId) {
		this.calendarId = calendarId;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getEmpFirstName() {
		return empFirstName;
	}
	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}
	public String getEmpLastName() {
		return empLastName;
	}
	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}
	public long getEmpTypeId() {
		return empTypeId;
	}
	public void setEmpTypeId(long empTypeId) {
		this.empTypeId = empTypeId;
	}
	public String getEmpPhone() {
		return empPhone;
	}
	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}
	public String getEmpAddressStreet() {
		return empAddressStreet;
	}
	public void setEmpAddressStreet(String empAddressStreet) {
		this.empAddressStreet = empAddressStreet;
	}
	public String getEmpAddressArea() {
		return empAddressArea;
	}
	public void setEmpAddressArea(String empAddressArea) {
		this.empAddressArea = empAddressArea;
	}
	public String getEmpAddressCity() {
		return empAddressCity;
	}
	public void setEmpAddressCity(String empAddressCity) {
		this.empAddressCity = empAddressCity;
	}
	public String getEmpAddressPincode() {
		return empAddressPincode;
	}
	public void setEmpAddressPincode(String empAddressPincode) {
		this.empAddressPincode = empAddressPincode;
	}
	public String getEmpAddressLandMark() {
		return empAddressLandMark;
	}

	public void setEmpAddressLandMark(String empAddressLandMark) {
		this.empAddressLandMark = empAddressLandMark;
	}

	public String getEmpAddressState() {
		return empAddressState;
	}

	public void setEmpAddressState(String empAddressState) {
		this.empAddressState = empAddressState;
	}

	public String getEmpAddressCountry() {
		return empAddressCountry;
	}

	public void setEmpAddressCountry(String empAddressCountry) {
		this.empAddressCountry = empAddressCountry;
	}

	public String getEmpEmail() {
		return empEmail;
	}
	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getHomeLat() {
		return homeLat;
	}
	public void setHomeLat(String homeLat) {
		this.homeLat = homeLat;
	}
	public String getHomeLong() {
		return homeLong;
	}
	public void setHomeLong(String homeLong) {
		this.homeLong = homeLong;
	}
	public String getWorkLat() {
		return workLat;
	}
	public void setWorkLat(String workLat) {
		this.workLat = workLat;
	}
	public String getWorkLong() {
		return workLong;
	}
	public void setWorkLong(String workLong) {
		this.workLong = workLong;
	}
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	public Boolean getProvisioning() {
		return provisioning;
	}
	public void setProvisioning(Boolean provisioning) {
		this.provisioning = provisioning;
	}
	public boolean isManager() {
		return manager;
	}
	public void setManager(boolean manager) {
		this.manager = manager;
	}
	

	

	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	
	@JsonIgnore
	public int getType() {
		return type;
	}
	@JsonIgnore
	public void setType(int type) {
		this.type = type;
	}
	
	public String getLasteSyncTime() {
		return lasteSyncTime;
	}
	
	public void setLasteSyncTime(String lasteSyncTime) {
		this.lasteSyncTime = lasteSyncTime;
	}
	
	@JsonIgnore
	public boolean isDirectAccess() {
		return directAccess;
	}
	@JsonIgnore
	public void setDirectAccess(boolean directAccess) {
		this.directAccess = directAccess;
	}

	public String getEmpName(){
		String empName = "";
		
		if(!Api.isEmptyString(getEmpFirstName())){
			empName += getEmpFirstName();
		}
		
		if(!Api.isEmptyString(getEmpLastName())){
			if(!Api.isEmptyString(empName)){
				empName += (" " + getEmpLastName());
			} else {
				empName += getEmpLastName();
			}
		}
		
		return empName;
	}
	
	public String getEmpTypeName(){
		if(getEmpTypeId() == TYPE_FIELD){
			return "Field";
		} else if(getEmpTypeId() == TYPE_BACK_OFFICE){
			return "Back office";
		}
		
		return "";
	}
	
	public boolean isAvialableForJob() {
		return avialableForJob;
	}

	public void setAvialableForJob(boolean avialableForJob) {
		this.avialableForJob = avialableForJob;
	}

	public double getDistanceFormJob() {
		return distanceFormJob;
	}

	public void setDistanceFormJob(double distanceFormJob) {
		this.distanceFormJob = distanceFormJob;
	}
	
	

	
	@Override
	public String toString() {
		return empId+"";
	}
	
	public String toCSV() {
		return "[empId=" + empId + ", companyId=" + companyId
				+ ", calendarId=" + calendarId + ", empNo=" + empNo
				+ ", empFirstName=" + empFirstName + ", empLastName="
				+ empLastName + ", empPhone=" + empPhone
				+ ", empAddressStreet=" + empAddressStreet
				+ ", empAddressArea=" + empAddressArea + ", empAddressCity="
				+ empAddressCity + ", empAddressDistrict="+ empAddressDistrict + ", empAddressPincode=" + empAddressPincode
				+ ", empEmail=" + empEmail + ", imei=" + imei + ", managerId="
				+ managerId + ", provisioning=" + provisioning
				+ ", manager=" + manager + ", status=" + status
				+ ", createWebUser=" + createWebUser + "]";
	}

	
	public String getLastTrackTime() {
		return lastTrackTime;
	}

	public void setLastTrackTime(String lastTrackTime) {
		this.lastTrackTime = lastTrackTime;
	}
    private String designation;

    
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public Integer getTimezoneId() {
		return timezoneId;
	}

	public void setTimezoneId(Integer timezoneId) {
		this.timezoneId = timezoneId;
	}
	
	public Integer getTimezoneOffset() {
		return timezoneOffset;
	}
	public void setTimezoneOffset(Integer timezoneOffset) {
		this.timezoneOffset = timezoneOffset;
	}
	public int getRank() {
	    return rank;
	}
	public void setRank(int rank) {
	    this.rank = rank;
	}
	public boolean isCreateWebPanelAccount() {
		return createWebPanelAccount;
	}
	public void setCreateWebPanelAccount(boolean createWebPanelAccount) {
		this.createWebPanelAccount = createWebPanelAccount;
	}
	public boolean isAllCustomerInSync() {
		return allCustomerInSync;
	}
	public void setAllCustomerInSync(boolean allCustomerInSync) {
		this.allCustomerInSync = allCustomerInSync;
	}
	public Boolean getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	
	public String getEmpMappedGroupIds() {
		return empMappedGroupIds;
	}
	public void setEmpMappedGroupIds(String empMappedGroupIds) {
		this.empMappedGroupIds = empMappedGroupIds;
	}
	
	public boolean isEmployeeCheckList() {
		return employeeCheckList;
	}
	public void setEmployeeCheckList(boolean employeeCheckList) {
		this.employeeCheckList = employeeCheckList;
	}

	public String getEmpAddressDistrict() {
		return empAddressDistrict;
	}
	public void setEmpAddressDistrict(String empAddressDistrict) {
		this.empAddressDistrict = empAddressDistrict;
	}
	public boolean isOnLeave() {
		return onLeave;
	}
	public void setOnLeave(boolean onLeave) {
		this.onLeave = onLeave;
	}

	
	public String getLastActivityTime() {
		return lastActivityTime;
	}

	public void setLastActivityTime(String lastActivityTime) {
		this.lastActivityTime = lastActivityTime;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
	
	public String getEmpStatus() {
		return empStatus;
	}
	
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}
	
	public String getLastKnownLocation() {
		return lastKnownLocation;
	}
	
	public void setLastKnownLocation(String lastKnownLocation) {
		this.lastKnownLocation = lastKnownLocation;
	}

	public Long getEmpMediaId() {
		return empMediaId;
	}

	public void setEmpMediaId(Long empMediaId) {
		this.empMediaId = empMediaId;
	}

	public String getEmpCheckInTarget() {
		return empCheckInTarget;
	}

	public void setEmpCheckInTarget(String empCheckInTarget) {
		this.empCheckInTarget = empCheckInTarget;
	}

	
	

	
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
	

	public Long getEmpBranchEntityId() {
		return empBranchEntityId;
	}

	public void setEmpBranchEntityId(Long empBranchEntityId) {
		this.empBranchEntityId = empBranchEntityId;
	}

	public String getEmpFiltName() {
		return empFiltName;
	}

	public void setEmpFiltName(String empFiltName) {
		this.empFiltName = empFiltName;
	}


	public Long getEmpGroupId() {
		return empGroupId;
	}

	public void setEmpGroupId(Long empGroupId) {
		this.empGroupId = empGroupId;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public String getProvisioningStatus() {
		return provisioningStatus;
	}

	public void setProvisioningStatus(String provisioningStatus) {
		this.provisioningStatus = provisioningStatus;
	}

	public String getEmpGroupIds() {
		return empGroupIds;
	}

	public void setEmpGroupIds(String empGroupIds) {
		this.empGroupIds = empGroupIds;
	}
	
	public String getAccepted() {
		return accepted;
	}

	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}
	
	public String getRejected() {
		return rejected;
	}

	public void setRejected(String rejected) {
		this.rejected = rejected;
	}

	public String getManagerNo() {
		return managerNo;
	}

	public void setManagerNo(String managerNo) {
		this.managerNo = managerNo;
	}

	public Integer getEmpIsdCode() {
		return empIsdCode;
	}

	public void setEmpIsdCode(Integer empIsdCode) {
		this.empIsdCode = empIsdCode;
	}


	@JsonIgnore
	public String getEmpMappedGroupNames() {
		return empMappedGroupNames;
	}
	@JsonIgnore
	public void setEmpMappedGroupNames(String empMappedGroupNames) {
		this.empMappedGroupNames = empMappedGroupNames;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getKnoxPackages() {
		return knoxPackages;
	}

	public void setKnoxPackages(String knoxPackages) {
		this.knoxPackages = knoxPackages;
	}
	

	
	public boolean isOnWeekOff() {
		return onWeekOff;
	}
	
	public void setOnWeekOff(boolean onWeekOff) {
		this.onWeekOff = onWeekOff;
	}
	@JsonIgnore
	public boolean isOnHoliday() {
		return onHoliday;
	}
	@JsonIgnore
	public void setOnHoliday(boolean onHoliday) {
		this.onHoliday = onHoliday;
	}


	@JsonIgnore
	public String getEffortUsername() {
		return effortUsername;
	}

	@JsonIgnore
	public void setEffortUsername(String effortUsername) {
		this.effortUsername = effortUsername;
	}

	@JsonIgnore
	public String getEffortPassword() {
		return effortPassword;
	}

	@JsonIgnore
	public void setEffortPassword(String effortPassword) {
		this.effortPassword = effortPassword;
	}

	@JsonIgnore
	public boolean isAdUser() {
		return adUser;
	}

	@JsonIgnore
	public void setAdUser(boolean adUser) {
		this.adUser = adUser;
	}

	@JsonIgnore
	public String getAdUsername() {
		return adUsername;
	}

	@JsonIgnore
	public void setAdUsername(String adUsername) {
		this.adUsername = adUsername;
	}

	@JsonIgnore
	public String getAdServerCode() {
		return adServerCode;
	}

	@JsonIgnore
	public void setAdServerCode(String adServerCode) {
		this.adServerCode = adServerCode;
	}

	@JsonIgnore
	public String getEncryptionKey() {
		return encryptionKey;
	}

	@JsonIgnore
	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	@JsonIgnore
	public Boolean getIsMapped() {
		if(isMapped == null){
			isMapped = false;
		}
		return isMapped;
	}
	@JsonIgnore
	public void setIsMapped(Boolean isMapped) {
		if(isMapped == null){
			isMapped = false;
		}
		this.isMapped = isMapped;
	}

	public String getEmpBranchIds() {
		return empBranchIds;
	}

	public void setEmpBranchIds(String empBranchIds) {
		this.empBranchIds = empBranchIds;
	}

	public String getWebUserStatus() {
		return webUserStatus;
	}

	public void setWebUserStatus(String webUserStatus) {
		this.webUserStatus = webUserStatus;
	}

	

	public String getMapToSettingModifiedTime() {
		return mapToSettingModifiedTime;
	}

	public void setMapToSettingModifiedTime(String mapToSettingModifiedTime) {
		this.mapToSettingModifiedTime = mapToSettingModifiedTime;
	}

	public Long getTerritoryId() {
		return territoryId;
	}

	public void setTerritoryId(Long territoryId) {
		this.territoryId = territoryId;
	}

	public String getTerritoryIds() {
		return territoryIds;
	}

	public void setTerritoryIds(String territoryIds) {
		this.territoryIds = territoryIds;
	}

	public String getFieldsListFilteringMap() {
		return fieldsListFilteringMap;
	}

	public void setFieldsListFilteringMap(String fieldsListFilteringMap) {
		this.fieldsListFilteringMap = fieldsListFilteringMap;
	}

	public String getLastKnownLocationTime() {
		return lastKnownLocationTime;
	}

	public void setLastKnownLocationTime(String lastKnownLocationTime) {
		this.lastKnownLocationTime = lastKnownLocationTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isKeepSessionAlive() {
		return keepSessionAlive;
	}

	public void setKeepSessionAlive(boolean keepSessionAlive) {
		this.keepSessionAlive = keepSessionAlive;
	}

	public String getDeviceUpdatedLastSyncTime() {
		return deviceUpdatedLastSyncTime;
	}

	public void setDeviceUpdatedLastSyncTime(String deviceUpdatedLastSyncTime) {
		this.deviceUpdatedLastSyncTime = deviceUpdatedLastSyncTime;
	}

	public String getEffortToken() {
		return effortToken;
	}

	public void setEffortToken(String effortToken) {
		this.effortToken = effortToken;
	}

	public String getEmpMappedTerritoryNames() {
		return empMappedTerritoryNames;
	}

	public void setEmpMappedTerritoryNames(String empMappedTerritoryNames) {
		this.empMappedTerritoryNames = empMappedTerritoryNames;
	}
	public String getTerritoryNos() {
		return territoryNos;
	}

	public void setTerritoryNos(String territoryNos) {
		this.territoryNos = territoryNos;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public boolean isCanOverRideMaxJsonLimit() {
		return canOverRideMaxJsonLimit;
	}

	public void setCanOverRideMaxJsonLimit(boolean canOverRideMaxJsonLimit) {
		this.canOverRideMaxJsonLimit = canOverRideMaxJsonLimit;
	}
	
	public boolean isEnableMultiUserLogin() {
		return enableMultiUserLogin;
	}
	
	public void setEnableMultiUserLogin(boolean enableMultiUserLogin) {
		this.enableMultiUserLogin = enableMultiUserLogin;
	}
	public boolean isBlockedByOpsForSendingCustomerToOfflineUse() {
		return blockedByOpsForSendingCustomerToOfflineUse;
	}
	public void setBlockedByOpsForSendingCustomerToOfflineUse(
			boolean blockedByOpsForSendingCustomerToOfflineUse) {
		this.blockedByOpsForSendingCustomerToOfflineUse = blockedByOpsForSendingCustomerToOfflineUse;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public String getAdNameId() {
		return adNameId;
	}
	public void setAdNameId(String adNameId) {
		this.adNameId = adNameId;
	}
	public String getManagerIds() {
		return managerIds;
	}
	public void setManagerIds(String managerIds) {
		this.managerIds = managerIds;
	}
	public int getEmpStructure() {
		return empStructure;
	}
	public void setEmpStructure(int empStructure) {
		this.empStructure = empStructure;
	}
	public List<Long> getAllUnderEmployees() {
		return allUnderEmployees;
	}
	public void setAllUnderEmployees(List<Long> allUnderEmployees) {
		this.allUnderEmployees = allUnderEmployees;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	
	public Long getEmployeeCreatedBy() {
		return employeeCreatedBy;
	}
	public void setEmployeeCreatedBy(Long employeeCreatedBy) {
		this.employeeCreatedBy = employeeCreatedBy;
	}
	public Long getEmployeeModifiedBy() {
		return employeeModifiedBy;
	}
	public void setEmployeeModifiedBy(Long employeeModifiedBy) {
		this.employeeModifiedBy = employeeModifiedBy;
	}
	
	

	public Integer getManuallyBlocked() {
		return manuallyBlocked;
	}
	public void setManuallyBlocked(Integer manuallyBlocked) {
		this.manuallyBlocked = manuallyBlocked;
	}
	public Integer getAutoBlocked() {
		return autoBlocked;
	}
	public void setAutoBlocked(Integer autoBlocked) {
		this.autoBlocked = autoBlocked;
	}
	
	public String getCircleTypeTerritoryIds() {
		return circleTypeTerritoryIds;
	}
	public void setCircleTypeTerritoryIds(String circleTypeTerritoryIds) {
		this.circleTypeTerritoryIds = circleTypeTerritoryIds;
	}
	
	public String getSignInTerritoryNos() {
		return signInTerritoryNos;
	}
	public void setSignInTerritoryNos(String signInTerritoryNos) {
		this.signInTerritoryNos = signInTerritoryNos;
	}
	public boolean isGenerateQrCode() {
		return generateQrCode;
	}
	public void setGenerateQrCode(boolean generateQrCode) {
		this.generateQrCode = generateQrCode;
	}
	public boolean isEligibleForMobileAccess() {
		return eligibleForMobileAccess;
	}
	public void setEligibleForMobileAccess(boolean eligibleForMobileAccess) {
		this.eligibleForMobileAccess = eligibleForMobileAccess;
	}
	
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getBatteryLevel() {
		return batteryLevel;
	}
	public void setBatteryLevel(String batteryLevel) {
		this.batteryLevel = batteryLevel;
	}
	public String getMarutiZoneValue() {
		return marutiZoneValue;
	}
	public void setMarutiZoneValue(String marutiZoneValue) {
		this.marutiZoneValue = marutiZoneValue;
	}
	public Long getDurationInSeconds() {
		return durationInSeconds;
	}
	public void setDurationInSeconds(Long durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}
	public Double getDistanceInMeters() {
		return distanceInMeters;
	}
	public void setDistanceInMeters(Double distanceInMeters) {
		this.distanceInMeters = distanceInMeters;
	}
	public String getDistanceText() {
		return distanceText;
	}
	public void setDistanceText(String distanceText) {
		this.distanceText = distanceText;
	}
	public String getDurationText() {
		return durationText;
	}
	public void setDurationText(String durationText) {
		this.durationText = durationText;
	}
	public Integer getEmployeeSyncHoldTimeInSecs() {
		return employeeSyncHoldTimeInSecs;
	}
	public void setEmployeeSyncHoldTimeInSecs(Integer employeeSyncHoldTimeInSecs) {
		this.employeeSyncHoldTimeInSecs = employeeSyncHoldTimeInSecs;
	}
	public Short getBlocked() {
		return blocked;
	}
	public void setBlocked(Short blocked) {
		this.blocked = blocked;
	}
	public Short getDisabled() {
		return disabled;
	}
	public void setDisabled(Short disabled) {
		this.disabled = disabled;
	}
	
	public String getStateEntityId() {
		return stateEntityId;
	}
	public void setStateEntityId(String stateEntityId) {
		this.stateEntityId = stateEntityId;
	}
	public String getCityEntityId() {
		return cityEntityId;
	}
	public void setCityEntityId(String cityEntityId) {
		this.cityEntityId = cityEntityId;
	}
	public Long getEmpMapCustomerId() {
		return empMapCustomerId;
	}
	public void setEmpMapCustomerId(Long empMapCustomerId) {
		this.empMapCustomerId = empMapCustomerId;
	}
	public String getMarutiZonefieldValue() {
		return marutiZonefieldValue;
	}
	public void setMarutiZonefieldValue(String marutiZonefieldValue) {
		this.marutiZonefieldValue = marutiZonefieldValue;
	}
	public String getLiveMonitorSummaryIcon() {
		return liveMonitorSummaryIcon;
	}
	public void setLiveMonitorSummaryIcon(String liveMonitorSummaryIcon) {
		this.liveMonitorSummaryIcon = liveMonitorSummaryIcon;
	}
	public String getJobLabel() {
		return jobLabel;
	}
	public void setJobLabel(String jobLabel) {
		this.jobLabel = jobLabel;
	}
	public boolean isOpenMap() {
		return openMap;
	}
	public void setOpenMap(boolean openMap) {
		this.openMap = openMap;
	}
	public Double getTravelTime() {
		return travelTime;
	}
	public void setTravelTime(Double travelTime) {
		this.travelTime = travelTime;
	}
	public int getServiceType() {
		return serviceType;
	}
	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}
	
	public Integer getSourceOfModification() {
		return sourceOfModification;
	}
	public void setSourceOfModification(Integer sourceOfModification) {
		this.sourceOfModification = sourceOfModification;
	}
	public int getEnableResolveDistanceForActivityLocations() {
		return enableResolveDistanceForActivityLocations;
	}
	public void setEnableResolveDistanceForActivityLocations(int enableResolveDistanceForActivityLocations) {
		this.enableResolveDistanceForActivityLocations = enableResolveDistanceForActivityLocations;
	}
	public boolean isInviteEmployee() {
		return inviteEmployee;
	}
	public void setInviteEmployee(boolean inviteEmployee) {
		this.inviteEmployee = inviteEmployee;
	}
	public String getSignInTime() {
		return signInTime;
	}
	public void setSignInTime(String signInTime) {
		this.signInTime = signInTime;
	}
	public String getSignInSignOutDiff() {
		return signInSignOutDiff;
	}
	public void setSignInSignOutDiff(String signInSignOutDiff) {
		this.signInSignOutDiff = signInSignOutDiff;
	}
	public boolean isEmpGraphFilter() {
		return empGraphFilter;
	}
	public void setEmpGraphFilter(boolean empGraphFilter) {
		this.empGraphFilter = empGraphFilter;
	}
	public String getGraphFilterEmpIds() {
		return graphFilterEmpIds;
	}
	public void setGraphFilterEmpIds(String graphFilterEmpIds) {
		this.graphFilterEmpIds = graphFilterEmpIds;
	}
	public String getEmployeeNote() {
		return employeeNote;
	}
	public void setEmployeeNote(String employeeNote) {
		this.employeeNote = employeeNote;
	}
	public String getManagerNote() {
		return managerNote;
	}
	public void setManagerNote(String managerNote) {
		this.managerNote = managerNote;
	}
	public String getAppliedON() {
		return appliedON;
	}
	public void setAppliedON(String appliedON) {
		this.appliedON = appliedON;
	}
	public long getIncompleteWorksCount() {
		return incompleteWorksCount;
	}
	public void setIncompleteWorksCount(long incompleteWorksCount) {
		this.incompleteWorksCount = incompleteWorksCount;
	}
	
	public String getEmpJoiningDate() {
		return empJoiningDate;
	}
	public void setEmpJoiningDate(String empJoiningDate) {
		this.empJoiningDate = empJoiningDate;
	}
	public String getEmpProhibitionPeriod() {
		return empProhibitionPeriod;
	}
	public void setEmpProhibitionPeriod(String empProhibitionPeriod) {
		this.empProhibitionPeriod = empProhibitionPeriod;
	}
		
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Integer getNotifyBefore() {
		return notifyBefore;
	}
	public void setNotifyBefore(Integer notifyBefore) {
		this.notifyBefore = notifyBefore;
	}
	public String getNotifyToEmails() {
		return notifyToEmails;
	}
	public void setNotifyToEmails(String notifyToEmails) {
		this.notifyToEmails = notifyToEmails;
	}
	public Integer getIsExpired() {
		return isExpired;
	}
	public void setIsExpired(Integer isExpired) {
		this.isExpired = isExpired;
	}
	public String getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getEmployeesLabel() {
		return employeesLabel;
	}
	public void setEmployeesLabel(String employeesLabel) {
		this.employeesLabel = employeesLabel;
	}
	
	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public boolean isSkipWelcomePage() {
		return skipWelcomePage;
	}
	public void setSkipWelcomePage(boolean skipWelcomePage) {
		this.skipWelcomePage = skipWelcomePage;
	}
	public String getPushId() {
		return pushId;
	}
	public void setPushId(String pushId) {
		this.pushId = pushId;
	}
	public int getEmployeeMentType() {
		return employeeMentType;
	}
	public void setEmployeeMentType(int employeeMentType) {
		this.employeeMentType = employeeMentType;
	}
	public boolean isFromCompanyCreation() {
		return fromCompanyCreation;
	}
	public void setFromCompanyCreation(boolean fromCompanyCreation) {
		this.fromCompanyCreation = fromCompanyCreation;
	}
	public String getSubscriptionTime() {
		return subscriptionTime;
	}
	public void setSubscriptionTime(String subscriptionTime) {
		this.subscriptionTime = subscriptionTime;
	}
	public String getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getWebUserUserName() {
		return webUserUserName;
	}
	public void setWebUserUserName(String webUserUserName) {
		this.webUserUserName = webUserUserName;
	}
	public String getWebUserPasswordEncrypted() {
		return webUserPasswordEncrypted;
	}
	public void setWebUserPasswordEncrypted(String webUserPasswordEncrypted) {
		this.webUserPasswordEncrypted = webUserPasswordEncrypted;
	}
	public Integer getAppExpiryLogoutDuration() {
		return appExpiryLogoutDuration;
	}
	public void setAppExpiryLogoutDuration(Integer appExpiryLogoutDuration) {
		this.appExpiryLogoutDuration = appExpiryLogoutDuration;
	}
	public boolean isWebLoginInfo() {
		return webLoginInfo;
	}
	public void setWebLoginInfo(boolean webLoginInfo) {
		this.webLoginInfo = webLoginInfo;
	}
	public String getEmployeeMentStatusType() {
		return employeeMentStatusType;
	}
	public void setEmployeeMentStatusType(String employeeMentStatusType) {
		this.employeeMentStatusType = employeeMentStatusType;
	}
	public Boolean getIsSignedIn() {
		return isSignedIn;
	}
	public void setIsSignedIn(Boolean isSignedIn) {
		this.isSignedIn = isSignedIn;
	}
	public String getIsSignedInToday() {
		return isSignedInToday;
	}
	public void setIsSignedInToday(String isSignedInToday) {
		this.isSignedInToday = isSignedInToday;
	}
	public boolean isBlockMobileActivities() {
		return blockMobileActivities;
	}
	public void setBlockMobileActivities(boolean blockMobileActivities) {
		this.blockMobileActivities = blockMobileActivities;
	}
	
	
	public Integer getBlockedDueToListItemExpiry() {
		return blockedDueToListItemExpiry;
	}
	public void setBlockedDueToListItemExpiry(Integer blockedDueToListItemExpiry) {
		this.blockedDueToListItemExpiry = blockedDueToListItemExpiry;
	}
	public String getExpiredListItems() {
		return expiredListItems;
	}
	public void setExpiredListItems(String expiredListItems) {
		this.expiredListItems = expiredListItems;
	}
	public boolean isManagerValidation() {
		return managerValidation;
	}
	public void setManagerValidation(boolean managerValidation) {
		this.managerValidation = managerValidation;
	}
	public double getEmployeeUsageInfo() {
		return employeeUsageInfo;
	}
	public void setEmployeeUsageInfo(double employeeUsageInfo) {
		this.employeeUsageInfo = employeeUsageInfo;
	}
	public boolean isStopPushNotificationsAfterWorkingHours() {
		return stopPushNotificationsAfterWorkingHours;
	}
	public void setStopPushNotificationsAfterWorkingHours(boolean stopPushNotificationsAfterWorkingHours) {
		this.stopPushNotificationsAfterWorkingHours = stopPushNotificationsAfterWorkingHours;
	}

	public int getEmployeeLeaveCount() {
		return employeeLeaveCount;
	}
	public void setEmployeeLeaveCount(int employeeLeaveCount) {
		this.employeeLeaveCount = employeeLeaveCount;
	}
	public int getEmployeeDayPlanCount() {
		return employeeDayPlanCount;
	}
	public void setEmployeeDayPlanCount(int employeeDayPlanCount) {
		this.employeeDayPlanCount = employeeDayPlanCount;
	}
	
	
	public boolean isEnableOrDisable() {
		return enableOrDisable;
	}
	public void setEnableOrDisable(boolean enableOrDisable) {
		this.enableOrDisable = enableOrDisable;
	}
	public boolean isWebUserActive() {
		return webUserActive;
	}
	public void setWebUserActive(boolean webUserActive) {
		this.webUserActive = webUserActive;
	}
	
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@JsonIgnore
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}