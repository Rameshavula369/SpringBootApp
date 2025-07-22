package com.effort.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.effort.dao.SmsDao;
import com.effort.entity.OutgoingSMS;
import com.effort.settings.Constants;
import com.effort.util.Api;
import com.effort.util.Log;


import org.springframework.stereotype.Service;


@Service
public class SmsManager {

	
	private RestTemplate restTemplateForMessaging;
	
	@Autowired
	private SmsDao smsDao; 
	
	@Autowired
	private Constants constants;
	
	public long sendSms(OutgoingSMS outgoingSMS){
		int status = -1;
		long time = System.currentTimeMillis();
		Log.info(getClass(), "sendSms starts");
		String logText = "sendSms() // msisdn = "+outgoingSMS.getMsisdn();
		Log.info(getClass(), logText+" starts...");
		try{
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("msisdn", Api.isEmptyString(outgoingSMS.getMsisdn()) ? "" : outgoingSMS.getMsisdn().trim());
			
			String message = outgoingSMS.getMessage();
			if(!Api.isEmptyString(outgoingSMS.getMessage())) {
				message = message.replace("+", "%2B");
			}
			parameters.put("message", message);
//			if(outgoingSMS.getCompanyId()==null){
//				parameters.put("companyId", null);
//			}else{
			parameters.put("companyId", ""+outgoingSMS.getCompanyId());
//			}
			
			parameters.put("smsType", ""+outgoingSMS.getSmsType());
			String sendSmsViaWhatsapp = "0";
			if(outgoingSMS.isSendSmsViaWhatsaap())
			{
				sendSmsViaWhatsapp = "1";
			}
			
			String sendSmsType = "1";
			if(outgoingSMS.getSendSmsType() != null) {
				sendSmsType = outgoingSMS.getSendSmsType()+"";
			}
			parameters.put("sendSmsViaWhatsaap", sendSmsViaWhatsapp);
			parameters.put("sendSmsType", sendSmsType);

			if(!Api.isEmptyString(outgoingSMS.getPlaceholdersJson())) {
				parameters.put("placeholdersJson", outgoingSMS.getPlaceholdersJson());
			}else {
				parameters.put("placeholdersJson", "");
			}

			long time1 = System.currentTimeMillis();
			Log.info(getClass(), logText+" sendSms from restTemplateForMessaging started... smsGatewayUrl = "+constants.getSmsGatewayUrl());
			Log.info(getClass(), logText+" sendSms from restTemplateForMessaging started... new - parameters = "+parameters);
			String resp = restTemplateForMessaging.getForObject(constants.getSmsGatewayUrl(), String.class, parameters);
			Log.info(getClass(), logText+" sendSms from restTemplateForMessaging ends and took :"+(System.currentTimeMillis()-time1));
			Log.info(this.getClass(), logText+" response = "+resp +" Mobile No : "+outgoingSMS.getMsisdn());
			
			if(resp != null && !"failure".equalsIgnoreCase(resp)){
				status = 1;
			}
		} catch (HttpClientErrorException e) {
			Log.info(this.getClass(), logText+" Outgoing sms failed: "+e.getMessage()+": "+e.getResponseBodyAsString()+"  Mobile No :  "+outgoingSMS.getMsisdn());
		} catch (Exception e) {
			Log.info(this.getClass(),logText+" Outgoing sms failed: "+e.toString()+" Mobile No : "+outgoingSMS.getMsisdn(), e);
		}
		Log.info(this.getClass(), logText+" Mobile No status is :"+status);
		outgoingSMS.setStatus(status);
		long id = smsDao.saveOutgoingSms(outgoingSMS);
		Log.info(getClass(), logText+" sendSms ends and took :"+(System.currentTimeMillis()-time));
		return id;
	}
	
}
