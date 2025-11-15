package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDateTime;

public class InstallationReportDTO {

	private int srNo;
    private String modelNo;
    private LocalDateTime installationStarted;
    private LocalDateTime installationEnded;
    private String section;
    private String poleNo;
    private String fromKm;
    private String toKm;
    private Integer wheelCount;
    private Integer timeCount;
    private String status;
    
    public InstallationReportDTO() {
		// TODO Auto-generated constructor stub
	}

	public InstallationReportDTO(int srNo, String modelNo, LocalDateTime installationStarted,
			LocalDateTime installationEnded, String section, String poleNo, String fromKm, String toKm,
			Integer wheelCount, Integer timeCount, String status) {
		super();
		this.srNo = srNo;
		this.modelNo = modelNo;
		this.installationStarted = installationStarted;
		this.installationEnded = installationEnded;
		this.section = section;
		this.poleNo = poleNo;
		this.fromKm = fromKm;
		this.toKm = toKm;
		this.wheelCount = wheelCount;
		this.timeCount = timeCount;
		this.status = status;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
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

	public LocalDateTime getInstallationEnded() {
		return installationEnded;
	}

	public void setInstallationEnded(LocalDateTime installationEnded) {
		this.installationEnded = installationEnded;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getPoleNo() {
		return poleNo;
	}

	public void setPoleNo(String poleNo) {
		this.poleNo = poleNo;
	}

	public String getFromKm() {
		return fromKm;
	}

	public void setFromKm(String fromKm) {
		this.fromKm = fromKm;
	}

	public String getToKm() {
		return toKm;
	}

	public void setToKm(String toKm) {
		this.toKm = toKm;
	}

	public Integer getWheelCount() {
		return wheelCount;
	}

	public void setWheelCount(Integer wheelCount) {
		this.wheelCount = wheelCount;
	}

	public Integer getTimeCount() {
		return timeCount;
	}

	public void setTimeCount(Integer timeCount) {
		this.timeCount = timeCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
