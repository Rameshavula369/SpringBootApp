package com.effort.entity;


public class TerritoriesMapping {
	
	private Long territoriesMappingId;
	private Long territoryId;
	private Long empId;
	private Boolean deleted=false;
	private String modifiedTime;
	private String territoryName;
	private Boolean isMapped=false;
	
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public Long getTerritoriesMappingId() {
		return territoriesMappingId;
	}
	public void setTerritoriesMappingId(Long territoriesMappingId) {
		this.territoriesMappingId = territoriesMappingId;
	}
	public Long getTerritoryId() {
		return territoryId;
	}
	public void setTerritoryId(Long territoryId) {
		this.territoryId = territoryId;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public String getTerritoryName() {
		return territoryName;
	}
	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}
	public Boolean getIsMapped() {
		return isMapped;
	}
	public void setIsMapped(Boolean isMapped) {
		this.isMapped = isMapped;
	}
	
	
	

}
