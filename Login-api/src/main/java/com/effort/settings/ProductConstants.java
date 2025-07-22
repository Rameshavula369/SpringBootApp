package com.effort.settings;


import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class ProductConstants implements Serializable  {
	
	private static final long serialVersionUID = -4158624493530980043L;
	
	private String productId;
	private Boolean isTracking;
	private Boolean isService;
	private Boolean showJobs;
	private Boolean showEmployeeLeaves;
	private Boolean showMobileUpdates;
	private Boolean showLocationUpdates;
	private Boolean createJob;
	private Boolean addEmployee;
	private Boolean addForm;
	private Boolean addNotification;
	private Boolean addCustomer;
	private Boolean applyLeave;
	private Boolean dispatch;
	private Boolean assignRoutePlan;
	private Boolean myApprovals;
	private Boolean bulkListUpload;
	private Boolean showKnownLocations;
	private Boolean showMap;

	
	
	/*Organize*/
	private Boolean showKnowledgeBase;
	private Boolean showJobsOrganize;
	private Boolean showForms;
	private Boolean showCustomers;
	private Boolean showLeaves;
	private Boolean showClaims;
	private Boolean showAssignedRoutePlans;
	
	/*monitor*/
	private Boolean showJobReports;
	private Boolean showAudits;
	private Boolean showFormReports;
	private Boolean showScheduleReports;
	private Boolean showLocationReports;
	private Boolean showEmployeeReports;
	private Boolean showEmployeeActivityReport;
	private Boolean showMisc;
	//private Boolean showClaims;
	private Boolean showRoutesReport;
	private Boolean showInsights;
	private Boolean showMyReports;

	
	/*configure*/
	private Boolean showEmployee;
	private Boolean showWorkflow;
	private Boolean showCalendars;
	//private Boolean showForms;
	private Boolean showNamedLocations;
	private Boolean showJob;
	private Boolean showCustomer;
	private Boolean showCompany;
	private Boolean showMyAccount;
	private Boolean showNotificationsInMyAccount;
	private Boolean showInvoice;
	//private Boolean showClaims;
	private Boolean showRoutes;
	
	private Boolean showEmployeeLeaveFrequencyReport;
	private Boolean showEmployeeLeaveReport;
	
	private Boolean uiLayoutEast;
	private String productName;
	private String productLineName;
	private String version;
	private String customerSupport;
	private int companyId;
	private Boolean isHSBCLogin;
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public Boolean getIsTracking() {
		return isTracking;
	}
	public void setIsTracking(Boolean isTracking) {
		this.isTracking = isTracking;
	}
	public Boolean getShowJobs() {
		return showJobs;
	}
	public void setShowJobs(Boolean showJobs) {
		this.showJobs = showJobs;
	}
	public Boolean getShowEmployeeLeaves() {
		return showEmployeeLeaves;
	}
	public void setShowEmployeeLeaves(Boolean showEmployeeLeaves) {
		this.showEmployeeLeaves = showEmployeeLeaves;
	}
	public Boolean getShowMobileUpdates() {
		return showMobileUpdates;
	}
	public void setShowMobileUpdates(Boolean showMobileUpdates) {
		this.showMobileUpdates = showMobileUpdates;
	}
	public Boolean getShowLocationUpdates() {
		return showLocationUpdates;
	}
	public void setShowLocationUpdates(Boolean showLocationUpdates) {
		this.showLocationUpdates = showLocationUpdates;
	}
	public Boolean getCreateJob() {
		return createJob;
	}
	public void setCreateJob(Boolean createJob) {
		this.createJob = createJob;
	}
	public Boolean getAddEmployee() {
		return addEmployee;
	}
	public void setAddEmployee(Boolean addEmployee) {
		this.addEmployee = addEmployee;
	}
	public Boolean getAddForm() {
		return addForm;
	}
	public void setAddForm(Boolean addForm) {
		this.addForm = addForm;
	}
	public Boolean getAddNotification() {
		return addNotification;
	}
	public void setAddNotification(Boolean addNotification) {
		this.addNotification = addNotification;
	}
	public Boolean getAddCustomer() {
		return addCustomer;
	}
	public void setAddCustomer(Boolean addCustomer) {
		this.addCustomer = addCustomer;
	}
	public Boolean getApplyLeave() {
		return applyLeave;
	}
	public void setApplyLeave(Boolean applyLeave) {
		this.applyLeave = applyLeave;
	}
	public Boolean getDispatch() {
		return dispatch;
	}
	public void setDispatch(Boolean dispatch) {
		this.dispatch = dispatch;
	}
	public Boolean getAssignRoutePlan() {
		return assignRoutePlan;
	}
	public void setAssignRoutePlan(Boolean assignRoutePlan) {
		this.assignRoutePlan = assignRoutePlan;
	}
	public Boolean getMyApprovals() {
		return myApprovals;
	}
	public void setMyApprovals(Boolean myApprovals) {
		this.myApprovals = myApprovals;
	}
	public Boolean getBulkListUpload() {
		return bulkListUpload;
	}
	public void setBulkListUpload(Boolean bulkListUpload) {
		this.bulkListUpload = bulkListUpload;
	}
	public Boolean getShowKnownLocations() {
		return showKnownLocations;
	}
	public void setShowKnownLocations(Boolean showKnownLocations) {
		this.showKnownLocations = showKnownLocations;
	}
	public Boolean getShowMap() {
		return showMap;
	}
	public void setShowMap(Boolean showMap) {
		this.showMap = showMap;
	}
	public Boolean getShowKnowledgeBase() {
		return showKnowledgeBase;
	}
	public void setShowKnowledgeBase(Boolean showKnowledgeBase) {
		this.showKnowledgeBase = showKnowledgeBase;
	}
	public Boolean getShowJobsOrganize() {
		return showJobsOrganize;
	}
	public void setShowJobsOrganize(Boolean showJobsOrganize) {
		this.showJobsOrganize = showJobsOrganize;
	}
	public Boolean getShowForms() {
		return showForms;
	}
	public void setShowForms(Boolean showForms) {
		this.showForms = showForms;
	}
	public Boolean getShowCustomers() {
		return showCustomers;
	}
	public void setShowCustomers(Boolean showCustomers) {
		this.showCustomers = showCustomers;
	}
	public Boolean getShowLeaves() {
		return showLeaves;
	}
	public void setShowLeaves(Boolean showLeaves) {
		this.showLeaves = showLeaves;
	}
	public Boolean getShowClaims() {
		return showClaims;
	}
	public void setShowClaims(Boolean showClaims) {
		this.showClaims = showClaims;
	}
	public Boolean getShowAssignedRoutePlans() {
		return showAssignedRoutePlans;
	}
	public void setShowAssignedRoutePlans(Boolean showAssignedRoutePlans) {
		this.showAssignedRoutePlans = showAssignedRoutePlans;
	}
	public Boolean getShowJobReports() {
		return showJobReports;
	}
	public void setShowJobReports(Boolean showJobReports) {
		this.showJobReports = showJobReports;
	}
	public Boolean getShowAudits() {
		return showAudits;
	}
	public void setShowAudits(Boolean showAudits) {
		this.showAudits = showAudits;
	}
	public Boolean getShowFormReports() {
		return showFormReports;
	}
	public void setShowFormReports(Boolean showFormReports) {
		this.showFormReports = showFormReports;
	}
	public Boolean getShowScheduleReports() {
		return showScheduleReports;
	}
	public void setShowScheduleReports(Boolean showScheduleReports) {
		this.showScheduleReports = showScheduleReports;
	}
	public Boolean getShowLocationReports() {
		return showLocationReports;
	}
	public void setShowLocationReports(Boolean showLocationReports) {
		this.showLocationReports = showLocationReports;
	}
	public Boolean getShowEmployeeReports() {
		return showEmployeeReports;
	}
	public void setShowEmployeeReports(Boolean showEmployeeReports) {
		this.showEmployeeReports = showEmployeeReports;
	}
	public Boolean getShowEmployeeActivityReport() {
		return showEmployeeActivityReport;
	}
	public void setShowEmployeeActivityReport(Boolean showEmployeeActivityReport) {
		this.showEmployeeActivityReport = showEmployeeActivityReport;
	}
	public Boolean getShowMisc() {
		return showMisc;
	}
	public void setShowMisc(Boolean showMisc) {
		this.showMisc = showMisc;
	}
	public Boolean getShowRoutesReport() {
		return showRoutesReport;
	}
	public void setShowRoutesReport(Boolean showRoutesReport) {
		this.showRoutesReport = showRoutesReport;
	}
	public Boolean getShowInsights() {
		return showInsights;
	}
	public void setShowInsights(Boolean showInsights) {
		this.showInsights = showInsights;
	}
	public Boolean getShowMyReports() {
		return showMyReports;
	}
	public void setShowMyReports(Boolean showMyReports) {
		this.showMyReports = showMyReports;
	}
	public Boolean getShowEmployee() {
		return showEmployee;
	}
	public void setShowEmployee(Boolean showEmployee) {
		this.showEmployee = showEmployee;
	}
	public Boolean getShowWorkflow() {
		return showWorkflow;
	}
	public void setShowWorkflow(Boolean showWorkflow) {
		this.showWorkflow = showWorkflow;
	}
	public Boolean getShowCalendars() {
		return showCalendars;
	}
	public void setShowCalendars(Boolean showCalendars) {
		this.showCalendars = showCalendars;
	}
	public Boolean getShowNamedLocations() {
		return showNamedLocations;
	}
	public void setShowNamedLocations(Boolean showNamedLocations) {
		this.showNamedLocations = showNamedLocations;
	}
	public Boolean getShowJob() {
		return showJob;
	}
	public void setShowJob(Boolean showJob) {
		this.showJob = showJob;
	}
	public Boolean getShowCustomer() {
		return showCustomer;
	}
	public void setShowCustomer(Boolean showCustomer) {
		this.showCustomer = showCustomer;
	}
	public Boolean getShowCompany() {
		return showCompany;
	}
	public void setShowCompany(Boolean showCompany) {
		this.showCompany = showCompany;
	}
	public Boolean getShowMyAccount() {
		return showMyAccount;
	}
	public void setShowMyAccount(Boolean showMyAccount) {
		this.showMyAccount = showMyAccount;
	}
	public Boolean getShowNotificationsInMyAccount() {
		return showNotificationsInMyAccount;
	}
	public void setShowNotificationsInMyAccount(Boolean showNotificationsInMyAccount) {
		this.showNotificationsInMyAccount = showNotificationsInMyAccount;
	}
	public Boolean getShowInvoice() {
		return showInvoice;
	}
	public void setShowInvoice(Boolean showInvoice) {
		this.showInvoice = showInvoice;
	}
	public Boolean getShowRoutes() {
		return showRoutes;
	}
	public void setShowRoutes(Boolean showRoutes) {
		this.showRoutes = showRoutes;
	}
	public Boolean getUiLayoutEast() {
		return uiLayoutEast;
	}
	public void setUiLayoutEast(Boolean uiLayoutEast) {
		this.uiLayoutEast = uiLayoutEast;
	}
	public Boolean getShowEmployeeLeaveFrequencyReport() {
		return showEmployeeLeaveFrequencyReport;
	}
	public void setShowEmployeeLeaveFrequencyReport(
			Boolean showEmployeeLeaveFrequencyReport) {
		this.showEmployeeLeaveFrequencyReport = showEmployeeLeaveFrequencyReport;
	}
	public Boolean getShowEmployeeLeaveReport() {
		return showEmployeeLeaveReport;
	}
	public void setShowEmployeeLeaveReport(Boolean showEmployeeLeaveReport) {
		this.showEmployeeLeaveReport = showEmployeeLeaveReport;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getProductLineName() {
		return productLineName;
	}
	public void setProductLineName(String productLineName) {
		this.productLineName = productLineName;
	}
	public String getCustomerSupport() {
		return customerSupport;
	}
	public void setCustomerSupport(String customerSupport) {
		this.customerSupport = customerSupport;
	}
	public Boolean getIsService() {
		return isService;
	}
	public void setIsService(Boolean isService) {
		this.isService = isService;
	}
	public Boolean getIsHSBCLogin() {
		return isHSBCLogin;
	}
	public void setIsHSBCLogin(Boolean isHSBCLogin) {
		this.isHSBCLogin = isHSBCLogin;
	}
	
}
