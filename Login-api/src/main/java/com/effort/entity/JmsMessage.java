package com.effort.entity;



import java.io.Serializable;
import java.util.List;


public class JmsMessage implements Serializable,Cloneable{
	private static final long serialVersionUID = 1L;
	
	
	public static final int TYPE_JOB = 1;
	public static final int TYPE_FORM = 2;
	public static final int TYPE_WORK_FLOW = 3;
	public static final int TYPE_ROUTE_PLAN = 4;
	public static final int TYPE_PUNCH_IN_PUNCH_OUT = 5;
	public static final int TYPE_AIRTEL = 500;
	public static final int NO_WORKFLOW_HISTORY_FOUND=-1;
	public static final int NO_ROUTE_HISTORY_FOUND=-1;
 
	public static final int CHANGE_TYPE_ADD = 1;
	public static final int CHANGE_TYPE_MODIFY = 2;
	public static final int CHANGE_TYPE_DELETE = 3;
	public static final int CHANGE_TYPE_ENABLE = 45;
	public static final int CHANGE_TYPE_OTHER = 4;
	public static final int CHANGE_TYPE_COMPLETE = 5;
	public static final int CHANGE_TYPE_REJECTED = 6;
	public static final int CHANGE_TYPE_ALLOCATE = 7;
	public static final int CHANGE_TYPE_EMP_FIELD_MODIFY = 7;
	public static final int CHANGE_TYPE_FEED_BACK_FORM_SUBMISSION = 8;
	public static final int CHANGE_TYPE_ACCEPTED = 20;
	public static final int CHANGE_TYPE_ADD_ACTIONABLE = 21;
	public static final int CHANGE_TYPE_ACCEPT_ACTIONABLE = 22;
	public static final int CHANGE_TYPE_CREATE = 40;
	 public static final int CHANGE_TYPE_REASSGINED = 44;
	public static final int CHANGE_TYPE_BULKUPLOAD = 46;
	public static final int CHANGE_TYPE_UNASSGINED = 55;
	public static final int CHANGE_TYPE_ASSGINED = 80;
	public static final int CHANGE_TYPE_INVITATION_SENT_ON_ACTIONABLE = 60;
	
	public static final int TYPE_WORK_BULKUPLOAD = 18008;
	public static final int TYPE_JOB_DELAYED = 1001;
	public static final int TYPE_JOB_NOT_CLOSED = 1002;
	public static final int TYPE_NO_SYNC = 1003;
	public static final int TYPE_NOT_NEAR_CLIENT_LOCATION = 1004;
	public static final int TYPE_JOB_STAGE_COMPLETION = 1005;
	
	
	public static final int LOCATION_TYPE_PUNCH_IN = 1;
	public static final int LOCATION_TYPE_PUNCH_OUT = 2;
	
	
	
	public static final int COMES_TO_MY_APPROVAL = 2001;
	public static final int MY_SUBMISSION_GETS_REJECTED = 2002;
	public static final int MY_SUBMISSION_GETS_APPROVED  = 2003;
	public static final int A_SUBMISSION_IS_CANCELED = 2004;
	public static final int A_SUBMISSION_IS_APPROVED = 2005;
	public static final int A_SUBMISSION_IS_REJECTED = 2006;
	
	
	public static final int ROUTE_PLAN_ACTIVITY_COMPLETED = 3001;
	public static final int ROUTE_PLAN_FORM_FILLED = 3002;
	public static final int ROUTE_PLAN_COMPLETED = 3003;
	public static final int ROUTE_PLAN_REJECTED = 3004;
	
//	public static final int FRESH_SUBMISSION_COMES_TO_ME_FOR_APPROVAL = 2004;
//	public static final int RESUBMISSION_COMES_FOR_MY_APPROVAL = 2005;
//	public static final int SUBMISSION_IS_MADE_FOR_WHICH_I_AM_ONE_OF_THE_APPROVERS = 2006;
//	public static final int SUBMISSION_IS_CANCELED = 2007;
//	public static final int SUBMISSION_IS_APPROVED = 2008;
	
