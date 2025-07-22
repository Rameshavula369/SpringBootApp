package com.effort.entity;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class ITunesPurchase {

	private int itPurchaseId;
	private long empId;
	private String udid;
	private String appStoreProductId;
	private String transactionId;
	private Date purchaseTime;
	private Date expiryTime;
	private String itemId;

	private String purchaseTimeXSD;
	private String expiryTimeXSD;

	private int packageId;
	private String packageTitle;
	private String packageDescription;
	private int noOfDays;
	private boolean subscribed;
	@JsonProperty(access = Access.WRITE_ONLY)
	private int trial;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String receiptData;

	
	private String appStoreResponse;

	private boolean status;
	private String statusMessage;

	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	
	

	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	@JsonIgnore
	public String getAppStoreResponse() {
		return appStoreResponse;
	}

	@JsonIgnore
	public void setAppStoreResponse(String appStoreResponse) {
		this.appStoreResponse = appStoreResponse;
	}

	public int getItPurchaseId() {
		return itPurchaseId;
	}

	public void setItPurchaseId(int itPurchaseId) {
		this.itPurchaseId = itPurchaseId;
	}

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(Date purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public String getReceiptData() {
		return receiptData;
	}

	public void setReceiptData(String receiptData) {
		this.receiptData = receiptData;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getAppStoreProductId() {
		return appStoreProductId;
	}

	public void setAppStoreProductId(String appStoreProductId) {
		this.appStoreProductId = appStoreProductId;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public String getPackageTitle() {
		return packageTitle;
	}

	public void setPackageTitle(String packageTitle) {
		this.packageTitle = packageTitle;
	}

	public String getPackageDescription() {
		return packageDescription;
	}

	public void setPackageDescription(String packageDescription) {
		this.packageDescription = packageDescription;
	}

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	
	public int getTrial() {
		return trial;
	}

	public void setTrial(int trial) {
		this.trial = trial;
	}

	public String getPurchaseTimeXSD() {
		return purchaseTimeXSD;
	}

	public void setPurchaseTimeXSD(String purchaseTimeXSD) {
		this.purchaseTimeXSD = purchaseTimeXSD;
	}

	public String getExpiryTimeXSD() {
		return expiryTimeXSD;
	}

	public void setExpiryTimeXSD(String expiryTimeXSD) {
		this.expiryTimeXSD = expiryTimeXSD;
	}

}
