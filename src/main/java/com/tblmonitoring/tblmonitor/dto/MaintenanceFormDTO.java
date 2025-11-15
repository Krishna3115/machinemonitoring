package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDateTime;

public class MaintenanceFormDTO {

	private Long id;
    private Long machineId;
    private String modelNo;
    private String greaseLevel;
    private String greaseLevelPhotoUrl;
    private String batteryReading;
    private String solarPanelReading;
    private Integer timeCount;
    private Integer wheelCount;
    private String machineInfoPlatePhotoUrl;
    private String sensor;
    private String applicator;
    private String machineStatus;
    private LocalDateTime maintenanceDate;
    private Long inspectedByUserId;
    private String inspectedByName;
    private Long technicianUserId;
    private LocalDateTime dueDate;
    private String status;
    private LocalDateTime maintenanceStarted;
    private LocalDateTime maintenanceEnded;
    private String division;
    private String section;
    private String solarPanelReading1;
    private String solarPanelReading2;
    private String remark;


    // + getters and setters
    
    public MaintenanceFormDTO() {
		// TODO Auto-generated constructor stub
	}


	public MaintenanceFormDTO(Long id, Long machineId, String modelNo, String greaseLevel, String greaseLevelPhotoUrl,
			String batteryReading, String solarPanelReading, Integer timeCount, Integer wheelCount,
			String machineInfoPlatePhotoUrl, String sensor, String applicator, String machineStatus,
			LocalDateTime maintenanceDate, Long inspectedByUserId, String inspectedByName, Long technicianUserId,
			LocalDateTime dueDate, String status, LocalDateTime maintenanceStarted, LocalDateTime maintenanceEnded,
			String division, String section, String solarPanelReading1, String solarPanelReading2, String remark) {
		super();
		this.id = id;
		this.machineId = machineId;
		this.modelNo = modelNo;
		this.greaseLevel = greaseLevel;
		this.greaseLevelPhotoUrl = greaseLevelPhotoUrl;
		this.batteryReading = batteryReading;
		this.solarPanelReading = solarPanelReading;
		this.timeCount = timeCount;
		this.wheelCount = wheelCount;
		this.machineInfoPlatePhotoUrl = machineInfoPlatePhotoUrl;
		this.sensor = sensor;
		this.applicator = applicator;
		this.machineStatus = machineStatus;
		this.maintenanceDate = maintenanceDate;
		this.inspectedByUserId = inspectedByUserId;
		this.inspectedByName = inspectedByName;
		this.technicianUserId = technicianUserId;
		this.dueDate = dueDate;
		this.status = status;
		this.maintenanceStarted = maintenanceStarted;
		this.maintenanceEnded = maintenanceEnded;
		this.division = division;
		this.section = section;
		this.solarPanelReading1 = solarPanelReading1;
		this.solarPanelReading2 = solarPanelReading2;
		this.remark = remark;
	}



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getMachineId() {
		return machineId;
	}


	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}


	public String getModelNo() {
		return modelNo;
	}


	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
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


	public String getBatteryReading() {
		return batteryReading;
	}


	public void setBatteryReading(String batteryReading) {
		this.batteryReading = batteryReading;
	}


	public String getSolarPanelReading() {
		return solarPanelReading;
	}


	public void setSolarPanelReading(String solarPanelReading) {
		this.solarPanelReading = solarPanelReading;
	}


	public Integer getTimeCount() {
		return timeCount;
	}


	public void setTimeCount(Integer timeCount) {
		this.timeCount = timeCount;
	}


	public Integer getWheelCount() {
		return wheelCount;
	}


	public void setWheelCount(Integer wheelCount) {
		this.wheelCount = wheelCount;
	}


	public String getMachineInfoPlatePhotoUrl() {
		return machineInfoPlatePhotoUrl;
	}


	public void setMachineInfoPlatePhotoUrl(String machineInfoPlatePhotoUrl) {
		this.machineInfoPlatePhotoUrl = machineInfoPlatePhotoUrl;
	}


	public String getSensor() {
		return sensor;
	}


	public void setSensor(String sensor) {
		this.sensor = sensor;
	}


	public String getApplicator() {
		return applicator;
	}


	public void setApplicator(String applicator) {
		this.applicator = applicator;
	}


	public String getMachineStatus() {
		return machineStatus;
	}


	public void setMachineStatus(String machineStatus) {
		this.machineStatus = machineStatus;
	}


	public LocalDateTime getMaintenanceDate() {
		return maintenanceDate;
	}


	public void setMaintenanceDate(LocalDateTime maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}


	public Long getInspectedByUserId() {
		return inspectedByUserId;
	}


	public void setInspectedByUserId(Long inspectedByUserId) {
		this.inspectedByUserId = inspectedByUserId;
	}


	public String getInspectedByName() {
		return inspectedByName;
	}


	public void setInspectedByName(String inspectedByName) {
		this.inspectedByName = inspectedByName;
	}


	public Long getTechnicianUserId() {
		return technicianUserId;
	}


	public void setTechnicianUserId(Long technicianUserId) {
		this.technicianUserId = technicianUserId;
	}


	public LocalDateTime getDueDate() {
		return dueDate;
	}


	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public LocalDateTime getMaintenanceStarted() {
		return maintenanceStarted;
	}


	public void setMaintenanceStarted(LocalDateTime maintenanceStarted) {
		this.maintenanceStarted = maintenanceStarted;
	}


	public LocalDateTime getMaintenanceEnded() {
		return maintenanceEnded;
	}


	public void setMaintenanceEnded(LocalDateTime maintenanceEnded) {
		this.maintenanceEnded = maintenanceEnded;
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


	public String getSolarPanelReading1() {
		return solarPanelReading1;
	}


	public void setSolarPanelReading1(String solarPanelReading1) {
		this.solarPanelReading1 = solarPanelReading1;
	}


	public String getSolarPanelReading2() {
		return solarPanelReading2;
	}


	public void setSolarPanelReading2(String solarPanelReading2) {
		this.solarPanelReading2 = solarPanelReading2;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}