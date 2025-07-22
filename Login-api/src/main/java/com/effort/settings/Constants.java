package com.effort.settings;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.effort.dao.SettingsDao;
import com.effort.util.Log;



@Component
public class Constants implements Serializable {
	
	@Autowired
	private SettingsDao settingsDao;
	
	@Value("${disableInternFieldNamesForJsonObjectMapper}")
	private String disableInternFieldNamesForJsonObjectMapper;
	private String gspCropFormSpecUniqueId;
	private String rootEmpKey;
	private String glSTThresholdKey;
	private String mapToKey;
	private String jobsViaSms;
	private String jobsViaSmsKey;
	private String glGPSThresholdKey;
	private boolean gspCropFollowingWorkflow;
	private String jmsDestinationForJobAddOrModifi;
	private int gspCropNextScheduleInterval;
	private String formSpecUniqueId_Of_Approval_form_to_call_rest_call_api;
	private String jco_error_mail_address;
	public static final int CHANGE_TYPE_ADD = 1;
	public static final int CHANGE_TYPE_MODIFY = 2;
	public static final int FORM_FIELD_TYPE_CUSTOMER = 7;
	public static final String ACTION_TYPE_SYNC = "sync";
	public static final String AUTOGENSEQ_SEPERATOR_1 = "-";
	public static final String AUTOGENSEQ_SEPERATOR_2 = "/";
	private static String project;
	private String smsGatewayUrl;
	private boolean only200;
	private String smscKey;
	@Value("${debugLogEnable:false}")
	private Boolean debugLogEnable;
	private String rejectedBySytemDueToFormModify = "Rejected by the system because Form Template withdrawn";
	private String signInActivitiyVisibilityKey;
	private String oneMonthSubscriptionKey;
	private String changeTrackingDviceSms;
	private String reportStoragePath;
	private String domain;
	private String mediaStoragePath;
	private String supportingImgExtn; 
	private String mediaBaseUrl;
	public static final String RSA_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAOl0lgxVfyjdNdA97x3u4C1kq3eC\n" +
	        "h/dqhMntrc4yl5f58r3EZQIr2EGmOPFvSusEPFnzYVwRf+XgJ4P5yP1yxXB29f5IAL59Ao0LW1x3\n" +
	        "95vpxxZP1VujMkkp3mPcY1zJGVk43tWglg0Jwst4qGv1jHA9aTxK9Z5LQNsmk6swO4IpAgMBAAEC\n" +
	        "gYAQRL6cF20oFNpGK7q5FEdtAmHsjx0YgcTFKZn+D4p+3b0ruj7rutB0Krg3IvFOlVn7TrmPJ+0I\n" +
	        "wOkgCk0WVGQrwxvhf3Zi228rf6v2o9I2CUGfV7aaPoTJ1kazV/dII6obGN8bHHkIWeW3wuweXatA\n" +
	        "tGXJV1uTPI+6ILzVX43OXQJBAPVhQhiChzxjEpi1Fo/hKqrBJprzIThznBkVS3/84hf35gEkwq0V\n" +
	        "JJNjScN/RBkZkvKOR85ZyaZoLebp9KTZYqsCQQDzjzUwAQdWCMCGTHTkZXynshoCPeEdnL5FRz0Y\n" +
	        "XxL+lX7vLmM+m7Y93dDuvDnd0LwEB/s0hw6nCNhFuA6HK057AkAQKOONcasuUuf6npJpz05cCHRe\n" +
	        "Z/ycFyEEld5vA1xwb6b6FE2t0GKkQjmtYkg9zu1ag/w+nrMk/l9ngajW4moZAkBZjaFv2iD5aFRp\n" +
	        "zPj59brk6h6YDmqUecqYLH5xVJvmUc+PYE4LejODAelNrpXUUmifTtP112by0dS7pdZpqum7AkAh\n" +
	        "2+QKmJoc05DWQqpVcxlWSwhKPHIbDKjjfntjHpWxIew1gM/HZV8w7RQzIBRPV0/NTeSjBQwBlmSl\n" +
	        "zViQp0qK\n";
	
   
	public static int getFormFieldTypeCustomer() {
		return FORM_FIELD_TYPE_CUSTOMER;
	}
	
	
	public static int getChangeTypeModify() {
		return CHANGE_TYPE_MODIFY;
	}
	

	
	public String getDisableInternFieldNamesForJsonObjectMapper() {
		return disableInternFieldNamesForJsonObjectMapper;
	}
	public void setDisableInternFieldNamesForJsonObjectMapper(
			String disableInternFieldNamesForJsonObjectMapper) {
		this.disableInternFieldNamesForJsonObjectMapper = disableInternFieldNamesForJsonObjectMapper;
	}
	
	
	public String getRejectedBySytemDueToFormModify() {
		return rejectedBySytemDueToFormModify;
	}
	

	public void setRejectedBySytemDueToFormModify(
			String rejectedBySytemDueToFormModify) {
		this.rejectedBySytemDueToFormModify = rejectedBySytemDueToFormModify;
	}
	
	
	
