package com.tblmonitoring.tblmonitor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstallationFormDTO {

	@JsonProperty("modelNo")
	private String modelNo;
    private String section;
    private String curveNo;
    private String poleNo;
    private String fromKm;
    private String toKm;
    private String rhLhRadius;
    private String srDen;
    private String lineSection;
    private String pwi;
    private String machineStatus;
    private String greaseLevel;
    private String greaseLevelPhotoUrl;
    private Integer wheelCount;
    private Integer timeCount;
    private String remarks;
    private Double greaseLevelKg;

    
    public InstallationFormDTO() {
		// TODO Auto-generated constructor stub
	}

	public InstallationFormDTO(String modelNo, String section, String curveNo, String poleNo, String fromKm,
			String toKm, String rhLhRadius, String srDen, String lineSection, String pwi, String machineStatus,
			String greaseLevel, String greaseLevelPhotoUrl, Integer wheelCount, Integer timeCount, String remarks,
			Double greaseLevelKg) {
		super();
		this.modelNo = modelNo;
		this.section = section;
		this.curveNo = curveNo;
		this.poleNo = poleNo;
		this.fromKm = fromKm;
		this.toKm = toKm;
		this.rhLhRadius = rhLhRadius;
		this.srDen = srDen;
		this.lineSection = lineSection;
		this.pwi = pwi;
		this.machineStatus = machineStatus;
		this.greaseLevel = greaseLevel;
		this.greaseLevelPhotoUrl = greaseLevelPhotoUrl;
		this.wheelCount = wheelCount;
		this.timeCount = timeCount;
		this.remarks = remarks;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getCurveNo() {
		return curveNo;
	}

	public void setCurveNo(String curveNo) {
		this.curveNo = curveNo;
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

	public String getRhLhRadius() {
		return rhLhRadius;
	}

	public void setRhLhRadius(String rhLhRadius) {
		this.rhLhRadius = rhLhRadius;
	}

	public String getSrDen() {
		return srDen;
	}

	public void setSrDen(String srDen) {
		this.srDen = srDen;
	}

	public String getLineSection() {
		return lineSection;
	}

	public void setLineSection(String lineSection) {
		this.lineSection = lineSection;
	}

	public String getPwi() {
		return pwi;
	}

	public void setPwi(String pwi) {
		this.pwi = pwi;
	}

	public String getMachineStatus() {
		return machineStatus;
	}

	public void setMachineStatus(String machineStatus) {
		this.machineStatus = machineStatus;
	}

	public String getGreaseLevel() {
		return greaseLevel;
	}

	public void setGreaseLevel(String greaseLevel) {
		this.greaseLevel = greaseLevel;
	}

	public String getGreaseLevelPhotoUrl() {
		return greaseLevelPhotoUrl;
	}

	public void setGreaseLevelPhotoUrl(String greaseLevelPhotoUrl) {
		this.greaseLevelPhotoUrl = greaseLevelPhotoUrl;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    
	public Double getGreaseLevelKg() {
		return greaseLevelKg;
	}

	public void setGreaseLevelKg(Double greaseLevelKg) {
		this.greaseLevelKg = greaseLevelKg;
	}
	
	

    
}
