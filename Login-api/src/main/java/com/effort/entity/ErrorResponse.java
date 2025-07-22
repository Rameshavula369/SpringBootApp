package com.effort.entity;


import com.effort.util.Api;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class ErrorResponse {
	private int code;
	private String description;
	private String smsc;
	
	private String key;
	private String id;
	private String clientId;
	private String param;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getSmsc() {
		return smsc;
	}
	public void setSmsc(String smsc) {
		this.smsc = smsc;
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public void appendDescription(String desc){
		if(Api.isEmptyString(description)){
			description = desc;
		} else {
			if(!Api.isEmptyString(desc)){
				description += " - " + desc;
			}
		}
	}
}
