package com.effort.manager;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.MessagingException;
import org.springframework.security.core.GrantedAuthority;

import com.effort.context.AppContext;
import com.effort.dao.EmployeeDao;
import com.effort.dao.ExtraDao;
import com.effort.dao.ExtraSupportAdditionalDao;
import com.effort.dao.InAppDao;
import com.effort.entity.Company;
import com.effort.entity.Employee;
import com.effort.entity.ErrorResponse;
import com.effort.entity.ITunesPurchase;
import com.effort.entity.Mail;
import com.effort.entity.OutgoingSMS;
import com.effort.entity.Provisioning;
import com.effort.entity.ProvisioningFailureDetails;
import com.effort.entity.ProvisioningOTKey;
import com.effort.entity.SmsActivationTemplate;
import com.effort.entity.Subscripton;
import com.effort.entity.WebUser;
import com.effort.exception.EffortError;
import com.effort.remote.RemoteUserAuthority;
import com.effort.settings.BrandingConstants;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.settings.ResponseCodes;
import com.effort.util.Api;
import com.effort.util.Log;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;


@Service
public class MainManager {

	@Autowired
	private WebManager webManager;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private Constants constants;
	
	@Autowired
	private SmsManager smsManager; 
	
	@Autowired
	private ExtraSupportAdditionalDao extraSupportAdditionalDao;
	
	@Autowired
	private ExtraDao extraDao;
	
	@Autowired
	private ConstantsExtra constantsExtra; 
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private InAppDao inAppDao; 
	
	@Autowired
	private MailTask mailTask;
	
	@Autowired
	private WebExtraManager webExtraManager;
	
	@Autowired
	private BrandingConstants brandingConstants; 
	
	@Autowired
	private ResponseCodes responseCodes;
	
	private WebAdditionalManager getWebAdditionalManager(){
		WebAdditionalManager webAdditionalManager = AppContext.getApplicationContext().getBean("webAdditionalManager",WebAdditionalManager.class);
		return webAdditionalManager;
	}
	
	private SyncManager getSyncManager(){
		SyncManager syncManager = AppContext.getApplicationContext().getBean("syncManager",SyncManager.class);
		return syncManager;
	}
	
	
	 private WebExtensionManager getWebExtensionManager(){
			WebExtensionManager webExtensionManager = AppContext.getApplicationContext().getBean("webExtensionManager",WebExtensionManager.class);
			return webExtensionManager;
		}
	 
