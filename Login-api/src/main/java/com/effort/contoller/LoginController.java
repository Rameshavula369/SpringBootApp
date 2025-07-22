package com.effort.contoller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.effort.context.AppContext;
import com.effort.dao.EmployeeDao;
import com.effort.entity.Employee;
import com.effort.entity.ErrorResponse;
import com.effort.entity.ProvisioningFailureDetails;
import com.effort.entity.WebUser;
import com.effort.exception.EffortError;
import com.effort.manager.MainManager;
import com.effort.manager.WebExtensionManager;
import com.effort.manager.WebManager;
import com.effort.manager.WebSupportManager;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.util.Api;
import com.effort.util.Log;
import com.effort.util.UrlSigner;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping(value = "/v17/api")
public class LoginController {
	
	Logger logger = LogManager.getLogger("synclog"); 
	
	@Autowired
	private MainManager mainManager;
	
	@Autowired
	private WebSupportManager webSupportManager;
	
	@Autowired
	private WebManager webManager;
	
	@Autowired
	private ConstantsExtra constantsExtra; 
	
	@Autowired
	private Constants constants;
	
	private WebExtensionManager getWebExtensionManager() {
		WebExtensionManager webExtensionManager = AppContext.getApplicationContext().getBean("webExtensionManager",
				WebExtensionManager.class);
		return webExtensionManager;
	}
	
	private EmployeeDao getEmployeeDao() {
		EmployeeDao employeeDao = AppContext.getApplicationContext().getBean("employeeDao", EmployeeDao.class);
		return employeeDao;
	}
	
