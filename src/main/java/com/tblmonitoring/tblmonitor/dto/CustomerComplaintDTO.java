package com.tblmonitoring.tblmonitor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerComplaintDTO {

	@JsonProperty("division")
	private String division;
	
	@JsonProperty("section")
    private String section;
  
    @JsonProperty("fromKm")
    private double fromKm;
    
    @JsonProperty("toKm")
    private double toKm;
    
    @JsonProperty("modelNo")
    private String modelNo;
    
    @JsonProperty("machineIssue")
    private String machineIssue;
    
    @JsonProperty("photoUrl")
    private String photoUrl;
    
    public CustomerComplaintDTO() {
		// TODO Auto-generated constructor stub
	}

	public CustomerComplaintDTO(String division, String section, double fromKm, double toKm, String modelNo,
			String machineIssue, String photoUrl) {
		super();
		this.division = division;
		this.section = section;
		this.fromKm = fromKm;
		this.toKm = toKm;
		this.modelNo = modelNo;
		this.machineIssue = machineIssue;
		this.photoUrl = photoUrl;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public double getFromKm() {
		return fromKm;
	}

	public void setFromKm(double fromKm) {
		this.fromKm = fromKm;
	}

	public double getToKm() {
		return toKm;
	}

	public void setToKm(double toKm) {
		this.toKm = toKm;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getMachineIssue() {
		return machineIssue;
	}

	public void setMachineIssue(String machineIssue) {
		this.machineIssue = machineIssue;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	} 
    
    
}
