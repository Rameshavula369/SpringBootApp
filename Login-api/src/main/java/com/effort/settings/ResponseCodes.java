package com.effort.settings;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ResponseCodes {

	private Map<Integer, String> responseCodeMap;
	
	public ResponseCodes(){
		responseCodeMap = new HashMap<Integer, String>();
		responseCodeMap.put(2000, "Success");
		responseCodeMap.put(4000, "Invalid clientPlatform");
		responseCodeMap.put(4001, "Bad Json");
		responseCodeMap.put(4002, "Invalid lat");
		responseCodeMap.put(4003, "Invalid long");
		responseCodeMap.put(4004, "Invalid empId");
		responseCodeMap.put(4005, "Invalid time format");
		responseCodeMap.put(4006, "Invalid syncTime");
		responseCodeMap.put(4007, "Content type not supported");
		responseCodeMap.put(4008, "no file found");
		responseCodeMap.put(4009, "more than 1 file is not supported");
		responseCodeMap.put(4010, "file upload failed");
		responseCodeMap.put(4011, "invalid/restricted media");
		responseCodeMap.put(4012, "invalid customerId");
		responseCodeMap.put(4013, "invalid messageId");
		responseCodeMap.put(4014, "invalid operatorId");
		responseCodeMap.put(4015, "invalid msisdn");
		responseCodeMap.put(4016, "invalid message");
		responseCodeMap.put(4017, "invalid time, should be in YYYY-MM-DD hh:mm:ss");
		responseCodeMap.put(4018, "Tap continue when you receive the confirmation SMS. Tap re-send* if you haven't received a confirmation SMS for more than 2 minutes.\n\n*SMS charges apply.");
		responseCodeMap.put(4019, "You seem to be already using EFFORT on another device. You can have EFFORT active only on one device at a time.\n\nDo you want to switch to this device?");
		responseCodeMap.put(4020, "You are no longer authorized to use EFFORT. Tap OK to exit.");
		responseCodeMap.put(4021, "");
		responseCodeMap.put(4022, "Max number of active employee reached");
		responseCodeMap.put(4023, "Invalid fileSize");
		responseCodeMap.put(4024, "file corrupted");
		responseCodeMap.put(4025, "Invalid fileId");
		responseCodeMap.put(4026, "Invalid partNo");
		responseCodeMap.put(4027, "Invalid totalPart");
		responseCodeMap.put(4028, "Account inactive");
		responseCodeMap.put(4029, "phone no already in use");
		responseCodeMap.put(4030, "email already in use");
		responseCodeMap.put(4031, "IMEI already in use");
		responseCodeMap.put(4032, "Invalid Email");
		responseCodeMap.put(4033, "form error");
		responseCodeMap.put(4034, "formField error");
		responseCodeMap.put(4035, "Invalid partChecksum");
		responseCodeMap.put(4036, "Invalid finalChecksum");
		responseCodeMap.put(4037, "Authentication Failed");
		responseCodeMap.put(4038, "Subscription expired");
		responseCodeMap.put(4039, "Invalid product code or unauthorised app");
		responseCodeMap.put(4040, "Phone no not found");
		responseCodeMap.put(4041, "Employee is blocked");
		
		responseCodeMap.put(4401, "Unauthorized");
		responseCodeMap.put(4403, "Forbidden");
		responseCodeMap.put(4404, "Not Found");
		responseCodeMap.put(4412, "Precondition Failed");
		responseCodeMap.put(4413, "Form error");
		
		responseCodeMap.put(5001, "Invalid productId");
		responseCodeMap.put(5000, "Error");
		responseCodeMap.put(5118, "Invalid appStoreId");
		responseCodeMap.put(5119, "packageId doesn't match with receiptData details");
		responseCodeMap.put(5120, "receiptData rejected from app store");
		responseCodeMap.put(5121, "Invalid appStoreProductId");
		responseCodeMap.put(21006, "This receipt is valid but the subscription has expired");
		responseCodeMap.put(21000, "The App Store could not read the JSON object you provided");
		responseCodeMap.put(21002, "The data in the receipt-data property was malformed");
		responseCodeMap.put(21003, "The receipt could not be authenticated");
		responseCodeMap.put(21008, "This receipt is a production receipt, but it was sent to the sandbox service for verification");
		responseCodeMap.put(21007, "This receipt is a sandbox receipt, but it was sent to the production service for verification");
		responseCodeMap.put(21005, "The receipt server is not currently available");
		responseCodeMap.put(6001, "The server is under maintenance, please visit again few minutes later");
	
		responseCodeMap.put(9000, "Unknown");
		responseCodeMap.put(7000, "Form Not Found");
		responseCodeMap.put(7001, "Form is Not Accessable");
		responseCodeMap.put(7002, "No Data Found");
		responseCodeMap.put(7003, "File has been deleted, please re-schedule for this form.");
		
		
		//added because to find out fixing missing Db commits 
		responseCodeMap.put(9501, "Fatal Error Occured");
		responseCodeMap.put(7005, "Access Denied");
		
		responseCodeMap.put(9901, "Sorry, another sync request is already in progress. Please try again after a couple of minutes.");
		
		responseCodeMap.put(60061, "UserName and password are mandatory.");
		responseCodeMap.put(60062, "Customer is not a web user.");
		responseCodeMap.put(60063, "Incorrect password.");
		responseCodeMap.put(60064, "Failed getting customer please try again");
		responseCodeMap.put(5020, "Invalid EFFORT Token.");
		responseCodeMap.put(4090, "Authorization Required");
		
		responseCodeMap.put(2019, "Email Sent Successfully for ops Login.");
		
		responseCodeMap.put(9910, "Something went wrong due to high response, Please contact our support team.");
		responseCodeMap.put(9990, "You haven't opted for this feature.If you need to use this feature please contact your manager/admin for access permission.");
		
		responseCodeMap.put(2020, "Invalid Entity Id");
		responseCodeMap.put(2021, "Invalid Work Id");
		responseCodeMap.put(2022, "Invalid DayPlan Approval Status");
		
		responseCodeMap.put(2023, "Invalid companyId");
		responseCodeMap.put(2024, "Invalid workSpecId");
		responseCodeMap.put(2025, "uniqueness configuration not found");
		responseCodeMap.put(2026, "formFields size is empty");
		responseCodeMap.put(2027, "fieldSpecUniqueId not configured in uniqueness configuration");
		responseCodeMap.put(2028, "invalid field data");
		responseCodeMap.put(2029, "invalid clientFormId");
		responseCodeMap.put(2030, "ClientFormId missing");
		responseCodeMap.put(2031, "invalid base64 format");
		responseCodeMap.put(2032, "invalid mimetype");
		responseCodeMap.put(2033, "unable to save media");
		
		responseCodeMap.put(2120, "Invalid companyId");
		responseCodeMap.put(2121, "Invalid workSpecId");
		responseCodeMap.put(2122, "Invalid formSpecId");
		responseCodeMap.put(2123, "formSpec companyId and companyId are not matching");
		responseCodeMap.put(2124, "uniqueness configuration not found");
		responseCodeMap.put(2125, "formFields size is empty");
		responseCodeMap.put(2126, "field not configured in uniqueness configuration");
		responseCodeMap.put(2127, "invalid field data");
		
		
		responseCodeMap.put(7100, "Employee id based login is not enabled, Please contact our support team.");
		responseCodeMap.put(7101, "Your request is rejected as case has been accepted by another user.");
		
		responseCodeMap.put(9902, "Your sync request blocked for the temporary time period. Please contact application support for more details.");
		
		
		responseCodeMap.put(9903, "Phone no can't be greater than 20 digits.");
		responseCodeMap.put(9904, "Login Expired.");
		
		responseCodeMap.put(99909, "");
		
		responseCodeMap.put(99990, "We apologize for the disruption. you are not allowed to login via the mobile phone number and otp, please login using Email and Password.");
		
		responseCodeMap.put(8800, "It appears that you haven't registered yet. Please get in touch with our support team for more information.");
		responseCodeMap.put(8801, "It appears that you have been disabled. Please get in touch with our support team for more information.");
		responseCodeMap.put(8802, "It appears that you dont have mobile app access. Please get in touch with our support team for more information.");
		responseCodeMap.put(8803, "It appears that your subscription is expired. Please get in touch with your admin/manager for more information.");
		
		responseCodeMap.put(9991, "You haven't opted for Effort NXT. If you wish to use Effort NXT, please contact your manager or admin.");
		
	}
	
	public String getDescription(int code){
		return responseCodeMap.get(code);
	}
	
}