	public static final int TYPE_TERRITORY_NOTIFICATION = 6;
	
	public static final int TYPE_WORK_NOTIFICATION = 7;
	public static final int TYPE_WORK = 8;
	public static final int TYPE_WORK_STATUS_HISTORY = 9;
	
	public static final int TYPE_WORK__ABOUT_TO_DELAY = 7001;
	public static final int TYPE_WORK_ABOUT_TO_START = 7003;
	public static final int TYPE_WORK_EMPLOYEE_NOT_NEAR_CLIENT_LOCATION = 7002;
	public static final int TYPE_WORK_GOT_DELAYED = 7004;
	//Deva,2017-01-26,Janak Health care,sending notification based on dates selected in entity
	public static final int CUSTOM_NOTIFICATION = 7005;
	
	public static final int TYPE_WORK_ACTION_GOT_DELAYED = 7006;
	
	public static final int TYPE_WORK_ATTACHMENT = 10;
	public static final int LOCATION_GPS_STATUS = 11;
	
	public static final int TYPE_LEAVE = 12;
	public static final int LEAVE_APPLIED = 12001;
	public static final int LEAVE_CANCELLED = 12002;
	public static final int LEAVE_APPROVED = 12003;
	public static final int LEAVE_REJECTED = 12004;
	public static final int TYPE_ACTIVITY_LOCATION = 12005;
	
	public static final int TYPE_ASSIGNED_ROUTE_PLAN = 13;
	public static final int TYPE_DAY_PLAN = 14;
	public static final int TYPE_WORK_ACTION_NOTIFICATION = 15;
	
	public static final int TYPE_WORK_DATA_SYNC =16;
	public static final int TYPE_TRACK = 17;
	
	public static final int TYPE_PRINT_LOG = 18;
	public static final int TYPE_WORK_FEEDBACK_FORM_SUBMISSTION = 19;
	
	public static final int TYPE_ON_SITE_GO_WORK_REASSIGNMENT = 90009;
	
	public static final int TYPE_SIMPLIFIED_WORK_DATA_SYNC =16001;
	public static final int CHANGE_TYPE_WORK_ACTION_SUBMISSION =16002;
	public static final int CHANGE_TYPE_WORK_ACTION_UPDATION =16003;
	public static final int TYPE_WORK_ACTION_MAPPING_NOTIFICATION = 23;
	public static final int CHANGE_TYPE_MANUAL = 24;
	public static final int CHANGE_TYPE_FLOW_EDITED = 25;
	public static final int CHECK_FOR_INVITATION_ACCEPT = 26;
	public static final int CHANGE_TYPE_INVITATION_ACCEPT = 27;
	
	public static final int CHANGE_TYPE_WORK_ACTION_SUBMISSION_WORK_COMPLETED =16005;
	
	public static final int TYPE_WORK_PUSH_NOTIFICATION =17001;
	public static final int CHANGE_TYPE_WORK_PUSH_NOTIFICATION_TRIGER =17002;
	public static final int TYPE_EMPLOYEE_NOT_SIGNED_IN_NOTIFICATION = 7007;
	public static final int TYPE_EMPLOYEE_NOT_SIGNED_OUT_NOTIFICATION = 7008;
	
	public static final int TYPE_WORK_NOT_CREATED = 7009;
	
	public static final int TYPE_FORM_SPEC = 18001;
	public static final int TYPE_WORK_SPEC = 18002;
	public static final int TYPE_ACTION_ASSIGNMENT = 18003;
	public static final int CHANGE_TYPE_ASSIGN_EMPLOYEE_GROUP_FOR_WORKSPEC = 18004;
	public static final int CHANGE_TYPE_ASSIGN_EMPLOYEE_GROUP_FOR_WORKACTIONSPEC = 18005;
	public static final int APPCONFIGURATION_CHANGES = 18006;
	public static final int CHANGE_TYPE_FORMSPEC_CUSTOM_ENTITYSPEC_VISIBILITY = 18007;

