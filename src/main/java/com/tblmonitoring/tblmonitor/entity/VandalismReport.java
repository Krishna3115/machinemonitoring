package com.tblmonitoring.tblmonitor.entity;


import java.util.Date;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "vandalism_report")
public class VandalismReport {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long inspectionId;
    private String modelNo;
    private String componentName;

    @Column(length = 1000)
    private String issueDescription;

    private String photoUrl;
    private Long reportedByUserId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date reportedAtDateTime;

    @Column(name = "is_damaged")
    private Boolean isDamaged;
  

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "vandalism_report_photos", joinColumns = @JoinColumn(name = "vandalism_report_id"))
    @Column(name = "photo_url")
    private List<String> photoUrls;


    
    public VandalismReport() {
		// TODO Auto-generated constructor stub
	}

    

	public VandalismReport(Long id, Long inspectionId, String modelNo, String componentName, String issueDescription,
			String photoUrl, Long reportedByUserId, Date reportedAtDateTime, Boolean isDamaged, List<String> photoUrls) {
		super();
		this.id = id;
		this.inspectionId = inspectionId;
		this.modelNo = modelNo;
		this.componentName = componentName;
		this.issueDescription = issueDescription;
		this.photoUrl = photoUrl;
		this.reportedByUserId = reportedByUserId;
		this.reportedAtDateTime = reportedAtDateTime;
		this.isDamaged = isDamaged;
		this.photoUrls = photoUrls;
		
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


	public Long getReportedByUserId() {
		return reportedByUserId;
	}


	public void setReportedByUserId(Long reportedByUserId) {
		this.reportedByUserId = reportedByUserId;
	}


	public Date getReportedAtDateTime() {
		return reportedAtDateTime;
	}


	public void setReportedAtDateTime(Date reportedAtDateTime) {
		this.reportedAtDateTime = reportedAtDateTime;
	}

	public Boolean getIsDamaged() {
	    return isDamaged;
	}

	public void setIsDamaged(Boolean isDamaged) {
	    this.isDamaged = isDamaged;
	}



	public List<String> getPhotoUrls() {
		return photoUrls;
	}



	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}

	
	
}
