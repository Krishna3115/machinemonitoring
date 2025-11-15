package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDateTime;

public class MachineStatusDTO {

	private String modelNo;
    private String status; // "Active" or "Under Maintenance"
    private LocalDateTime latestMaintenanceDate;
    private LocalDateTime latestComplaintDate;
    private LocalDateTime latestVandalismDate;
    private String Division;
    private String Section;

    public MachineStatusDTO() {
		// TODO Auto-generated constructor stub
	}

	public MachineStatusDTO(String modelNo, String status, LocalDateTime latestMaintenanceDate,
			LocalDateTime latestComplaintDate, LocalDateTime latestVandalismDate, String division, String section) {
		super();
		this.modelNo = modelNo;
		this.status = status;
		this.latestMaintenanceDate = latestMaintenanceDate;
		this.latestComplaintDate = latestComplaintDate;
		this.latestVandalismDate = latestVandalismDate;
		Division = division;
		Section = section;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getLatestMaintenanceDate() {
		return latestMaintenanceDate;
	}

	public void setLatestMaintenanceDate(LocalDateTime latestMaintenanceDate) {
		this.latestMaintenanceDate = latestMaintenanceDate;
	}

	public LocalDateTime getLatestComplaintDate() {
		return latestComplaintDate;
	}

	public void setLatestComplaintDate(LocalDateTime latestComplaintDate) {
		this.latestComplaintDate = latestComplaintDate;
	}

	public LocalDateTime getLatestVandalismDate() {
		return latestVandalismDate;
	}

	public void setLatestVandalismDate(LocalDateTime latestVandalismDate) {
		this.latestVandalismDate = latestVandalismDate;
	}

	public String getDivision() {
		return Division;
	}

	public void setDivision(String division) {
		Division = division;
	}

	public String getSection() {
		return Section;
	}

	public void setSection(String section) {
		Section = section;
	}
    
    
    
    
}