	public static final int TYPE_WORK_ACTION_INVITAION_STATUS = 99023;
	
	public static final int TYPE_ENTITY_ITEM = 41;

	public static final int CHANGE_TYPE_ACTIONABLE_WORKS = 42;

	public static final int TYPE_WORKS_ADD_MODIFY = 43;
	
	public static final int TYPE_WORKS_SPECS_MODIFY = 44;
	
	public static final int TYPE_LIST_UPDATE_FORM = 45;
	
	public static final int TYPE_FACE_RECOGNITION = 46;
	
	public static final int TYPE_FORM_MEDIA_COMMITED = 47;
	
	public static final int TYPE_DAY_PLAN_CUSTOMER_NOTIFY = 48;
	
	public static final int CHANGE_TYPE_DAY_PLAN_CUST_VISIT_DEFINED_ORDER = 49;
	
	public static final int TYPE_DAY_PLAN_CUSTOM_ENTITY_NOTIFY = 50;
	
	
	public static final int TYPE_ROUTE_PLAN_CUSTOMER_NOTIFY = 51;
	public static final int CHANGE_TYPE_ROUTE_PLAN_CUST_VISIT_DEFINED_ORDER = 52;
	
	public static final int CHANGE_TYPE_SIMPLIFIED_WORK_DATA_SYNC_FOR_RESEND = 54;
	public static final int TYPE_LEAVE_REMINDER = 7011;

	
	public static final int TYPE_EXTERNAL_INTEGRATION = 16006;
	public static final int TYPE_CUSTOM_ENTITY_UPDATE_FORM = 402;
	
	public static final int TYPE_WORK_INVITATION = 16;
	public static final int TYPE_WORK_INVITATION_ACCEPT = 1;
	public static final int TYPE_WORK_INVITATION_REJECTED = 2;
	public static final int TYPE_WORK_INVITATION_SENT = 3;
	
	public static final int TYPE_NO_ACTIVITY_ESCALATION = 7012;
	
	public static final int TYPE_TIME_BASED_FORM_SUBMISSION = 7010;

	public static final int TYPE_TIME_BASED_NOTIFICATION = 1;
	public static final int TYPE_EVENT_BASED_NOTIFICATION = 2;
	
	public static final int TYPE_LATE_SIGNIN_NOTIFICATION = 20;
	public static final int TYPE_EARLY_SIGNOUT_NOTIFICATION = 21;
	public static final int TYPE_EMPLOYEE_BLOCK_NOTIFICATION = 63;
	public static final int TYPE_EMPLOYEE_UNBLOCK_NOTIFICATION = 64;
	public static final int TYPE_EMPLOYEE_NOTIFICATION = 24;
	public static final int CHANGE_TYPE_EMPLOYEE_DISABLE = 30;
	public static final int CHANGE_TYPE_EMPLOYEE_WORK_REASSIGNMENT = 31;
	public static final int CHANGE_TYPE_EMPLOYEE_TERRITORY_MAPPING = 32;
	public static final int CHANGE_TYPE_EMPLOYEE_LIST_MAPPING = 33;
	public static final int CHANGE_TYPE_EMPLOYEE_RESEND_PASSWORD = 34;
	public static final int CHANGE_TYPE_EMPLOYEE_REQUEST_DUBUG_INFO = 35;
	public static final int CHANGE_TYPE_EMPLOYEE_CANCEL_DUBUG_INFO = 36;
	public static final int CHANGE_TYPE_CHAT_MESSAGES = 37;
	public static final int CHANGE_TYPE_EMPLOYEE_CUSTOMER_MAPPING = 38;
	public static final int CHANGE_TYPE_EMPLOYEE_CUSTOMER_COPY_EMPLOYEE = 39;
	public static final int CHANGE_TYPE_EMPLOYEE_CUSTOMER_MOVE_EMPLOYEE = 40;
	public static final int CHANGE_TYPE_EMPLOYEE_CUSTOMER_MAPPING_REMOVE = 41;
	public static final int CHANGE_TYPE_EMPLOYEE_TERRITORY_MAPPING_REMOVE = 42;
	public static final int CHANGE_TYPE_EMPLOYEE_LIST_MAPPING_REMOVE = 43;
	public static final int CHANGE_TYPE_EMPLOYEE_LIST_MAPPING_REMOVE_AND_ADDED = 54;
	public static final int CHANGE_TYPE_EMPLOYEE_ROUTE_MAPPING_REMOVE = 53;
	public static final int CHANGE_TYPE_EMPLOYEE_ROUTE_MAPPING_ADD = 52;
	public static final int CHANGE_TYPE_EMPLOYEE_CUSTOMER_MAPPING_REMOVE_AND_ADDED = 56;
	public static final int CHANGE_TYPE_EMPLOYEE_ROUTE_MAPPING_ADD_AND_REMOVED = 57;
	public static final int CHANGE_TYPE_EMPLOYEE_TERRITORY_MAPPING_ADD_AND_REMOVED = 58;

