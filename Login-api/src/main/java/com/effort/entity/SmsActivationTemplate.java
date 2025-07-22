package com.effort.entity;


public class SmsActivationTemplate {

	private int smsActivationTemplateId;
	private String smsActivationTemplateText;
	private int companyId;
	private String creationDate;
	private String modifiedDate;
	private long createdBy;
	private long modifiedBy;
	
	public int getSmsActivationTemplateId() {
		return smsActivationTemplateId;
	}

	public void setSmsActivationTemplateId(int smsActivationTemplateId) {
		this.smsActivationTemplateId = smsActivationTemplateId;
	}

	public String getSmsActivationTemplateText() {
		return smsActivationTemplateText;
	}

	public void setSmsActivationTemplateText(String smsActivationTemplateText) {
		this.smsActivationTemplateText = smsActivationTemplateText;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