	public Employee getEmployeeByLogin(String code, String username,
			String password, String override, ProvisioningFailureDetails pfd, String apiLevel,int loginType) throws EffortError {
		WebUser webUser = null;

		try {
			webUser = webManager.getWebUserByUsernameEmp(username);
		} catch (IncorrectResultSizeDataAccessException e) {
		}
		
		if(webUser!=null){
			List<String> authoritiesRaw = webManager.getWebUserAuthorities(webUser
					.getUsername());

			if(authoritiesRaw!=null && !authoritiesRaw.isEmpty()){
				Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				for (String authorityStr : authoritiesRaw) {
					authorities.add(new RemoteUserAuthority(authorityStr));
				}
				webUser.setAuthorities(authorities);
				boolean noMobileAccess = false;
				if(webUser.hasRole("ROLE_MGNR_NO_MOBILE") || webUser.hasRole("ROLE_USER_NO_MOBILE"))
				{
					noMobileAccess = true;
				}
				else if(webUser.hasRole("ROLE_MGNR_NO_REPORT_MOBILE") || webUser.hasRole("ROLE_USER_NO_REPORT_MOBILE"))
				{
					noMobileAccess = true;
				}
				else if(webUser.hasRole("ROLE_WEBLITE_NO_MOBILE"))
				{
					noMobileAccess = true;
				}
				else if (webUser.hasRole("ROLE_CONFIGURATOR") || webUser.hasRole("ROLE_MGNR_WEB_CONFIG_WEBLITE") || webUser.hasRole("ROLE_USER_WEB_CONFIG_WEBLITE")) {
					noMobileAccess = true;
				}
				if(noMobileAccess){
					throw new EffortError(9990, HttpStatus.PRECONDITION_FAILED);
				}
			}
			
			Company company = extraDao.getCompany(webUser.getCompanyId());
			if(company!=null) {
				if(company.isEffortPresence()) {
					throw new EffortError(9991, HttpStatus.PRECONDITION_FAILED);
				}
			}
			
		}

		if (webUser != null && !Api.isEmptyString(password)) {
			String sha256Password = "";

			String salt = "";
			if(webUser.getSalt() != null)
			{
				salt = webUser.getSalt();
			}
			try {
				sha256Password = commonService.getSha256Hash(password+salt);
			} catch (NoSuchAlgorithmException e) {
				Log.info(this.getClass(), e.toString(), e);
			}

			if (sha256Password.equals(webUser.getPassword())) {
				try {
					return getEmployeeByCode(code, override, pfd,apiLevel,webUser.getEmpId(),loginType);
				} catch (EffortError e) {
					if (e.getCode() == 4018) {
						provision(webUser.getEmpId(), code,pfd.getIpAddress());
						return getEmployeeByCode(code, override, pfd,apiLevel,webUser.getEmpId(),loginType);
					} else {
						pfd.setUsername(username);
						pfd.setPassword(sha256Password);
						pfd.setImei(code);
						pfd.setOverride(override);
						pfd.setFailureCode(ProvisioningFailureDetails.INVALID_UNAME_PASSWORD);
						extraDao.insertProvisioningFailureDetails(pfd);
						
						throw e;
					}
				}
			} else {
				pfd.setUsername(username);
				pfd.setPassword(sha256Password);
				pfd.setImei(code);
				pfd.setOverride(override);
				pfd.setEmpId(webUser.getEmpId());
				pfd.setCompanyId(webUser.getCompanyId());
				pfd.setFailureCode(ProvisioningFailureDetails.INVALID_UNAME_PASSWORD);
				extraDao.insertProvisioningFailureDetails(pfd);
				throw new EffortError(4037, HttpStatus.PRECONDITION_FAILED);
			}
		} else {
			String sha256Password=  "";
			try {
				sha256Password = commonService.getSha256Hash(password);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			pfd.setUsername(username);
			pfd.setPassword(sha256Password);
			pfd.setImei(code);
			pfd.setOverride(override);
			pfd.setFailureCode(ProvisioningFailureDetails.INVALID_UNAME_PASSWORD);
			extraDao.insertProvisioningFailureDetails(pfd);
			throw new EffortError(4037, HttpStatus.PRECONDITION_FAILED);
		}
	}

	public void provision(long empId, String code, String ipAddress) {
		Provisioning provisioning = null;

		try {
			provisioning = employeeDao.getProvisioningByEmpId(empId);
		} catch (Exception e) {
		}

		if (provisioning == null) {
			Provisioning newProvisioning = new Provisioning();
			newProvisioning.setEmpId(empId);
			newProvisioning.setCode(code);

			employeeDao.insertProvisioning(newProvisioning);
			//getWebAdditionalManager().updateEmployeeWithIMEI(code,empId);
			webManager.logEmployeeAudit(empId, empId,ipAddress,null);
		} else {
			provisioning.setPendingCode(code);

			employeeDao.updateProvisioning(provisioning);
			//getWebAdditionalManager().updateEmployeeWithIMEI(code,empId);
			webManager.logEmployeeAudit(empId, empId,ipAddress,null);
		}

	}
	
	
	
	
	public Employee getEmployeeByCode(String code, String override, ProvisioningFailureDetails pfd, String apiLevel, Long empId,int loginType)
			throws EffortError {
		Provisioning provisioning = null;
		
		Employee loginEmployee = employeeDao.getEmployeeBasicDetailsByEmpId(empId+"");
		boolean isImeiBased = true;
		if(loginEmployee!=null)
		{
			isImeiBased = constantsExtra.getImeiBasedProvisioning(loginEmployee.getCompanyId());
		}

		try {
			if(isImeiBased){
				provisioning = employeeDao.getProvisioningByCodeORPendingCode(code);
			}
		} catch (Exception e) {
			
		}

		if (provisioning != null) {
			
			Employee employee = null;
			if(isImeiBased){
				employee = employeeDao.getEmployee(provisioning.getEmpId()+"");
			}else
			{
				employee = employeeDao.getEmployee(empId+"");
			}
			
			if (employee.getStatus() == 0) {
				pfd.setEmpId(employee.getEmpId());
				pfd.setFailureCode(ProvisioningFailureDetails.INVALID_IMEI);
				extraDao.insertProvisioningFailureDetails(pfd);
				throw new EffortError(4020, HttpStatus.PRECONDITION_FAILED);
				
			}
			if (provisioning.getCode() != null
					&& provisioning.getCode().equals(code)) {
				if(pfd.getClientPlatform().equals("4")){
					if(!inAppDao.isPurchaseRecordAvailableForEMp(employee.getEmpId())){
						employee.setImei(code);
					insertItunePurchases(employee);
					}else{
						ITunesPurchase iTunesPurchase = inAppDao.getITunePurchaseRecord(employee.getEmpId());
						if(iTunesPurchase != null && !code.equals(iTunesPurchase.getUdid())){
							inAppDao.updateUdidCodeForEmp(employee.getEmpId(), code);
						}
					}
				}
				if(empId!=null && empId != employee.getEmpId())
				{
					pfd.setEmpId(employee.getEmpId());
					pfd.setFailureCode(ProvisioningFailureDetails.INVALID_IMEI);
					extraDao.insertProvisioningFailureDetails(pfd);
					throw new EffortError(4031, HttpStatus.PRECONDITION_FAILED);
				}
				if(!constantsExtra.getImeiBasedProvisioning(employee.getCompanyId()))
				{
					String effortToken = generateRandomGuId();
					extraSupportAdditionalDao.updateEffortTokenInProvisioningsForGivenEmpIdAndEmpCode(employee.getEmpId(),effortToken);
					employee.setEffortToken(effortToken);
					if((employee.getImei() == null || (employee.getImei()!=null && !employee.getImei().equals(effortToken))) && !isImeiBased)
					{
						getWebAdditionalManager().updateEmployeeWithIMEI(effortToken,employee.getEmpId());
					}
				}
				else if((employee.getImei() == null || (employee.getImei()!=null && !employee.getImei().equals(code))) && isImeiBased)
				{
					getWebAdditionalManager().updateEmployeeWithIMEI(code,employee.getEmpId());
				}
				return employee;
			} else if (provisioning.getPendingCode() != null
					&& provisioning.getPendingCode().equals(code)) {

				if (override != null && override.equals("1")) {
					provisioning.setCode(code);
					provisioning.setPendingCode(null);
					if(empId!=null && empId != employee.getEmpId())
					{
						pfd.setEmpId(employee.getEmpId());
						pfd.setFailureCode(ProvisioningFailureDetails.INVALID_IMEI);
						extraDao.insertProvisioningFailureDetails(pfd);
						throw new EffortError(4031, HttpStatus.PRECONDITION_FAILED);
					}
					if(!constantsExtra.getImeiBasedProvisioning(employee.getCompanyId()))
					{
						String effortToken = generateRandomGuId();
						employee.setEffortToken(effortToken);
						provisioning.setEffortToken(effortToken);
						provisioning.setCode(effortToken);
						employeeDao.updateProvisioningWithEffortToken(provisioning);
						if((employee.getImei() == null || (employee.getImei()!=null && !employee.getImei().equals(effortToken))) && !isImeiBased)
						{
							getWebAdditionalManager().updateEmployeeWithIMEI(effortToken,employee.getEmpId());
						}
					}
					else
					{
						employeeDao.updateProvisioning(provisioning);
					}
					if((employee.getImei() == null || (employee.getImei()!=null && !employee.getImei().equals(code))) && isImeiBased)
					{
						getWebAdditionalManager().updateEmployeeWithIMEI(code,employee.getEmpId());
					}
					webManager.logEmployeeAudit(employee.getEmpId(),
							employee.getEmpId(),pfd.getIpAddress(),null);
					
					String msisdn = employee.getEmpPhone();
					if(employee.getEmpIsdCode() != null){
						msisdn= employee.getEmpIsdCode().intValue()+""+msisdn;
					}
					
					OutgoingSMS outgoingSMS = new OutgoingSMS();
					outgoingSMS.setMsisdn(msisdn);
					outgoingSMS.setMessage(constants
							.getChangeTrackingDviceSms());
					outgoingSMS.setCompanyId(employee.getCompanyId());
					outgoingSMS.setSmsType(OutgoingSMS.FREE);
					outgoingSMS.setSendSmsType(1);
					try {
						smsManager.sendSms(outgoingSMS);
					} catch (Exception e) {
						Log.info(this.getClass(), e.toString(), e);
					}
					if(pfd.getClientPlatform().equals("4")){
						if(!inAppDao.isPurchaseRecordAvailableForEMp(employee.getEmpId())){
							employee.setImei(code);
							insertItunePurchases(employee);
						}else{
							ITunesPurchase iTunesPurchase = inAppDao.getITunePurchaseRecord(employee.getEmpId());
							if(iTunesPurchase != null && !code.equals(iTunesPurchase.getUdid())){
								inAppDao.updateUdidCodeForEmp(employee.getEmpId(), code);
							}
						}
					}
					
					return employee;
				} else {
					throw new EffortError(4019, HttpStatus.PRECONDITION_FAILED);
				}
			} else {
				pfd.setFailureCode(ProvisioningFailureDetails.INVALID_IMEI);
				extraDao.insertProvisioningFailureDetails(pfd);
				throw new EffortError(4018, HttpStatus.PRECONDITION_FAILED);
			}
		} else {
			Employee employee = null;
			try {
				// employee = employeeDao.getEmployeeByImei(code);
				if(isImeiBased){
					employee = employeeDao.getActiveEmployeeByImei(code);
				}else
				{
					employee = employeeDao.getEmployee(empId+"");
				}
				/*if(employee == null && loginType == 1)
				{
					employee = employeeDao.getEmployee(empId+"");
				}*/
				if (employee != null) {
					if (employee.getStatus() == 0) {
						pfd.setEmpId(employee.getEmpId());
						pfd.setFailureCode(ProvisioningFailureDetails.INVALID_IMEI);
						extraDao.insertProvisioningFailureDetails(pfd);
						throw new EffortError(4020,
								HttpStatus.PRECONDITION_FAILED);
					} else {
						
						provisioning = new Provisioning();
						provisioning.setCode(code);
						provisioning.setEmpId(employee.getEmpId());
						if(empId!=null && empId != employee.getEmpId())
						{
							pfd.setEmpId(employee.getEmpId());
							pfd.setFailureCode(ProvisioningFailureDetails.INVALID_IMEI);
							extraDao.insertProvisioningFailureDetails(pfd);
							throw new EffortError(4031, HttpStatus.PRECONDITION_FAILED);
						}
						
						if(!constantsExtra.getImeiBasedProvisioning(employee.getCompanyId()))
						{
							String effortToken = generateRandomGuId();
							employee.setEffortToken(effortToken);
							provisioning.setCode(effortToken);
							provisioning.setEffortToken(effortToken);
							employeeDao.insertProvisioningWithEffortToken(provisioning);
							getWebAdditionalManager().updateEmployeeWithIMEI(effortToken,employee.getEmpId());
						}
						else
						{
							employeeDao.insertProvisioning(provisioning);
						}
						if(isImeiBased){
							getWebAdditionalManager().updateEmployeeWithIMEI(code,employee.getEmpId());
						}
						webManager.logEmployeeAudit(employee.getEmpId(),
								employee.getEmpId(),pfd.getIpAddress(),null);
						
						if(pfd.getClientPlatform().equals("4")){
							if(!inAppDao.isPurchaseRecordAvailableForEMp(employee.getEmpId())){
								employee.setImei(code);
							insertItunePurchases(employee);
							}else{
								ITunesPurchase iTunesPurchase = inAppDao.getITunePurchaseRecord(employee.getEmpId());
								if(iTunesPurchase != null && !code.equals(iTunesPurchase.getUdid())){
									inAppDao.updateUdidCodeForEmp(employee.getEmpId(), code);
								}
							}
						}

						return employee;
					}
				} else {
					pfd.setFailureCode(ProvisioningFailureDetails.INVALID_IMEI);
					extraDao.insertProvisioningFailureDetails(pfd);
					throw new EffortError(4018, HttpStatus.PRECONDITION_FAILED);
				}
			} catch (DuplicateKeyException e) {
				try {
					employeeDao.deleteProvisioning(employee.getEmpId());
					if(empId !=null && empId != employee.getEmpId())
					{
						pfd.setEmpId(employee.getEmpId());
						pfd.setFailureCode(ProvisioningFailureDetails.INVALID_IMEI);
						extraDao.insertProvisioningFailureDetails(pfd);
						throw new EffortError(4090, HttpStatus.PRECONDITION_FAILED);
					}
					if(!constantsExtra.getImeiBasedProvisioning(employee.getCompanyId()))
					{
						String effortToken = generateRandomGuId();
						employee.setEffortToken(effortToken);
						provisioning.setEffortToken(effortToken);
						provisioning.setCode(effortToken);
						employeeDao.insertProvisioningWithEffortToken(provisioning);
						getWebAdditionalManager().updateEmployeeWithIMEI(effortToken,employee.getEmpId());
					}else{
						employeeDao.insertProvisioning(provisioning);
					}
					if(isImeiBased){
						getWebAdditionalManager().updateEmployeeWithIMEI(code,employee.getEmpId());
					}
					
					if(!Api.isEmptyString(employee.getEmpEmail())) {
						String mailBody = "<PRE>Dear "+employee.getEmpName()+", \n"
								+ "\n"
								+ "Looks like your EFFORT account has been logged in. \n"
								+ "\n"
								+ "If this is not you, please contact your manager immediately.\n"
								+ " \n"
								+ "Thanks, \n"
								+ "EFFORT Team</PRE>";
						mailTask.sendMail("", employee.getEmpEmail(), "New login detected in your EFFORT app"
								, mailBody, 2, employee.getCompanyId()+"",false,Mail.HIGH_PRIORITY_EMAIL);
					}
					
					webManager.logEmployeeAudit(employee.getEmpId(),
							employee.getEmpId(),pfd.getIpAddress(),null);
					if(pfd.getClientPlatform().equals("4")){
						if(!inAppDao.isPurchaseRecordAvailableForEMp(employee.getEmpId())){
							employee.setImei(code);
							insertItunePurchases(employee);
						}else{
							ITunesPurchase iTunesPurchase = inAppDao.getITunePurchaseRecord(employee.getEmpId());
							if(iTunesPurchase != null && !code.equals(iTunesPurchase.getUdid())){
								inAppDao.updateUdidCodeForEmp(employee.getEmpId(), code);
							}
						}
					}
					return employee;
				} catch (Exception ee) {
					pfd.setFailureCode(ProvisioningFailureDetails.INVALID_IMEI);
					extraDao.insertProvisioningFailureDetails(pfd);
					Log.info(this.getClass(), ee.toString(), ee);
					throw new EffortError(4018, HttpStatus.PRECONDITION_FAILED);
				}
			} catch (Exception e) {
				pfd.setFailureCode(ProvisioningFailureDetails.INVALID_IMEI);
				extraDao.insertProvisioningFailureDetails(pfd);
				throw new EffortError(4018, HttpStatus.PRECONDITION_FAILED);
			}
		}
	}
	
	public String generateRandomGuId() {
		return UUID.randomUUID().toString();
	} 
	
	public void insertItunePurchases(Employee employee){
		
		Subscripton subscripton=extraDao.getActiveSubscripton(employee.getCompanyId());
		String date = Api.getDateTimeInUTC(new Date(System
				.currentTimeMillis()));
		
		String subscriptionTime = subscripton.getExpiryTime();
		String subscriptionTimeWithGracePeriod = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
	try {
		c.setTime(sdf.parse(subscriptionTime));
		c.add(Calendar.DAY_OF_MONTH, subscripton.getGracePreiod()); 		
		subscriptionTimeWithGracePeriod = sdf.format(c.getTime());

		if (subscripton != null) {
			ITunesPurchase itunePurchase = new ITunesPurchase();
			itunePurchase.setEmpId(employee.getEmpId());
			itunePurchase.setPurchaseTime(Api.getDateTimeInUTC(date));
			itunePurchase.setExpiryTime(Api.getDateTimeInUTC(subscriptionTimeWithGracePeriod));
			itunePurchase.setAppStoreProductId(constants.getOneMonthSubscriptionKey());
			itunePurchase.setStatus(true);
			itunePurchase.setStatusMessage(null);
			itunePurchase.setUdid(employee.getImei());
			itunePurchase.setTrial(0);
			inAppDao.insertITunesPurchase(itunePurchase);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	
	
	public  Map<String, String> resendActivationCode(Long empPhone, HttpServletRequest request,Map<String, String> response) throws EffortError 
	{
		if(empPhone != null)
  		{
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				   ipAddress = request.getRemoteAddr();
			}
  			List<Employee> employees = null;
			try {
				//activeEmployee = webManager.getActiveEmployeeByPhone(empPhone+"");
				employees = getSyncManager().getEmployeeByPhone(empPhone+"");
			}
			catch(Exception e)
			{
				
			}
			Employee employee = null;
			boolean isActive = false;
			if(employees!=null && employees.size()>0)
			{
				for(Employee emp : employees)
				{
					if(emp.getStatus() == 1)
					{
						isActive = true;
						employee = emp;
					}
				}
			}
			if(!isActive && (employees!=null && employees.size()>0))
			{
				employee = employees.get(employees.size()-1);
			}
			
			if(employee != null && employee.getEmpTypeId() == Employee.TYPE_BACK_OFFICE)
			{
				//throw new EffortError(8802, HttpStatus.PRECONDITION_FAILED);
				response.put("response", "Failed");
				response.put("message", "It appears that you dont have mobile app access. Please get in touch with our support team for more information.");
				return response;
			}
			
			if(employee != null &&  employee.getEmpTypeId() != Employee.TYPE_BACK_OFFICE)
			{
				
				if(!isActive)
				{
					//throw new EffortError(8801, HttpStatus.PRECONDITION_FAILED);
					response.put("response", "Failed");
					response.put("message", "It appears that your ID is inactive. Please get in touch with your admin/manager for more information.");
					return response;
				}
				
				Company company = extraDao.getCompany(employee.getCompanyId());

				if (!company.isActive()) {
					//throw new EffortError(4028, HttpStatus.PRECONDITION_FAILED);
					response.put("response", "Failed");
					response.put("message", "Account inactive");
					return response;
				}
				
				if(!isCompanyActive(employee.getCompanyId()))
				{
					//throw new EffortError(8803, HttpStatus.PRECONDITION_FAILED);
					response.put("response", "Failed");
					response.put("message", "It appears that your subscription is expired. Please get in touch with your admin/manager for more information.");
					return response;
				}
				
				String msisdn_with_ISD= empPhone.longValue()+"";
				if(employee.getEmpIsdCode() != null){
					msisdn_with_ISD= employee.getEmpIsdCode().intValue()+""+empPhone.longValue();
				}
				
				if(canResendActivationCode(msisdn_with_ISD, ipAddress))
				{
					String activationcode = webExtraManager.getProvisioningOTKByEmp(employee);
					String subject = constantsExtra.getResendActivationCodeSubject().replace("{EMP_PHONE}", employee.getEmpPhone()).replace("{ACTIVATION_CODE}", activationcode);
					String body = "";
				SmsActivationTemplate smsActivationTemplate = getWebExtensionManager()
						.getSmsActivationTemplateForCompany(employee.getCompanyId() + "");
				if (constantsExtra.isKalpataruLogin() == true) {
					body = brandingConstants.getResendActivationCodeBodySmsForKalpataru()
							.replace("{EMP_PHONE}", employee.getEmpPhone())
							.replace("{ACTIVATION_CODE}", activationcode);
				} else if (smsActivationTemplate != null
						&& !Api.isEmptyString(smsActivationTemplate.getSmsActivationTemplateText())) {
					body = smsActivationTemplate.getSmsActivationTemplateText().replace("{ACTIVATION_CODE}",
							activationcode);
				} else {
					body = constantsExtra.getResendActivationCodeBody()
							.replace("{EMP_PHONE}", employee.getEmpPhone())
							.replace("{ACTIVATION_CODE}", activationcode);
				}
					OutgoingSMS outgoingSMS = new OutgoingSMS();
					outgoingSMS.setMsisdn(msisdn_with_ISD);
					outgoingSMS.setMessage(body);
					outgoingSMS.setStatus(1);
					outgoingSMS.setCompanyId(employee.getCompanyId());
					outgoingSMS.setSmsType(OutgoingSMS.FREE);
					outgoingSMS.setSendSmsViaWhatsaap(true);
					outgoingSMS.setSendSmsType(1);
					smsManager.sendSms(outgoingSMS);
					if(!Api.isEmptyString(employee.getEmpEmail()))
					{
						boolean isSendQRCodeForMobileLogin = Api.isSendQRCodeForMobileAppLogin(employee.getCreateWebUser(),employee.isGenerateQrCode(),employee.isEnableMultiUserLogin());
						if(isSendQRCodeForMobileLogin) {
							String qrCodeLink = getWebExtensionManager().getQRCodeForMobileAppLogin(activationcode, employee);
							if(!Api.isEmptyString(qrCodeLink)) {
								String qrCodeInformation = constantsExtra.getQrCodeInformation().replace("<media_link>", qrCodeLink);
								body = body.concat(qrCodeInformation);
							}
						}
						try {
							/*
							 * mailTask.sendMail(employee.getEmpId() + "", employee.getEmpEmail(), subject,
							 * body, employee.getCompanyId()+"",Mail.WEBSITE_RELATED);
							 */
							mailTask.sendHighPriorityMail(employee.getManagerId() + "", employee.getEmpEmail(),
									subject, body, Mail.BODY_TYPE_HTML, employee.getCompanyId() + "", false,
									Mail.HIGH_PRIORITY_EMAIL,Mail.WEBSITE_RELATED);
						} catch (MessagingException e) {
							Log.info(this.getClass(), e.toString(), e);
						}
					}
					
					extraSupportAdditionalDao.insertAuditForActivationCodeResend(msisdn_with_ISD, ipAddress);
				}
				
				response.put("response", "success");
				response.put("message", "OTP sent successfully.");
			}
			else
			{
				//throw new EffortError(8800, HttpStatus.PRECONDITION_FAILED);
				response.put("response", "Failed");
				response.put("message", "It appears that you haven't registered yet. Please get in touch with your admin/manager for more information.");
				return response;
			}
			
			
  		}
		return response;
	}
	
	
	public boolean isCompanyActive(long companyId) {
		try {
			Company company = extraDao.getCompany(companyId);

			if (company.isActive()) {
				Subscripton subscripton = getSubscripton(companyId);
				if (subscripton == null) {
					return false;
				}

				long now = System.currentTimeMillis();
				long expired = Api
						.getDateTimeInUTC(subscripton.getExpiryTime())
						.getTime();

				long diff = (expired - now);
				diff += (subscripton.getGracePreiod() * 24 * 60 * 60 * 1000l);

				if (diff <= 0) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
			return false;
		}
	}
	
	  public Subscripton getSubscripton(long companyId) {
		    Subscripton subscripton = null;
		    try {
		      subscripton = extraDao.getActiveSubscripton(companyId);
		    } catch (Exception e) {
		      Log.ignore(this.getClass(), e);
		    }
		    return subscripton;
		  }
	
	  
	  
	  public boolean canResendActivationCode(String empPhone, String ipAddress) {
			Long count = extraSupportAdditionalDao.getPreviuosResendActivationCodeAuditCountByPhone(empPhone, -1l); // get count in last 1l minute
			if(count == 0) // if it is sent to phone in last 1l minute, then don't resend
			{
				 count = extraSupportAdditionalDao.getPreviuosResendActivationCodeAuditCountByIpAddress(ipAddress, -60l); // get count in last 60l minute
				 if(count < 100) // if request came via ipAddress in last 60l minutes is greater than 100, then don't resend
				 {
					 return true;
				 }
			}
			return false;
		}
	
	  
	  
	  
	  public Employee getEmployeeByOtp(String code, String empPhone,
				String activationCode, String override,  ProvisioningFailureDetails pfd, String apiLevel) throws EffortError {
			WebUser webUser = new WebUser();

			try 
			{
				List<ProvisioningOTKey> provisioningOTKeys = webExtraManager.getActiveProvisioningKeyByEmpPhone(empPhone);
				
				if(provisioningOTKeys!=null && provisioningOTKeys.size()>0){
					ProvisioningOTKey provisioningOTKey = provisioningOTKeys.get(0);
					Employee webUserEmp = webManager.getEmployee(provisioningOTKey.getEmpId()+"");
					if(webUserEmp!=null && !Api.isEmptyString(webUserEmp.getEmpEmail())){
						
						try {
							webUser = webManager.getWebUserByUsernameEmp(webUserEmp.getEmpEmail());
						} catch (IncorrectResultSizeDataAccessException e) {
						}
						
						if(webUser!=null){
							List<String> authoritiesRaw = webManager.getWebUserAuthorities(webUser
									.getUsername());

							if(authoritiesRaw!=null && !authoritiesRaw.isEmpty()){
								Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
								if (authoritiesRaw != null) {
									for (String authorityStr : authoritiesRaw) {
										authorities.add(new RemoteUserAuthority(authorityStr));
									}
								}
								webUser.setAuthorities(authorities);
								boolean noMobileAccess = false;
								if(webUser.hasRole("ROLE_MGNR_NO_MOBILE") || webUser.hasRole("ROLE_USER_NO_MOBILE"))
								{
									noMobileAccess = true;
								}
								else if(webUser.hasRole("ROLE_MGNR_NO_REPORT_MOBILE") || webUser.hasRole("ROLE_USER_NO_REPORT_MOBILE"))
								{
									noMobileAccess = true;
								}
								else if(webUser.hasRole("ROLE_WEBLITE_NO_MOBILE"))
								{
									noMobileAccess = true;
								}
								else if (webUser.hasRole("ROLE_CONFIGURATOR") || webUser.hasRole("ROLE_MGNR_WEB_CONFIG_WEBLITE") || webUser.hasRole("ROLE_USER_WEB_CONFIG_WEBLITE")) {
									noMobileAccess = true;
								}
								if(noMobileAccess){
									throw new EffortError(9990, HttpStatus.PRECONDITION_FAILED);
								}
							}
							
						}
					}
					
					if(webUserEmp!=null) {
						
						Company company = extraDao.getCompany(webUserEmp.getCompanyId());
						if(company!=null) {
							if(company.isEffortPresence()) {
								throw new EffortError(9991, HttpStatus.PRECONDITION_FAILED);
							}
						}
						
					}
					
				}
				
				if(provisioningOTKeys!=null && provisioningOTKeys.size()>0)
				{
					ProvisioningOTKey provisioningOTKey = provisioningOTKeys.get(0);
					webUser = new WebUser();
					webUser.setEmpId(provisioningOTKey.getEmpId());
					if(provisioningOTKey.getKey().trim().equalsIgnoreCase(activationCode))
					{
						try 
						{
							webExtraManager.updateActiveProvisioningKeyByEmpPhoneAndKey(empPhone, activationCode);
							
							Employee employee= getEmployeeByEmpPhone(code, override,empPhone,pfd.getIpAddress(),apiLevel,webUser.getEmpId());
							if(pfd.getClientPlatform().equals("4")){
								if(!inAppDao.isPurchaseRecordAvailableForEMp(employee.getEmpId())){
									employee.setImei(code);
								insertItunePurchases(employee);
								}else{
									ITunesPurchase iTunesPurchase = inAppDao.getITunePurchaseRecord(employee.getEmpId());
									if(iTunesPurchase != null && !code.equals(iTunesPurchase.getUdid())){
										inAppDao.updateUdidCodeForEmp(employee.getEmpId(), code);
									}
								}
								
							}
							return employee;
						} catch (EffortError e) {
							if (e.getCode() == 4018) {
								provision(webUser.getEmpId(), code,pfd.getIpAddress());
								webExtraManager.updateActiveProvisioningKeyByEmpPhoneAndKey(empPhone, activationCode);
								Employee employee= getEmployeeByEmpPhone(code, override,empPhone,pfd.getIpAddress(),apiLevel,webUser.getEmpId());
								/*if(pfd.getClientPlatform().equals("4") && !inAppDao.isPurchaseRecordAvailableForEMp(employee.getEmpId())){
									insertItunePurchases(employee);
								}*/
								if(pfd.getClientPlatform().equals("4")){
									if(!inAppDao.isPurchaseRecordAvailableForEMp(employee.getEmpId())){
										employee.setImei(code);
										insertItunePurchases(employee);
									}else{
										ITunesPurchase iTunesPurchase = inAppDao.getITunePurchaseRecord(employee.getEmpId());
										
										if(iTunesPurchase != null && !code.equals(iTunesPurchase.getUdid())){
											inAppDao.updateUdidCodeForEmp(employee.getEmpId(), code);
										}
									}
								}
								
								return employee;
							} else {
								pfd.setEmpPhone(empPhone);
								pfd.setActivationCode(activationCode);
								pfd.setImei(code);
								pfd.setOverride(override);
								pfd.setEmpId(provisioningOTKey.getEmpId());
								pfd.setFailureCode(ProvisioningFailureDetails.INVALID_ACTIVATIONCODE);
								extraDao.insertProvisioningFailureDetails(pfd);
								throw e;
							}
						}
					}
					else
					{
						pfd.setEmpPhone(empPhone);
						pfd.setActivationCode(activationCode);
						pfd.setImei(code);
						pfd.setOverride(override);
						pfd.setEmpId(provisioningOTKey.getEmpId());
						pfd.setFailureCode(ProvisioningFailureDetails.INVALID_ACTIVATIONCODE);
						extraDao.insertProvisioningFailureDetails(pfd);
						throw new EffortError(4037, HttpStatus.PRECONDITION_FAILED);
					}
				}
				else
				{
					pfd.setEmpPhone(empPhone);
					pfd.setActivationCode(activationCode);
					pfd.setImei(code);
					pfd.setOverride(override);
					pfd.setFailureCode(ProvisioningFailureDetails.INVALID_ACTIVATIONCODE);
					extraDao.insertProvisioningFailureDetails(pfd);
					throw new EffortError(4037, HttpStatus.PRECONDITION_FAILED);
				}
			} 
			catch (IncorrectResultSizeDataAccessException e) 
			{
				pfd.setEmpPhone(empPhone);
				pfd.setActivationCode(activationCode);
				pfd.setImei(code);
				pfd.setOverride(override);
				pfd.setFailureCode(ProvisioningFailureDetails.INVALID_ACTIVATIONCODE);
				extraDao.insertProvisioningFailureDetails(pfd);
				throw new EffortError(4037, HttpStatus.PRECONDITION_FAILED);
			}
		}
	  
	  
	  public Employee getEmployeeByEmpPhone(String code, String override,String empPhone, String ipAddress, String apiLevel, long empId)
				throws EffortError {
			Provisioning provisioning = null;
			
			Employee loginEmployee = employeeDao.getEmployeeBasicDetailsByEmpId(empId+"");
			boolean isImeiBased = true;
			if(loginEmployee!=null)
			{
				isImeiBased = constantsExtra.getImeiBasedProvisioning(loginEmployee.getCompanyId());
			}

			try {
				if(isImeiBased){
					provisioning = employeeDao.getProvisioningByCodeORPendingCode(code);
				}
			} catch (Exception e) {
				
			}

			if (provisioning != null) {
				
				Employee employee = null;
				if(isImeiBased){
					employee = employeeDao.getEmployee(provisioning.getEmpId()+"");
				}else
				{
					employee = employeeDao.getEmployee(empId+"");
				}
				
				Employee otpEmployee = employeeDao.getActiveEmployeeByPhoneNo(empPhone);
				if (employee.getStatus() == 0) {
					throw new EffortError(4020, HttpStatus.PRECONDITION_FAILED);
					
				}
				
				if(employee ==null || otpEmployee == null || otpEmployee.getEmpId() != employee.getEmpId())
				{
					throw new EffortError(4031, HttpStatus.PRECONDITION_FAILED);
				}

				if (provisioning.getCode() != null
						&& provisioning.getCode().equals(code)) {
					if(!constantsExtra.getImeiBasedProvisioning(employee.getCompanyId()))
					{
						String effortToken = generateRandomGuId();
						extraSupportAdditionalDao.updateEffortTokenInProvisioningsForGivenEmpIdAndEmpCode(provisioning.getEmpId(),effortToken);
						employee.setEffortToken(effortToken);
						if((employee.getImei() == null || (employee.getImei()!=null && !employee.getImei().equals(effortToken))) && !isImeiBased)
						{
							getWebAdditionalManager().updateEmployeeWithIMEI(effortToken,employee.getEmpId());
						}
					}
					else if((employee.getImei() == null || (employee.getImei()!=null && !employee.getImei().equals(code))) && isImeiBased)
					{
						getWebAdditionalManager().updateEmployeeWithIMEI(code,employee.getEmpId());
					}
					return employee;
				} else if (provisioning.getPendingCode() != null
						&& provisioning.getPendingCode().equals(code)) {

					if (override != null && override.equals("1")) {
						provisioning.setCode(code);
						provisioning.setPendingCode(null);
						
						if(!constantsExtra.getImeiBasedProvisioning(employee.getCompanyId()))
						{
							String effortToken = generateRandomGuId();
							employee.setEffortToken(effortToken);
							provisioning.setEffortToken(effortToken);
							provisioning.setCode(effortToken);
							employeeDao.updateProvisioningWithEffortToken(provisioning);
							if((employee.getImei() == null || (employee.getImei()!=null && !employee.getImei().equals(effortToken))) && !isImeiBased)
							{
								getWebAdditionalManager().updateEmployeeWithIMEI(effortToken,employee.getEmpId());
							}
						}else {
							employeeDao.updateProvisioning(provisioning);
						}
						if(isImeiBased){
							getWebAdditionalManager().updateEmployeeWithIMEI(code,employee.getEmpId());
						}
						webManager.logEmployeeAudit(employee.getEmpId(),
								employee.getEmpId(),ipAddress,null);

						String msisdn = employee.getEmpPhone();
						if(employee.getEmpIsdCode() != null){
							msisdn= employee.getEmpIsdCode().intValue()+""+msisdn;
						}
						OutgoingSMS outgoingSMS = new OutgoingSMS();
						outgoingSMS.setMsisdn(msisdn);
						outgoingSMS.setMessage(constants
								.getChangeTrackingDviceSms());
						outgoingSMS.setCompanyId(employee.getCompanyId());
						outgoingSMS.setSmsType(OutgoingSMS.FREE);
						outgoingSMS.setSendSmsType(1);
						try {
							smsManager.sendSms(outgoingSMS);
						} catch (Exception e) {
							Log.info(this.getClass(), e.toString(), e);
						}

						return employee;
					} else {
						throw new EffortError(4019, HttpStatus.PRECONDITION_FAILED);
					}
				} else {
					throw new EffortError(4018, HttpStatus.PRECONDITION_FAILED);
				}
			} else {
				Employee employee = null;
				try {
					// employee = employeeDao.getEmployeeByImei(code);
					employee = employeeDao.getActiveEmployeeByPhoneNo(empPhone);
					if (employee != null) {
						if (employee.getStatus() == 0) {
							throw new EffortError(4020,
									HttpStatus.PRECONDITION_FAILED);
						} else {
							
							provisioning = new Provisioning();
							provisioning.setCode(code);
							provisioning.setEmpId(employee.getEmpId());
							if(!constantsExtra.getImeiBasedProvisioning(employee.getCompanyId()))
							{
								String effortToken = generateRandomGuId();
								employee.setEffortToken(effortToken);
								provisioning.setCode(effortToken);
								provisioning.setEffortToken(effortToken);
								employeeDao.insertProvisioningWithEffortToken(provisioning);
								getWebAdditionalManager().updateEmployeeWithIMEI(effortToken,employee.getEmpId());
							}else{
								employeeDao.insertProvisioning(provisioning);
							}
							if(isImeiBased){
								getWebAdditionalManager().updateEmployeeWithIMEI(code,employee.getEmpId());
							}
							webManager.logEmployeeAudit(employee.getEmpId(),
									employee.getEmpId(),ipAddress,null);

							return employee;
						}
					} else {
						throw new EffortError(4018, HttpStatus.PRECONDITION_FAILED);
					}
				} catch (DuplicateKeyException e) {
					try {
						if(!employee.getImei().equalsIgnoreCase(code)) {
							if(!Api.isEmptyString(employee.getEmpEmail())) {
								String mailBody = "<PRE>Dear "+employee.getEmpName()+", \n"
										+ "\n"
										+ "Looks like your EFFORT account has been logged in. \n"
										+ "\n"
										+ "If this is not you, please contact your manager immediately.\n"
										+ " \n"
										+ "Thanks, \n"
										+ "EFFORT Team</PRE>";
								mailTask.sendMail("", employee.getEmpEmail(), "New login detected in your EFFORT app"
										, mailBody, 2, employee.getCompanyId()+"",false,Mail.PRODUCTIVITY_RELATED);
								
								/*
								 * JSONObject multiCastRequestObject =
								 * getEffortPluginManager().getGoogleFirebaseMultiCastRequestObject(pushIds,
								 * clientPlatform, messageLiveDurationInMillies, jmsMessage.getCompanyId(),
								 * messageDataObj,actionType);
								 * getEffortPluginManager().sendGoogleFirebaseMultiCastMessages(
								 * multiCastRequestObject, clientPlatform,employeeDevices);
								 */
							}
						}
						employeeDao.deleteProvisioning(employee.getEmpId());
						if(!constantsExtra.getImeiBasedProvisioning(employee.getCompanyId()))
						{
							String effortToken = generateRandomGuId();
							employee.setEffortToken(effortToken);
							provisioning.setCode(effortToken);
							provisioning.setEffortToken(effortToken);
							employeeDao.insertProvisioningWithEffortToken(provisioning);
							getWebAdditionalManager().updateEmployeeWithIMEI(effortToken,employee.getEmpId());
						}else{
							employeeDao.insertProvisioning(provisioning);
						}
						
						if(isImeiBased){
							getWebAdditionalManager().updateEmployeeWithIMEI(code,employee.getEmpId());
						}
						webManager.logEmployeeAudit(employee.getEmpId(),
								employee.getEmpId(),ipAddress,null);
						return employee;
					} catch (Exception ee) {
						Log.info(this.getClass(), ee.toString(), ee);
						throw new EffortError(4018, HttpStatus.PRECONDITION_FAILED);
					}
				} catch (Exception e) {
					throw new EffortError(4018, HttpStatus.PRECONDITION_FAILED);
				}
			}
		}

	  public ErrorResponse prepareError(int code) {
		    ErrorResponse errorResponse = new ErrorResponse();
		    errorResponse.setCode(code);
		    errorResponse.setDescription(getErrorDescription(code));

		    return errorResponse;
		  }
	  public String getErrorDescription(int code) {
		    return responseCodes.getDescription(code);
		  }
	  public String getSmsc() {
			return constants.getSmsc();
		}
	  

}