	public static final int CHANGE_TYPE_CUSTOMER_DELETED = 62;
	public static final int TYPE_CUSTOMER_NOTIFICATION = 25;
	public static final int CHANGE_TYPE_CUSTOMER_LIST_MAPPING_REMOVE = 51;
	public static final int CHANGE_TYPE_CUSTOMER_LIST_MAPPING_ADD = 45;

	public static final int TYPE_KNOWLEDGE_BASED = 28;
	public static final int CHANGE_TYPE_KNOWLEDGE_BASED_FOLDER_ADD = 66;
	public static final int CHANGE_TYPE_KNOWLEDGE_BASED_FOLDER_MODIFY = 47;
	public static final int CHANGE_TYPE_KNOWLEDGE_BASED_FILE_ADD = 48;
	public static final int CHANGE_TYPE_KNOWLEDGE_BASED_FILE_MODIFY = 49;
	public static final int CHANGE_TYPE_KNOWLEDGE_BASED_DELETED = 50;
	
	public static final int CHANGE_TYPE_EMPLOYEE_ENTITY_MAPPING_ADD = 59;
	public static final int CHANGE_TYPE_EMPLOYEE_ENTITY_MAPPING_REMOVED = 65;
	public static final int CHANGE_TYPE_EMPLOYEE_ENTITY_MAPPING_ADD_AND_REMOVED = 61;
	public static final int LEAVE_MODIFIED = 12006;
	public static final int TYPE_ACTION_REQUIRED = 29;
	public static final int CHANGE_TYPE_ACTION_REQUIRED = 67;
	public static final int TYPE_CUSTOMER_CHECKIN_CHECKOUT = 68;
	public static final int TYPE_CUSTOM_ENTITY_CHECKIN_CHECKOUT = 69;
	public static final int TYPE_WORK_FLOW_BULK_ACTION = 4001;
	
	public static final int TYPE_TIME_BASED_CUSTOM_ENTITY = 7014;
	public static final int TYPE_CUSTOM_ENTITY = 71;
	public static final int TYPE_CUSTOM_ENTITY_SUBMISSION = 70;
	
	public static final int TYPE_JOINT_VISIT = 72;
	public static final int CHANGE_TYPE_REQUEST = 73;
	
	public static final int TYPE_JOINT_VISIT_ACK = 74;
	public static final int CHANGE_TYPE_ACK = 75;


	private long entityId;
	private int type;
	private long id;
	private long companyId;
	private long empId;
	private int changeType;
	private String time;
	private Serializable extra;
	private Boolean byClient = false;
	private Integer deliveryCount;
	private String criteriaTime;
	private String restrictWorkDataPostConfigId;


	private String empIds;



	private String message;


	private String itemIds;
	private int count;
	private int removedCount;
	private String mappedEmpIds;
	private long workActionSpecId;
	private long workId;
	private boolean reminderNotification;