	public Boolean isDebugLogEnable() {
		return debugLogEnable;
	}
	
	public String getGspCropFormSpecUniqueId() {
		return gspCropFormSpecUniqueId;
	}
	

	public boolean isGspCropFollowingWorkflow() {
		return gspCropFollowingWorkflow;
	}
	
	public String getRootEmpKey() {
		return rootEmpKey;
	}
	
	public String getGlSTThresholdKey() {
		return glSTThresholdKey;
	}
	public String getGlGPSThresholdKey() {
		return glGPSThresholdKey;
	}

	public String getMapToKey() {
		return mapToKey;
	}
	
	public String getJco_error_mail_address() {
		return jco_error_mail_address;
	}
	
	
	public String getJmsDestinationForJobAddOrModifi() {
		return jmsDestinationForJobAddOrModifi;
	}
	public String getJobsViaSmsKey() {
		return jobsViaSmsKey;
	}
	public int getGspCropNextScheduleInterval() {
		return gspCropNextScheduleInterval;
	}
	public String getFormSpecUniqueId_Of_Approval_form_to_call_rest_call_api() {
		return formSpecUniqueId_Of_Approval_form_to_call_rest_call_api;
	}

	
	public String getJobsViaSms() {
		return jobsViaSms;
	}
	public void setJobsViaSms(String jobsViaSms) {
		this.jobsViaSms = jobsViaSms;
	}
	public Boolean getDebugLogEnable() {
		return debugLogEnable;
	}
	public void setDebugLogEnable(Boolean debugLogEnable) {
		this.debugLogEnable = debugLogEnable;
	}
	
	
	public static int getChangeTypeAdd() {
		return CHANGE_TYPE_ADD;
	}
	public void setGspCropFormSpecUniqueId(String gspCropFormSpecUniqueId) {
		this.gspCropFormSpecUniqueId = gspCropFormSpecUniqueId;
	}
	public void setRootEmpKey(String rootEmpKey) {
		this.rootEmpKey = rootEmpKey;
	}
	public void setGlSTThresholdKey(String glSTThresholdKey) {
		this.glSTThresholdKey = glSTThresholdKey;
	}
	public void setMapToKey(String mapToKey) {
		this.mapToKey = mapToKey;
	}
	public void setJobsViaSmsKey(String jobsViaSmsKey) {
		this.jobsViaSmsKey = jobsViaSmsKey;
	}
	public void setGlGPSThresholdKey(String glGPSThresholdKey) {
		this.glGPSThresholdKey = glGPSThresholdKey;
	}
	public void setGspCropFollowingWorkflow(boolean gspCropFollowingWorkflow) {
		this.gspCropFollowingWorkflow = gspCropFollowingWorkflow;
	}
	public void setJmsDestinationForJobAddOrModifi(String jmsDestinationForJobAddOrModifi) {
		this.jmsDestinationForJobAddOrModifi = jmsDestinationForJobAddOrModifi;
	}
	public void setGspCropNextScheduleInterval(int gspCropNextScheduleInterval) {
		this.gspCropNextScheduleInterval = gspCropNextScheduleInterval;
	}
	public void setFormSpecUniqueId_Of_Approval_form_to_call_rest_call_api(
			String formSpecUniqueId_Of_Approval_form_to_call_rest_call_api) {
		this.formSpecUniqueId_Of_Approval_form_to_call_rest_call_api = formSpecUniqueId_Of_Approval_form_to_call_rest_call_api;
	}
	public void setJco_error_mail_address(String jco_error_mail_address) {
		this.jco_error_mail_address = jco_error_mail_address;
	}
	


	public static String getActionTypeSync() {
		return ACTION_TYPE_SYNC;
	}
	
	
	public String getSignInActivitiyVisibilityKey() {
		return signInActivitiyVisibilityKey;
	}
	
	
	public void setSignInActivitiyVisibilityKey(String signInActivitiyVisibilityKey) {
		this.signInActivitiyVisibilityKey = signInActivitiyVisibilityKey;
	}
	
	public static String getProject() {
		return project;
	}
	public String getOneMonthSubscriptionKey() {
		return oneMonthSubscriptionKey;
	}
	public String getChangeTrackingDviceSms() {
		return changeTrackingDviceSms;
	}

	public String getSmsGatewayUrl() {
		return smsGatewayUrl;
	}
	
	

	public String getReportStoragePath() {
		return reportStoragePath;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public String getMediaStoragePath() {
		return mediaStoragePath;
	}
 
	public String getSupportingImgExtn() {
		return supportingImgExtn;
	}

	public String getMediaBaseUrl() {
		return mediaBaseUrl;
	}
	public String getSmsc() {
		try {
			return settingsDao.getGlobalSettings(getSmscKey());
		} catch (Exception e) {
			Log.ignore(this.getClass(), e);
			return "";
		}
	}
	public String getSmscKey() {
		return smscKey;
	}

	public boolean isOnly200() {
		return only200;
	}
}

