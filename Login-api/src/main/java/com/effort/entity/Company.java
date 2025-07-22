package com.effort.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class Company {
	private int companyId;
	private String companyName;
	private String companyPhone;
	private String companyAddressStreet;
	private String companyAddressArea;
	private String companyAddressCity;
	private String companyAddressPincode;
	private String companyAddressLandMark;
	private String companyAddressState;
	private String companyAddressCountry;
	private String primaryContactFirstName;
	private String primaryContactLastName;
	private String primaryContactEmail;
	private String initialContactEmail;	
	private String primaryContactPhone;
	private String primaryContactTitle;
	private String secondaryContactFirstName;
	private String secondaryContactLastName;
	private String secondaryContactEmail;
	private String secondaryContactPhone;
	private String secondaryContactTitle;
	private boolean active;
	
	private String webLogoUrl;
	private String webLogoUrlMimeType;
	private String appLogoUrl;
	private String appLogoUrlMimeType;
	
	private boolean testCompany;
	
	private String createTime;
	private String modifiedTime;
	
	private Double totalSizeUsed;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Integer billCyclePeriod;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Integer duePeriod;
	private String companyGstNo;
	
	private boolean canAccessAdUser;
	private boolean internalTest;
	
	private String extraMailIdsForProductiveInfo;
	private boolean ncellLogin;
	
	private boolean invoiceDownloadEnabled;
	private boolean enableActivityBasedLocation;
	private boolean blockActivityBasedLocation;
	
	private boolean hardDeleteDeviceData;
	private boolean canAccessOpp;
	private Long companySubscriptionPlanId;
	private int requestPlanType;
	private boolean enableProformaInvoice;
	private Long gigUserAppId;
	
	private Long gigApplicantEmpId;
	private int enableMaps;
	private String apiKey;
	
	private boolean effortPresence;
	
	
	public boolean isCanAccessOpp() {
		return canAccessOpp;
	}
	public void setCanAccessOpp(boolean canAccessOpp) {
		this.canAccessOpp = canAccessOpp;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public int getId() {
		return getCompanyId();
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public String getCompanyAddressStreet() {
		return companyAddressStreet;
	}
	public void setCompanyAddressStreet(String companyAddressStreet) {
		this.companyAddressStreet = companyAddressStreet;
	}
	public String getCompanyAddressArea() {
		return companyAddressArea;
	}
	public void setCompanyAddressArea(String companyAddressArea) {
		this.companyAddressArea = companyAddressArea;
	}
	public String getCompanyAddressCity() {
		return companyAddressCity;
	}
	public void setCompanyAddressCity(String companyAddressCity) {
		this.companyAddressCity = companyAddressCity;
	}
	public String getCompanyAddressPincode() {
		return companyAddressPincode;
	}
	public void setCompanyAddressPincode(String companyAddressPincode) {
		this.companyAddressPincode = companyAddressPincode;
	}
	public String getCompanyAddressLandMark() {
		return companyAddressLandMark;
	}
	public void setCompanyAddressLandMark(String companyAddressLandMark) {
		this.companyAddressLandMark = companyAddressLandMark;
	}
	public String getCompanyAddressState() {
		return companyAddressState;
	}
	public void setCompanyAddressState(String companyAddressState) {
		this.companyAddressState = companyAddressState;
	}
	public String getCompanyAddressCountry() {
		return companyAddressCountry;
	}
	public void setCompanyAddressCountry(String companyAddressCountry) {
		this.companyAddressCountry = companyAddressCountry;
	}
	public String getPrimaryContactFirstName() {
		return primaryContactFirstName;
	}
	public void setPrimaryContactFirstName(String primaryContactFirstName) {
		this.primaryContactFirstName = primaryContactFirstName;
	}
	public String getPrimaryContactLastName() {
		return primaryContactLastName;
	}
	public void setPrimaryContactLastName(String primaryContactLastName) {
		this.primaryContactLastName = primaryContactLastName;
	}
	public String getPrimaryContactEmail() {
		return primaryContactEmail;
	}
	public void setPrimaryContactEmail(String primaryContactEmail) {
		this.primaryContactEmail = primaryContactEmail;
	}
	public String getPrimaryContactPhone() {
		return primaryContactPhone;
	}
	public void setPrimaryContactPhone(String primaryContactPhone) {
		this.primaryContactPhone = primaryContactPhone;
	}
	public String getPrimaryContactTitle() {
		return primaryContactTitle;
	}
	public void setPrimaryContactTitle(String primaryContactTitle) {
		this.primaryContactTitle = primaryContactTitle;
	}
	public String getSecondaryContactFirstName() {
		return secondaryContactFirstName;
	}
	public void setSecondaryContactFirstName(String secondaryContactFirstName) {
		this.secondaryContactFirstName = secondaryContactFirstName;
	}
	public String getSecondaryContactLastName() {
		return secondaryContactLastName;
	}
	public void setSecondaryContactLastName(String secondaryContactLastName) {
		this.secondaryContactLastName = secondaryContactLastName;
	}
	public String getSecondaryContactEmail() {
		return secondaryContactEmail;
	}
	public void setSecondaryContactEmail(String secondaryContactEmail) {
		this.secondaryContactEmail = secondaryContactEmail;
	}
	public String getSecondaryContactPhone() {
		return secondaryContactPhone;
	}
	public void setSecondaryContactPhone(String secondaryContactPhone) {
		this.secondaryContactPhone = secondaryContactPhone;
	}
	public String getSecondaryContactTitle() {
		return secondaryContactTitle;
	}
	public void setSecondaryContactTitle(String secondaryContactTitle) {
		this.secondaryContactTitle = secondaryContactTitle;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getWebLogoUrl() {
		return webLogoUrl;
	}
	public void setWebLogoUrl(String webLogoUrl) {
		this.webLogoUrl = webLogoUrl;
	}
	public String getAppLogoUrl() {
		return appLogoUrl;
	}
	public void setAppLogoUrl(String appLogoUrl) {
		this.appLogoUrl = appLogoUrl;
	}
	public String getWebLogoUrlMimeType() {
		return webLogoUrlMimeType;
	}
	public void setWebLogoUrlMimeType(String webLogoUrlMimeType) {
		this.webLogoUrlMimeType = webLogoUrlMimeType;
	}
	public String getAppLogoUrlMimeType() {
		return appLogoUrlMimeType;
	}
	public void setAppLogoUrlMimeType(String appLogoUrlMimeType) {
		this.appLogoUrlMimeType = appLogoUrlMimeType;
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
	public boolean isTestCompany() {
		return testCompany;
	}
	public void setTestCompany(boolean testCompany) {
		this.testCompany = testCompany;
	}
	public Double getTotalSizeUsed() {
		return totalSizeUsed;
	}
	public void setTotalSizeUsed(Double totalSizeUsed) {
		this.totalSizeUsed = totalSizeUsed;
	}
	public String getInitialContactEmail() {
		return initialContactEmail;
	}
	public void setInitialContactEmail(String initialContactEmail) {
		this.initialContactEmail = initialContactEmail;
	}
	
	public Integer getBillCyclePeriod() {
		return billCyclePeriod;
	}
	public void setBillCyclePeriod(Integer billCyclePeriod) {
		this.billCyclePeriod = billCyclePeriod;
	}
	
	public Integer getDuePeriod() {
		return duePeriod;
	}
	public void setDuePeriod(Integer duePeriod) {
		this.duePeriod = duePeriod;
	}
	public String getCompanyGstNo() {
		return companyGstNo;
	}
	public void setCompanyGstNo(String companyGstNo) {
		this.companyGstNo = companyGstNo;
	}
	public boolean isCanAccessAdUser() {
		return canAccessAdUser;
	}
	public void setCanAccessAdUser(boolean canAccessAdUser) {
		this.canAccessAdUser = canAccessAdUser;
	}
	public boolean isInternalTest() {
		return internalTest;
	}
	public void setInternalTest(boolean internalTest) {
		this.internalTest = internalTest;
	}
	public String getExtraMailIdsForProductiveInfo() {
		return extraMailIdsForProductiveInfo;
	}
	public void setExtraMailIdsForProductiveInfo(
			String extraMailIdsForProductiveInfo) {
		this.extraMailIdsForProductiveInfo = extraMailIdsForProductiveInfo;
	}
	public boolean isNcellLogin() {
		return ncellLogin;
	}
	public void setNcellLogin(boolean ncellLogin) {
		this.ncellLogin = ncellLogin;
	}
	public boolean isInvoiceDownloadEnabled() {
		return invoiceDownloadEnabled;
	}
	public void setInvoiceDownloadEnabled(boolean invoiceDownloadEnabled) {
		this.invoiceDownloadEnabled = invoiceDownloadEnabled;
	}
	
	@JsonIgnore
	public boolean isEnableActivityBasedLocation() {
		return enableActivityBasedLocation;
	}
	@JsonIgnore
	public void setEnableActivityBasedLocation(boolean enableActivityBasedLocation) {
		this.enableActivityBasedLocation = enableActivityBasedLocation;
	}
	@JsonIgnore
	public boolean isBlockActivityBasedLocation() {
		return blockActivityBasedLocation;
	}
	@JsonIgnore
	public void setBlockActivityBasedLocation(boolean blockActivityBasedLocation) {
		this.blockActivityBasedLocation = blockActivityBasedLocation;
	}
	public boolean isHardDeleteDeviceData() {
		return hardDeleteDeviceData;
	}
	public void setHardDeleteDeviceData(boolean hardDeleteDeviceData) {
		this.hardDeleteDeviceData = hardDeleteDeviceData;
	}
	public Long getCompanySubscriptionPlanId() {
		return companySubscriptionPlanId;
	}
	public void setCompanySubscriptionPlanId(Long companySubscriptionPlanId) {
		this.companySubscriptionPlanId = companySubscriptionPlanId;
	}
	public int getRequestPlanType() {
		return requestPlanType;
	}
	public void setRequestPlanType(int requestPlanType) {
		this.requestPlanType = requestPlanType;
	}
	public boolean isEnableProformaInvoice() {
		return enableProformaInvoice;
	}
	public void setEnableProformaInvoice(boolean enableProformaInvoice) {
		this.enableProformaInvoice = enableProformaInvoice;
	}
	public Long getGigUserAppId() {
		return gigUserAppId;
	}
	public void setGigUserAppId(Long gigUserAppId) {
		this.gigUserAppId = gigUserAppId;
	}
	public Long getGigApplicantEmpId() {
		return gigApplicantEmpId;
	}
	public void setGigApplicantEmpId(Long gigApplicantEmpId) {
		this.gigApplicantEmpId = gigApplicantEmpId;
	}
	public int getEnableMaps() {
		return enableMaps;
	}
	public void setEnableMaps(int enableMaps) {
		this.enableMaps = enableMaps;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public boolean isEffortPresence() {
		return effortPresence;
	}
	public void setEffortPresence(boolean effortPresence) {
		this.effortPresence = effortPresence;
	}
	
	
	

}
