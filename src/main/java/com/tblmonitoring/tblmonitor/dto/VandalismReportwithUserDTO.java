package com.tblmonitoring.tblmonitor.dto;

import java.sql.Date;
import java.util.List;

import com.tblmonitoring.tblmonitor.entity.VandalismReport;

public class VandalismReportwithUserDTO {

	private Long id;
    private Long inspectionId;
    private String modelNo;
    private String componentName;
    private String issueDescription;
    private String photoUrl;
    private String reportedByName;
    private Date reportedAtDateTime;
    private List<String> photoUrls; // NEW

    private String claimStatus;
    private Long claimId;
    
    
	public VandalismReportwithUserDTO(Long id, Long inspectionId, String modelNo, String componentName,
			String issueDescription, String photoUrl, String reportedByName, Date reportedAtDateTime, String claimStatus,
			Long claimId) {
		super();
		this.id = id;
		this.inspectionId = inspectionId;
		this.modelNo = modelNo;
		this.componentName = componentName;
		this.issueDescription = issueDescription;
		this.photoUrl = photoUrl;
		
		this.reportedByName = reportedByName;
		this.reportedAtDateTime = reportedAtDateTime;
		this.claimStatus = claimStatus;
		this.claimId = claimId;
	}
	
	
	public VandalismReportwithUserDTO(VandalismReport report, String reportedByName, String claimStatus, Long claimId) {
	    this.id = report.getId();
	    this.inspectionId = report.getInspectionId();
	    this.modelNo = report.getModelNo();
	    this.componentName = report.getComponentName();
	    this.issueDescription = report.getIssueDescription();
	    this.photoUrl = report.getPhotoUrl();
	    this.photoUrls = report.getPhotoUrls(); 
	    this.reportedByName = reportedByName;
	    this.reportedAtDateTime = new java.sql.Date(report.getReportedAtDateTime().getTime());
	    this.claimStatus = claimStatus;
	    this.claimId = claimId;
	}



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getInspectionId() {
		return inspectionId;
	}


	public void setInspectionId(Long inspectionId) {
		this.inspectionId = inspectionId;
	}


	public String getModelNo() {
		return modelNo;
	}


	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}


	public String getComponentName() {
		return componentName;
	}


	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}


	public String getIssueDescription() {
		return issueDescription;
	}


	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}


	public String getPhotoUrl() {
		return photoUrl;
	}


	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}


	public String getReportedByName() {
		return reportedByName;
	}


	public void setReportedByName(String reportedByName) {
		this.reportedByName = reportedByName;
	}


	public Date getReportedAtDateTime() {
		return reportedAtDateTime;
	}


	public void setReportedAtDateTime(Date reportedAtDateTime) {
		this.reportedAtDateTime = reportedAtDateTime;
	}
    
    
	public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }


	public List<String> getPhotoUrls() {
		return photoUrls;
	}


	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}
    
    
	
}