	private String formIds;
	private String workFlowFormStatusIds;
	private boolean ignoreFirebasePushNotification;
	
	private boolean ignoreFirebasePushNotificationForNotBulkApproval;
	private String messageSubject;
	private String messageContent;
	
	public JmsMessage(){}
	
//	public JmsMessage(int type, long id, int changeType) {
//		this.type = type;
//		this.id = id;
//		this.changeType = changeType;
//	}
//
//	public JmsMessage(int type, long id, int changeType, String time) {
//		this.type = type;
//		this.id = id;
//		this.changeType = changeType;
//		this.time = time;
//	}
	
	public JmsMessage(int type, long id, long companyId, long empId, int changeType, Boolean byClient, Integer deliveryCount) {
		this(type, id, companyId, empId, changeType, null,0, null, byClient, deliveryCount);
	}
	
	public JmsMessage(int type, long id, long companyId, long empId, int changeType, String time, Boolean byClient, Integer deliveryCount) {
		this(type, id, companyId, empId, changeType, time, 0,null, byClient, deliveryCount);
	}
	
	public JmsMessage(int type, long id, long companyId, long empId, int changeType, Serializable extra, Boolean byClient, Integer deliveryCount) {
		this(type, id, companyId, empId, changeType, null, 0,extra, byClient, deliveryCount);
	}

	public JmsMessage(int type, long id, long companyId, long empId, int changeType, String time,long entityId, Serializable extra, Boolean byClient, Integer deliveryCount) {
		this.type = type;
		this.id = id;
		this.companyId = companyId;
		this.empId = empId;
		this.changeType = changeType;
		this.time = time;
		this.entityId=entityId;
		this.extra = extra;
		this.byClient = byClient;
		this.deliveryCount = deliveryCount;
	}

	public JmsMessage(int type, long id, long companyId, long empId, int changeType, String time,long entityId, Serializable extra, Boolean byClient, Integer deliveryCount,String restrictWorkDataPostConfigId) {
		this.type = type;
		this.id = id;
		this.companyId = companyId;
		this.empId = empId;
		this.changeType = changeType;
		this.time = time;
		this.entityId=entityId;
		this.extra = extra;
		this.byClient = byClient;
		this.deliveryCount = deliveryCount;
		this.restrictWorkDataPostConfigId = restrictWorkDataPostConfigId;
	}

	public JmsMessage(int type, long id, long companyId, long empId, int changeType, String time,long entityId, Serializable extra, Boolean byClient, Integer deliveryCount,String restrictWorkDataPostConfigId,int count) {
		this.type = type;
		this.id = id;
		this.companyId = companyId;
		this.empId = empId;
		this.changeType = changeType;
		this.time = time;
		this.entityId=entityId;
		this.extra = extra;
		this.byClient = byClient;
		this.deliveryCount = deliveryCount;
		this.count = count;
	}
	
	public JmsMessage(int type, long id, long companyId, long empId, int changeType, String time,long entityId, Serializable extra, Boolean byClient, 
			Integer deliveryCount,String restrictWorkDataPostConfigId,int count,int removedCount) {
		this.type = type;
		this.id = id;
		this.companyId = companyId;
		this.empId = empId;
		this.changeType = changeType;
		this.time = time;
		this.entityId=entityId;
		this.extra = extra;
		this.byClient = byClient;
		this.deliveryCount = deliveryCount;
		this.count = count;
		this.removedCount = removedCount;
	}
	
