package com.effort.entity;


public class Subscripton {
	public static final int STATE_FAIELD = -1;
	public static final int STATE_ACTIVE = 0;
	public static final int STATE_EXPIRED = 1;
	public static final int STATE_UNSUBSCRIBED = 2;
	
	private long id;
	private int companyId;
	private int numOfEmployees;
	private double totalStorageAllocated;
	private float subscriptionPrice;
	private float renewalPrice;
	private boolean trial;
	private int state;
	private int gracePreiod;
	private String subscriptionTime;
	private String expiryTime;
	private String lastRenewalTime;
	private String unsubscriptionTime;
	private String modifiedTime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getNumOfEmployees() {
		return numOfEmployees;
	}
	public void setNumOfEmployees(int numOfEmployees) {
		this.numOfEmployees = numOfEmployees;
	}
	public double getTotalStorageAllocated() {
		return totalStorageAllocated;
	}
	public void setTotalStorageAllocated(double totalStorageAllocated) {
		this.totalStorageAllocated = totalStorageAllocated;
	}
	public float getSubscriptionPrice() {
		return subscriptionPrice;
	}
	public void setSubscriptionPrice(float subscriptionPrice) {
		this.subscriptionPrice = subscriptionPrice;
	}
	public float getRenewalPrice() {
		return renewalPrice;
	}
	public void setRenewalPrice(float renewalPrice) {
		this.renewalPrice = renewalPrice;
	}
	public boolean isTrial() {
		return trial;
	}
	public void setTrial(boolean trial) {
		this.trial = trial;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getGracePreiod() {
		return gracePreiod;
	}
	public void setGracePreiod(int gracePreiod) {
		this.gracePreiod = gracePreiod;
	}
	public String getSubscriptionTime() {
		return subscriptionTime;
	}
	public void setSubscriptionTime(String subscriptionTime) {
		this.subscriptionTime = subscriptionTime;
	}
	public String getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getLastRenewalTime() {
		return lastRenewalTime;
	}
	public void setLastRenewalTime(String lastRenewalTime) {
		this.lastRenewalTime = lastRenewalTime;
	}
	public String getUnsubscriptionTime() {
		return unsubscriptionTime;
	}
	public void setUnsubscriptionTime(String unsubscriptionTime) {
		this.unsubscriptionTime = unsubscriptionTime;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
}

