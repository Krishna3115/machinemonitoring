package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MasterReportDTO {

	 private String modelNo;
	    private String machineName;
	    private String location;
	    private String status;
	    private String dispatchDate;
	    private LocalDateTime deliveredDate;
	    private Boolean technicianAssigned;

	    // Purchase Order details
	    private String poNumber;
	    private LocalDate poDate;
	 //   private LocalDate finalDispatchDate;
	    private Integer poQuantity;
	    private Integer poWarrantyMonths;
	    private Integer maintenanceDays;
	    private String erpoa;
	    private Double perDayFine;

	    // Production Planning details
	    private Integer plannedQuantity;
	    private LocalDateTime productionStartDate;
	    private LocalDateTime productionEndDate;

	    // Installation Record details
	    private LocalDateTime installationStarted;
	    private LocalDateTime installationEnded;
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
	    private Long installationTechnicianId;
	    
    
    public MasterReportDTO() {
		// TODO Auto-generated constructor stub
	}


	public MasterReportDTO(String modelNo, String machineName, String location, String status,
			String dispatchDate, LocalDateTime deliveredDate, Boolean technicianAssigned, String poNumber,
			LocalDate poDate, LocalDate finalDispatchDate, Integer poQuantity, Integer poWarrantyMonths,
			Integer maintenanceDays, String erpoa, Double perDayFine, Integer plannedQuantity,
			LocalDateTime productionStartDate, LocalDateTime productionEndDate, LocalDateTime installationStarted,
			LocalDateTime installationEnded, String section, String curveNo, String poleNo, String fromKm, String toKm,
			String rhLhRadius, String srDen, String lineSection, String pwi, String machineStatus, String greaseLevel,
			String greaseLevelPhotoUrl, Integer wheelCount, Integer timeCount, String remarks, Double greaseLevelKg,
			Long installationTechnicianId) {
		super();
		this.modelNo = modelNo;
		this.machineName = machineName;
		this.location = location;
		this.status = status;
		this.dispatchDate = dispatchDate;
		this.deliveredDate = deliveredDate;
		this.technicianAssigned = technicianAssigned;
		this.poNumber = poNumber;
		this.poDate = poDate;
		//this.finalDispatchDate = finalDispatchDate;
		this.poQuantity = poQuantity;
		this.poWarrantyMonths = poWarrantyMonths;
		this.maintenanceDays = maintenanceDays;
		this.erpoa = erpoa;
		this.perDayFine = perDayFine;
		this.plannedQuantity = plannedQuantity;
		this.productionStartDate = productionStartDate;
		this.productionEndDate = productionEndDate;
		this.installationStarted = installationStarted;
		this.installationEnded = installationEnded;
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
		this.greaseLevelKg = greaseLevelKg;
		this.installationTechnicianId = installationTechnicianId;
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


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getDispatchDate() {
		return dispatchDate;
	}


	public void setDispatchDate(String dispatchDate) {
		this.dispatchDate = dispatchDate;
	}


	public LocalDateTime getDeliveredDate() {
		return deliveredDate;
	}


	public void setDeliveredDate(LocalDateTime deliveredDate) {
		this.deliveredDate = deliveredDate;
	}


	public Boolean getTechnicianAssigned() {
		return technicianAssigned;
	}


	public void setTechnicianAssigned(Boolean technicianAssigned) {
		this.technicianAssigned = technicianAssigned;
	}


	public String getPoNumber() {
		return poNumber;
	}


	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}


	public LocalDate getPoDate() {
		return poDate;
	}


	public void setPoDate(LocalDate poDate) {
		this.poDate = poDate;
	}

	public Integer getPoQuantity() {
		return poQuantity;
	}


	public void setPoQuantity(Integer poQuantity) {
		this.poQuantity = poQuantity;
	}


	public Integer getPoWarrantyMonths() {
		return poWarrantyMonths;
	}


	public void setPoWarrantyMonths(Integer poWarrantyMonths) {
		this.poWarrantyMonths = poWarrantyMonths;
	}


	public Integer getMaintenanceDays() {
		return maintenanceDays;
	}


	public void setMaintenanceDays(Integer maintenanceDays) {
		this.maintenanceDays = maintenanceDays;
	}


	public String getErpoa() {
		return erpoa;
	}


	public void setErpoa(String erpoa) {
		this.erpoa = erpoa;
	}


	public Double getPerDayFine() {
		return perDayFine;
	}


	public void setPerDayFine(Double perDayFine) {
		this.perDayFine = perDayFine;
	}


	public Integer getPlannedQuantity() {
		return plannedQuantity;
	}


	public void setPlannedQuantity(Integer plannedQuantity) {
		this.plannedQuantity = plannedQuantity;
	}


	public LocalDateTime getProductionStartDate() {
		return productionStartDate;
	}


	public void setProductionStartDate(LocalDateTime productionStartDate) {
		this.productionStartDate = productionStartDate;
	}


	public LocalDateTime getProductionEndDate() {
		return productionEndDate;
	}


	public void setProductionEndDate(LocalDateTime productionEndDate) {
		this.productionEndDate = productionEndDate;
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


	public Long getInstallationTechnicianId() {
		return installationTechnicianId;
	}


	public void setInstallationTechnicianId(Long installationTechnicianId) {
		this.installationTechnicianId = installationTechnicianId;
	}

	
}
