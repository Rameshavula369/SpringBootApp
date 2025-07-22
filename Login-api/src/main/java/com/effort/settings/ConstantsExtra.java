package com.effort.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.effort.dao.SettingsDao;
import com.effort.util.Log;

@Component
public class ConstantsExtra {
	
	@Autowired
	private SettingsDao settingsDao;
	private boolean kalpataruLogin;
	
	private String enableEmployeeExpiryKey;
	@Value("${imeiBasedProvisioningKey}")
	private String imeiBasedProvisioningKey;
	@Value("${imeiBasedProvisioning}")
	private boolean imeiBasedProvisioning;
	private String resendActivationCodeSubject;

	public static final String EXPIRED_EMPLOYEE_ANDROID_MESSAGE = "Your license expired, please contact admin or <Manager_Name>! Once your license is renewed, restart the application. Please do not uninstall the application, you will lose the data!";
	private String qrCodeInformation = "<p style=\"color:#455056; font-size:14px;line-height:24px; margin:10 0;\">Login using QR code? Scan the QR Code below from the EFFORT App.</p>"
			+ "<br/>"
			+ "<img src=\"<media_link>\" />"
			+ "<br/>";
	
	
	
	private String resendActivationCodeBody;
	
	public String getEnableEmployeeExpiryKey() {
		return enableEmployeeExpiryKey;
	}
	
	public String getImeiBasedProvisioningKey() {
		return imeiBasedProvisioningKey;
	} 
	
	public boolean getImeiBasedProvisioning(long companyId) {
		try {
			return Boolean.parseBoolean(settingsDao.getCompanySetting(
					companyId, getImeiBasedProvisioningKey()));
		} catch (Exception e) {
			return imeiBasedProvisioning;
		}
	}
	
	
	public String getQrCodeInformation() {
		return qrCodeInformation;
	}
	

	public String getResendActivationCodeSubject() {
		return resendActivationCodeSubject;
	}
	public String getResendActivationCodeBody() {
		return resendActivationCodeBody;
	}
	public boolean isKalpataruLogin() {
		return kalpataruLogin;
	}
	
}
