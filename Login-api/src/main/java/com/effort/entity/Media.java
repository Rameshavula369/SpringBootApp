package com.effort.entity;



public class Media {
	private long id;
	private long companyId;
	private long empId;
	private String mimeType;
	private String localPath;
	private String fileName;
	private String createdTime;
	private String modifiedTime;
	private Integer config;
	private String externalMediaId;
	private String pdfThumbNailMediaId;
	private String fieldValuePdfDisplay;
	private String documentMediaIcon;
	
	// media exists for ever IN FILE SYSTEM are Company logos,Employee profile pics,App icons,Theme images etc
	public static final int CONFIG_FOR_MEDIA_EXISTS_FOR_EVER = 1;
	public static final int CONFIG_FOR_MEDIA_EXISTS_FOR_SPECIFIC_DURATION = 0;
	
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
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public Integer getConfig() {
		return config;
	}
	public void setConfig(Integer config) {
		this.config = config;
	}
	public String getExternalMediaId() {
		return externalMediaId;
	}
	public void setExternalMediaId(String externalMediaId) {
		this.externalMediaId = externalMediaId;
	}
	public String getPdfThumbNailMediaId() {
		return pdfThumbNailMediaId;
	}
	public void setPdfThumbNailMediaId(String pdfThumbNailMediaId) {
		this.pdfThumbNailMediaId = pdfThumbNailMediaId;
	}
	public String getFieldValuePdfDisplay() {
		return fieldValuePdfDisplay;
	}
	public void setFieldValuePdfDisplay(String fieldValuePdfDisplay) {
		this.fieldValuePdfDisplay = fieldValuePdfDisplay;
	}
	public String getDocumentMediaIcon() {
		return documentMediaIcon;
	}
	public void setDocumentMediaIcon(String documentMediaIcon) {
		this.documentMediaIcon = documentMediaIcon;
	}
	
	
}
