package com.tblmonitoring.tblmonitor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteInspectionUpdateDTO {

	
	private String inspectionDate; 
    private String action;  // "markDone" or "reinspection"
    private String reinspectionDecidedDate; // e.g. "2025-06-28T10:00"
    private String reinspectionRemark;
	
    
	public SiteInspectionUpdateDTO() {
		// TODO Auto-generated constructor stub
	}

	
	public SiteInspectionUpdateDTO(String inspectionDate, String action, String reinspectionDecidedDate,
			String reinspectionRemark) {
		super();
		this.inspectionDate = inspectionDate;
		this.action = action;
		this.reinspectionDecidedDate = reinspectionDecidedDate;
		this.reinspectionRemark = reinspectionRemark;
	}



	public String getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(String inspectionDate) {
		this.inspectionDate = inspectionDate;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public String getReinspectionDecidedDate() {
		return reinspectionDecidedDate;
	}


	public void setReinspectionDecidedDate(String reinspectionDecidedDate) {
		this.reinspectionDecidedDate = reinspectionDecidedDate;
	}


	public String getReinspectionRemark() {
		return reinspectionRemark;
	}


	public void setReinspectionRemark(String reinspectionRemark) {
		this.reinspectionRemark = reinspectionRemark;
	}
	
	
	
}
