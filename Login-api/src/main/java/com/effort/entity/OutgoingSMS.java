package com.effort.entity;




public class OutgoingSMS {
	public static final int FREE = 1;//Free Of Charge
	public static final int PAID = 2;//Charges Applicable
	
	public static final int YET_TO_PROCESS = 1;
	public static final int DELIVERED = 2;
	public static final int FAILED = -1;
	
	private long id;
	private String msisdn;
	private Integer companyId;
	private String message;
	private String createTime;
	private int status;
	private int smsType;
	private boolean sendSmsViaWhatsaap;
	private String placeholdersJson;
	private Integer sendSmsType;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public int getSmsType() {
		return smsType;
	}
	public void setSmsType(int smsType) {
		this.smsType = smsType;
	}
	public boolean isSendSmsViaWhatsaap() {
		return sendSmsViaWhatsaap;
	}
	public void setSendSmsViaWhatsaap(boolean sendSmsViaWhatsaap) {
		this.sendSmsViaWhatsaap = sendSmsViaWhatsaap;
	}
	public String getPlaceholdersJson() {
		return placeholdersJson;
	}
	public void setPlaceholdersJson(String placeholdersJson) {
		this.placeholdersJson = placeholdersJson;
	}
	public Integer getSendSmsType() {
		return sendSmsType;
	}
	public void setSendSmsType(Integer sendSmsType) {
		this.sendSmsType = sendSmsType;
	}
	@Override
	public String toString() {
		return "OutgoingSMS [id=" + id + ", msisdn=" + msisdn + ", companyId=" + companyId + ", message=" + message
				+ ", createTime=" + createTime + ", status=" + status + ", smsType=" + smsType + ", sendSmsViaWhatsaap="
				+ sendSmsViaWhatsaap + ", sendSmsViaWhatsaap=" + sendSmsViaWhatsaap + "]";
	}
	
	
	
}
