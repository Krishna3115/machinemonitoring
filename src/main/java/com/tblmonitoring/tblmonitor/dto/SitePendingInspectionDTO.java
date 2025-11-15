package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;

public class SitePendingInspectionDTO {

	private Long id;
    private String modelNo;
    private String machineName;
    private String division;
    private String section;
    private LocalDateTime dispatchDate;
    private LocalDateTime deliveredDate;
    private LocalDateTime installationEnded;

    // New fields for inspection status & reinspection details
    private String inspectionStatus; // e.g. "Pending", "Reinspection", "Done"
    private LocalDateTime inspectionDate; // Date when inspection was completed
    private LocalDateTime reinspectionDecidedDate; // Date when reinspection was decided
    

    @Column(length = 500)
    private String reinspectionRemark; // Remark for reinspection
    
    
    public SitePendingInspectionDTO() {
		// TODO Auto-generated constructor stub
	}


	public SitePendingInspectionDTO(Long id, String modelNo, String machineName, String division, String section,
			LocalDateTime dispatchDate, LocalDateTime deliveredDate, LocalDateTime installationEnded,
			String inspectionStatus, LocalDateTime inspectionDate, LocalDateTime reinspectionDecidedDate,
            String reinspectionRemark) {
		
		super();
		this.id = id;
		this.modelNo = modelNo;
		this.machineName = machineName;
		this.division = division;
		this.section = section;
		this.dispatchDate = dispatchDate;
		this.deliveredDate = deliveredDate;
		this.installationEnded = installationEnded;
		this.inspectionStatus = inspectionStatus;
        this.inspectionDate = inspectionDate;
        this.reinspectionDecidedDate = reinspectionDecidedDate;
        this.reinspectionRemark = reinspectionRemark;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getModelNo() {
		return modelNo;
	}


	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}


	public String getMachineName() {
		return machineName;
	}


	public void setMachineName(String machineName) {
		this.machineName = machineName;
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


	public LocalDateTime getDispatchDate() {
		return dispatchDate;
	}


	public void setDispatchDate(LocalDateTime dispatchDate) {
		this.dispatchDate = dispatchDate;
	}


	public LocalDateTime getDeliveredDate() {
		return deliveredDate;
	}


	public void setDeliveredDate(LocalDateTime deliveredDate) {
		this.deliveredDate = deliveredDate;
	}


	public LocalDateTime getInstallationEnded() {
		return installationEnded;
	}


	public void setInstallationEnded(LocalDateTime installationEnded) {
		this.installationEnded = installationEnded;
	}


	public String getInspectionStatus() {
		return inspectionStatus;
	}


	public void setInspectionStatus(String inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}


	public LocalDateTime getInspectionDate() {
		return inspectionDate;
	}


	public void setInspectionDate(LocalDateTime inspectionDate) {
		this.inspectionDate = inspectionDate;
	}


	public LocalDateTime getReinspectionDecidedDate() {
		return reinspectionDecidedDate;
	}


	public void setReinspectionDecidedDate(LocalDateTime reinspectionDecidedDate) {
		this.reinspectionDecidedDate = reinspectionDecidedDate;
	}


	public String getReinspectionRemark() {
		return reinspectionRemark;
	}


	public void setReinspectionRemark(String reinspectionRemark) {
		this.reinspectionRemark = reinspectionRemark;
	}
    
	
}
