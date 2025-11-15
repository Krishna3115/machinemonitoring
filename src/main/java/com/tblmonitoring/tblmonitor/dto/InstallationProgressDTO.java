package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDateTime;

public class InstallationProgressDTO {

	private String modelNo;
    private LocalDateTime installationStarted;
    private String division;
    private String section;
    
    public InstallationProgressDTO() {
		// TODO Auto-generated constructor stub
	}

	public InstallationProgressDTO(String modelNo, LocalDateTime installationStarted, String division, String section) {
		super();
		this.modelNo = modelNo;
		this.installationStarted = installationStarted;
		this.division = division;
		this.section = section;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public LocalDateTime getInstallationStarted() {
		return installationStarted;
	}

	public void setInstallationStarted(LocalDateTime installationStarted) {
		this.installationStarted = installationStarted;
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
    
    
}