	public JmsMessage(int type, long id, long companyId, long empId, int changeType, String time,long entityId, Serializable extra, Boolean byClient, 
			Integer deliveryCount,String restrictWorkDataPostConfigId,int count,int removedCount,String mappedEmpIds) {
		this.type = type;
		this.id = id;
		this.companyId = companyId;
		this.empId = empId;
		this.changeType = changeType;
		this.time = time;
		this.entityId=entityId;
		this.extra = extra;
		this.byClient = byClient;
		this.deliveryCount = deliveryCount;
		this.count = count;
		this.removedCount = removedCount;
		this.mappedEmpIds = mappedEmpIds;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public int getChangeType() {
		return changeType;
	}
	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Serializable getExtra() {
		return extra;
	}
	public void setExtra(Serializable extra) {
		this.extra = extra;
	}
	public long getEntityId() {
		return entityId;
	}

	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	@Override
	public String toString() {
		return "[entityId=" + entityId + ", type=" + type + ", id=" + id + ", companyId=" + companyId + ", empId=" + empId + ", changeType=" + changeType + ", time=" + time + ", byClient=" + byClient + ", extra=" + extra + ", deliveryCount=" + deliveryCount + "]";
	}

	public Boolean getByClient() {
		return byClient;
	}

	public void setByClient(Boolean byClient) {
		this.byClient = byClient;
	}

	public Integer getDeliveryCount() {
		return deliveryCount;
	}

	public void setDeliveryCount(Integer deliveryCount) {
		this.deliveryCount = deliveryCount;
	}

	public String getCriteriaTime() {
		return criteriaTime;
	}

	public void setCriteriaTime(String criteriaTime) {
		this.criteriaTime = criteriaTime;
	}

	public String getRestrictWorkDataPostConfigId() {
		return restrictWorkDataPostConfigId;
	}

	public void setRestrictWorkDataPostConfigId(String restrictWorkDataPostConfigId) {
		this.restrictWorkDataPostConfigId = restrictWorkDataPostConfigId;
	}




	public String getEmpIds() {
		return empIds;
	}

	public void setEmpIds(String empIds) {
		this.empIds = empIds;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	
	
	@Override
	public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }



	

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getRemovedCount() {
		return removedCount;
	}

	public void setRemovedCount(int removedCount) {
		this.removedCount = removedCount;
	}

	public String getMappedEmpIds() {
		return mappedEmpIds;
	}

	public void setMappedEmpIds(String mappedEmpIds) {
		this.mappedEmpIds = mappedEmpIds;
	}

	public long getWorkActionSpecId() {
		return workActionSpecId;
	}

	public void setWorkActionSpecId(long workActionSpecId) {
		this.workActionSpecId = workActionSpecId;
	}

	public long getWorkId() {
		return workId;
	}

	public void setWorkId(long workId) {
		this.workId = workId;
	}

	public boolean isReminderNotification() {
		return reminderNotification;
	}

	public void setReminderNotification(boolean reminderNotification) {
		this.reminderNotification = reminderNotification;
	}


	public String getFormIds() {
		return formIds;
	}

	public void setFormIds(String formIds) {
		this.formIds = formIds;
	}

	public boolean isIgnoreFirebasePushNotification() {
		return ignoreFirebasePushNotification;
	}

	public void setIgnoreFirebasePushNotification(boolean ignoreFirebasePushNotification) {
		this.ignoreFirebasePushNotification = ignoreFirebasePushNotification;
	}

	public String getWorkFlowFormStatusIds() {
		return workFlowFormStatusIds;
	}

	public void setWorkFlowFormStatusIds(String workFlowFormStatusIds) {
		this.workFlowFormStatusIds = workFlowFormStatusIds;
	}




	public String getMessageSubject() {
		return messageSubject;
	}

	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public boolean isIgnoreFirebasePushNotificationForNotBulkApproval() {
		return ignoreFirebasePushNotificationForNotBulkApproval;
	}

	public void setIgnoreFirebasePushNotificationForNotBulkApproval(
			boolean ignoreFirebasePushNotificationForNotBulkApproval) {
		this.ignoreFirebasePushNotificationForNotBulkApproval = ignoreFirebasePushNotificationForNotBulkApproval;
	}
	
	

//	@Override
//	public String toString() {
//		return "[type=" + type + ", id=" + id + ", companyId=" + companyId + ", empId=" + empId + ", changeType=" + changeType + ", time=" + time + ", extra=" + extra + "]";
//	}
	
	
	
}