	@RequestMapping(value = "/init/login", method = RequestMethod.POST)
	@ResponseBody
	public Employee initLoginPost(@RequestParam(value = "override", required = false) String override,
			@RequestParam(value = "clientPlatform", required = false) String clientPlatform,
			@RequestParam(value = "clientVersion", required = false) String clientVersion,
			@RequestParam(value = "osVersion", required = false) String osVersion,
			@RequestParam(value = "apiLevel", required = false) String apiLevel,
			@RequestParam(value = "productCode", required = false) String productCode,
			@RequestParam(value = "versionCode", required = false) String versionCode,
			@RequestParam(value = "pushId", required = false) String pushId,
			@RequestParam(value = "signature", required = false, defaultValue = "") String signature,
			@RequestBody String jsonString, HttpServletRequest request) throws EffortError {

		// Start of for Security issue
		Log.info(getClass(), "initLoginPost() //  apiLevel = " + apiLevel + " signature = " + signature
				+ " jsonString = " + jsonString);
		if (!Api.isEmptyString(apiLevel) && Integer.parseInt(apiLevel) >= 15) {
			System.out.println(request.getRequestURL().toString() + "?" + request.getQueryString());
			String requestedUrl = request.getRequestURL().toString() + "?" + request.getQueryString();
			String serverSignatureKey = "";
			try {
				serverSignatureKey = UrlSigner.getSignedUrl(requestedUrl, jsonString, "LWFKFv5GyJPYvgOMT2n0PvMiTXg=");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			if (Api.isEmptyString(signature)
					|| (!Api.isEmptyString(signature) && !signature.equalsIgnoreCase(serverSignatureKey))) {
				throw new EffortError(7005, HttpStatus.PRECONDITION_FAILED);
			}
		}
		String code = "";
		String username = "";
		String password = "";
		String encryptionKey = "";
		try {
			JSONParser parser = new JSONParser();
			org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) parser.parse(jsonString);
			code = (String) jsonObj.get("code");
			username = (String) jsonObj.get("username");
			password = (String) jsonObj.get("password");
			encryptionKey = (String) jsonObj.get("encryptionKey");
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		// End of code for security issue
		ProvisioningFailureDetails pfd = new ProvisioningFailureDetails();
		pfd.setClientPlatform(clientPlatform);
		pfd.setClientVersion(clientVersion);
		pfd.setOsVersion(osVersion);
		pfd.setProductCode(productCode);
		pfd.setVersionCode(versionCode);
		pfd.setIpAddress(request.getRemoteAddr());
		int loginType = 1;
		Employee employee = mainManager.getEmployeeByLogin(code, username, password, override, pfd, apiLevel,
				loginType);
		if (employee != null) {
	         String message = getWebExtensionManager().checkEmployeeExpiryOnSync(employee.getEmpId()+"",Long.parseLong(employee.getCompanyId()+""));
	         if(message != null) {
	        	 Log.info(this.getClass(),employee.getEmpId()  + " Expired employee");
	        	 throw new EffortError(99909,message,HttpStatus.PRECONDITION_FAILED);
	         }
		}
		if (employee != null && !Api.isEmptyString(encryptionKey)) {
			if (!Api.isEmptyString(apiLevel) && Integer.parseInt(apiLevel) > 18  && !"4".equals(clientPlatform)) {
				try {
					encryptionKey = Api.decryptRSAEncryptedString(encryptionKey, Constants.RSA_PRIVATE_KEY);
				} catch (Exception e) {
					Log.info(getClass(), "initCode() // Exception occured while decryptRSAEncryptedString()", e);
					throw new EffortError(7005, HttpStatus.PRECONDITION_FAILED);
				}
			}
			webSupportManager.updateEmployeeEncryptionKey(employee.getEmpId(), encryptionKey);
		}
		
		if(employee != null && employee.getEmployeeMentType() == Employee.TYPE_GIG_USER)
		{
			WebUser webUser = null;
			try {
				webUser = webManager.getWebUserByUsernameEmp(username);
			} catch (IncorrectResultSizeDataAccessException e) {
			}
			
			if(webUser!=null)
			{
				employee.setWebUserUserName(webUser.getUsername());
				employee.setWebUserPasswordEncrypted(webUser.getPassword());
			}
		}

		return employee;
	}
	
	
	@RequestMapping(value = "/resend/activition/code", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> resendActivationCode(@RequestParam(value = "empPhone", required = true) Long empPhone,
			@RequestParam(value = "signature", required = true) String signature, HttpServletRequest request)
			throws EffortError {
		System.out.println(request.getRequestURL().toString() + "?" + request.getQueryString());
		String requestedUrl = request.getRequestURL().toString() + "?" + request.getQueryString();
		String serverSignatureKey = "";
		try {
			serverSignatureKey = UrlSigner.getSignedUrl(requestedUrl, "", "LWFKFv5GyJPYvgOMT2n0PvMiTXg=");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (Api.isEmptyString(signature) || !signature.equalsIgnoreCase(serverSignatureKey)) {
			throw new EffortError(7005, HttpStatus.PRECONDITION_FAILED);
		}

		Map<String, String> response = new HashMap<String, String>();
		response = mainManager.resendActivationCode(empPhone, request,response);
		return response;
	}

	@RequestMapping(value = "/init/{code}", method = RequestMethod.POST)
	@ResponseBody
	public Employee initLogin(@PathVariable("code") String code,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "override", required = false) String override,
			@RequestParam(value = "clientPlatform", required = false) String clientPlatform,
			@RequestParam(value = "clientVersion", required = false) String clientVersion,
			@RequestParam(value = "osVersion", required = false) String osVersion,
			@RequestParam(value = "apiLevel", required = false) String apiLevel,
			@RequestParam(value = "productCode", required = false) String productCode,
			@RequestParam(value = "versionCode", required = false) String versionCode,
			@RequestParam(value = "pushId", required = false) String pushId, HttpServletRequest request)
			throws EffortError {

		ProvisioningFailureDetails pfd = new ProvisioningFailureDetails();
		pfd.setClientPlatform(clientPlatform);
		pfd.setClientVersion(clientVersion);
		pfd.setOsVersion(osVersion);
		pfd.setProductCode(productCode);
		pfd.setVersionCode(versionCode);
		pfd.setIpAddress(request.getRemoteAddr());
		int loginType = 1;
		return mainManager.getEmployeeByLogin(code, username, password, override, pfd, apiLevel, loginType);
	}

	
	@RequestMapping(value = "/init/{code}", method = RequestMethod.GET)
	@ResponseBody
	public Employee initCode(@PathVariable("code") String code,
			@RequestParam(value = "override", required = false) String override,
			@RequestParam(value = "clientPlatform", required = false) String clientPlatform,
			@RequestParam(value = "clientVersion", required = false) String clientVersion,
			@RequestParam(value = "osVersion", required = false) String osVersion,
			@RequestParam(value = "apiLevel", required = false) String apiLevel,
			@RequestParam(value = "productCode", required = false) String productCode,
			@RequestParam(value = "versionCode", required = false) String versionCode,
			@RequestParam(value = "pushId", required = false) String pushId, HttpServletRequest request)
			throws EffortError {

		ProvisioningFailureDetails pfd = new ProvisioningFailureDetails();
		pfd.setClientPlatform(clientPlatform);
		pfd.setClientVersion(clientVersion);
		pfd.setOsVersion(osVersion);
		pfd.setProductCode(productCode);
		pfd.setVersionCode(versionCode);
		pfd.setImei(code);
		pfd.setOverride(override);
		pfd.setIpAddress(request.getRemoteAddr());
		int loginType = 0;
		return mainManager.getEmployeeByCode(code, override, pfd, apiLevel, null, loginType);
	}

	
	
	@RequestMapping(value = "/init/", method = RequestMethod.POST)
	@ResponseBody
	public Employee initCodePost(@RequestParam(value = "override", required = false) String override,
			@RequestParam(value = "clientPlatform", required = false) String clientPlatform,
			@RequestParam(value = "clientVersion", required = false) String clientVersion,
			@RequestParam(value = "osVersion", required = false) String osVersion,
			@RequestParam(value = "apiLevel", required = false) String apiLevel,
			@RequestParam(value = "productCode", required = false) String productCode,
			@RequestParam(value = "versionCode", required = false) String versionCode,
			@RequestParam(value = "pushId", required = false) String pushId,
			@RequestParam(value = "signature", required = false, defaultValue = "") String signature,
			@RequestParam(value = "clientEncryptionAware", required = false, defaultValue = "false") String clientEncryptionAware,
			@RequestBody String jsonString, HttpServletRequest request) throws EffortError {

		// Start of for Security issue

		if (!Api.isEmptyString(apiLevel) && Integer.parseInt(apiLevel) >= 15) {
			System.out.println(request.getRequestURL().toString() + "?" + request.getQueryString());
			String requestedUrl = request.getRequestURL().toString() + "?" + request.getQueryString();
			String serverSignatureKey = "";
			try {
				serverSignatureKey = UrlSigner.getSignedUrl(requestedUrl, jsonString, "LWFKFv5GyJPYvgOMT2n0PvMiTXg=");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (Api.isEmptyString(signature)
					|| (!Api.isEmptyString(signature) && !signature.equalsIgnoreCase(serverSignatureKey))) {
				throw new EffortError(7005, HttpStatus.PRECONDITION_FAILED);
			}
		}

		String code = "";
		String encryptionKey = "";
		try {
			JSONParser parser = new JSONParser();
			org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) parser.parse(jsonString);
			code = (String) jsonObj.get("code");
			encryptionKey = (String) jsonObj.get("encryptionKey");
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		Employee loginEmployee = null;
		try {
			loginEmployee = getEmployeeDao().getActiveEmployeeByImei(code);
		} catch (DuplicateKeyException e) {
			getEmployeeDao().deleteProvisioning(loginEmployee.getEmpId());
			throw new EffortError(4018, HttpStatus.PRECONDITION_FAILED);
		} catch (Exception e) {
			throw new EffortError(4018, HttpStatus.PRECONDITION_FAILED);
		}

		if (loginEmployee != null) {
	         String message = getWebExtensionManager().checkEmployeeExpiryOnSync(loginEmployee.getEmpId()+"",Long.parseLong(loginEmployee.getCompanyId()+""));
	         if(message != null) {
	        	 Log.info(this.getClass(),loginEmployee.getEmpId()  + " Expired employee");
	        	 throw new EffortError(99909,message,HttpStatus.PRECONDITION_FAILED);
	         }
		}
		if (loginEmployee != null && !constantsExtra.getImeiBasedProvisioning(loginEmployee.getCompanyId())) {
			Log.info(this.getClass(),
					"ImeiBasedProvisioning is Disabled for companyId :" + loginEmployee.getCompanyId());
			throw new EffortError(4018, HttpStatus.PRECONDITION_FAILED);
		}

		// End of code for security issue
		ProvisioningFailureDetails pfd = new ProvisioningFailureDetails();
		pfd.setClientPlatform(clientPlatform);
		pfd.setClientVersion(clientVersion);
		pfd.setOsVersion(osVersion);
		pfd.setProductCode(productCode);
		pfd.setVersionCode(versionCode);
		pfd.setImei(code);
		pfd.setOverride(override);
		pfd.setIpAddress(request.getRemoteAddr());
		int loginType = 0;
		Employee employee = mainManager.getEmployeeByCode(code, override, pfd, apiLevel, null, loginType);
		if (employee != null && !Api.isEmptyString(encryptionKey)) {
			if ((!Api.isEmptyString(apiLevel) && Integer.parseInt(apiLevel) > 18 && !"4".equals(clientPlatform))
					|| "true".equalsIgnoreCase(clientEncryptionAware)) {
				try {
					encryptionKey = Api.decryptRSAEncryptedString(encryptionKey, Constants.RSA_PRIVATE_KEY);
				} catch (Exception e) {
					Log.info(getClass(), "initCode() // Exception occured while decryptRSAEncryptedString()", e);
					throw new EffortError(7005, HttpStatus.PRECONDITION_FAILED);
				}
			}
			webSupportManager.updateEmployeeEncryptionKey(employee.getEmpId(), encryptionKey);
		}
		return employee;
	}

	
	
	@RequestMapping(value = "/init/otp/{code}", method = RequestMethod.POST)
	@ResponseBody
	public Employee initLoginByOtp(@PathVariable("code") String code,
			@RequestParam(value = "empPhone", required = false) String empPhone,
			@RequestParam(value = "activationcode", required = false) String activationcode,
			@RequestParam(value = "override", required = false) String override,
			@RequestParam(value = "clientPlatform", required = false) String clientPlatform,
			@RequestParam(value = "clientVersion", required = false) String clientVersion,
			@RequestParam(value = "osVersion", required = false) String osVersion,
			@RequestParam(value = "apiLevel", required = false) String apiLevel,
			@RequestParam(value = "productCode", required = false) String productCode,
			@RequestParam(value = "versionCode", required = false) String versionCode,
			@RequestParam(value = "pushId", required = false) String pushId, HttpServletRequest request)
			throws EffortError {

		ProvisioningFailureDetails pfd = new ProvisioningFailureDetails();
		pfd.setClientPlatform(clientPlatform);
		pfd.setClientVersion(clientVersion);
		pfd.setOsVersion(osVersion);
		pfd.setProductCode(productCode);
		pfd.setVersionCode(versionCode);
		pfd.setIpAddress(request.getRemoteAddr());

		if(empPhone.length() > 20) {
			throw new EffortError(9903, HttpStatus.PRECONDITION_FAILED);
		}
		return mainManager.getEmployeeByOtp(code, empPhone, activationcode, override, pfd, apiLevel);
	}

	@ExceptionHandler(EffortError.class)
    public ResponseEntity<?> handleEffortError(HttpServletRequest request, EffortError error) {
        errorlog(request, error);
        ErrorResponse errorResponse = mainManager.prepareError(error.getCode());
        errorResponse.appendDescription(error.getDesc());
        if (error.getCode() == 4018 || error.getCode() == 4019) {
            errorResponse.setSmsc(mainManager.getSmsc());
        }
        ResponseEntity<ErrorResponse> responseEntity = null;
        if (!constants.isOnly200()) {
            responseEntity = new ResponseEntity<ErrorResponse>(errorResponse, error.getHttpStatus());
        } else {
            responseEntity = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
        }

        return responseEntity;
    }
	public void errorlog(HttpServletRequest request, Throwable error) {
		if (request != null) {
			Log.info(this.getClass(), "URL: " + request.getRequestURL().toString());
			Log.info(this.getClass(), "Query: " + request.getQueryString());
			Log.info(this.getClass(), "IP: " + request.getRemoteAddr());
			Log.info(this.getClass(), "Path: " + request.getPathInfo());
		}
		Log.info(this.getClass(), error.toString(), error);
	}
}