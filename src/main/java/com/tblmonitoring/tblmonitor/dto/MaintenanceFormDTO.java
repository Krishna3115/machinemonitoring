package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaintenanceFormDTO {

	private Long id;
    private Long machineId; 
    @JsonProperty("model_no")
    private String modelNo;
// reference by ID instead of whole entity
   
    private LocalDate dateOfInspection;
    private String greaseLevelPhotoUrl;
    private String greaseLevel;
    private String batteryVoltage;
    private String solarPanelVoltage;
    private Integer cycleTime;
    private Integer wheelCount;
    private String motorPumpStatus;
    private String machineInfoPlatePhotoUrl;
    private String solarChargeController;
    private String sensorCondition;
    private String applicatorStatus;
    private String machineStatus;
    private String batchCounter;
    private String doorLock;
    private String applicatorPhotoUrl;
    private LocalDateTime maintenanceDate;
    private LocalDateTime maintenanceStarted;
    private LocalDateTime maintenanceEnded;
    private Long maintenanceTechnicianId;
    private String remark;
    private Long inspectedById; // reference by ID instead of whole entity
    private String division;
    private String section;
    private String status;
    private LocalDateTime dueDate;
    private Long technicianUserId;
    private Long inspectedByUserId;   // ADD THIS

    // + getters and setters
    
    public MaintenanceFormDTO() {
		// TODO Auto-generated constructor stub
	}

	public MaintenanceFormDTO(Long id, Long machineId, String modelNo, LocalDate dateOfInspection,
			String greaseLevelPhotoUrl, String greaseLevel, String batteryVoltage, String solarPanelVoltage,
			Integer cycleTime, Integer wheelCount, String motorPumpStatus, String machineInfoPlatePhotoUrl,
			String solarChargeController, String sensorCondition, String applicatorStatus, String machineStatus,
			String batchCounter, String doorLock, String applicatorPhotoUrl, LocalDateTime maintenanceDate,
			LocalDateTime maintenanceStarted, LocalDateTime maintenanceEnded, Long maintenanceTechnicianId,
			String remark, Long inspectedById, String division, String section, LocalDateTime dueDate
			, Long technicianUserId, String status, Long inspectedByUserId) {
		super();
		this.id = id;
		this.machineId = machineId;
		this.modelNo = modelNo;
		this.dateOfInspection = dateOfInspection;
		this.greaseLevelPhotoUrl = greaseLevelPhotoUrl;
		this.greaseLevel = greaseLevel;
		this.batteryVoltage = batteryVoltage;
		this.solarPanelVoltage = solarPanelVoltage;
		this.cycleTime = cycleTime;
		this.wheelCount = wheelCount;
		this.motorPumpStatus = motorPumpStatus;
		this.machineInfoPlatePhotoUrl = machineInfoPlatePhotoUrl;
		this.solarChargeController = solarChargeController;
		this.sensorCondition = sensorCondition;
		this.applicatorStatus = applicatorStatus;
		this.machineStatus = machineStatus;
		this.batchCounter = batchCounter;
		this.doorLock = doorLock;
		this.applicatorPhotoUrl = applicatorPhotoUrl;
		this.maintenanceDate = maintenanceDate;
		this.maintenanceStarted = maintenanceStarted;
		this.maintenanceEnded = maintenanceEnded;
		this.maintenanceTechnicianId = maintenanceTechnicianId;
		this.remark = remark;
		this.inspectedById = inspectedById;
		this.division = division;
		this.section = section;
		this.dueDate = dueDate;
		this.technicianUserId = technicianUserId;
		this.status = status;
		this.inspectedByUserId = inspectedByUserId;
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

	public LocalDate getDateOfInspection() {
		return dateOfInspection;
	}

	public void setDateOfInspection(LocalDate dateOfInspection) {
		this.dateOfInspection = dateOfInspection;
	}

	public String getGreaseLevelPhotoUrl() {
		return greaseLevelPhotoUrl;
	}

	public void setGreaseLevelPhotoUrl(String greaseLevelPhotoUrl) {
		this.greaseLevelPhotoUrl = greaseLevelPhotoUrl;
	}

	public String getGreaseLevel() {
		return greaseLevel;
	}

	public void setGreaseLevel(String greaseLevel) {
		this.greaseLevel = greaseLevel;
	}

	public String getBatteryVoltage() {
		return batteryVoltage;
	}

	public void setBatteryVoltage(String batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}

	public String getSolarPanelVoltage() {
		return solarPanelVoltage;
	}

	public void setSolarPanelVoltage(String solarPanelVoltage) {
		this.solarPanelVoltage = solarPanelVoltage;
	}

	public Integer getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(Integer cycleTime) {
		this.cycleTime = cycleTime;
	}

	public Integer getWheelCount() {
		return wheelCount;
	}

	public void setWheelCount(Integer wheelCount) {
		this.wheelCount = wheelCount;
	}

	public String getMotorPumpStatus() {
		return motorPumpStatus;
	}

	public void setMotorPumpStatus(String motorPumpStatus) {
		this.motorPumpStatus = motorPumpStatus;
	}

	public String getMachineInfoPlatePhotoUrl() {
		return machineInfoPlatePhotoUrl;
	}

	public void setMachineInfoPlatePhotoUrl(String machineInfoPlatePhotoUrl) {
		this.machineInfoPlatePhotoUrl = machineInfoPlatePhotoUrl;
	}

	public String getSolarChargeController() {
		return solarChargeController;
	}

	public void setSolarChargeController(String solarChargeController) {
		this.solarChargeController = solarChargeController;
	}

	public String getSensorCondition() {
		return sensorCondition;
	}

	public void setSensorCondition(String sensorCondition) {
		this.sensorCondition = sensorCondition;
	}

	public String getApplicatorStatus() {
		return applicatorStatus;
	}

	public void setApplicatorStatus(String applicatorStatus) {
		this.applicatorStatus = applicatorStatus;
	}

	public String getMachineStatus() {
		return machineStatus;
	}

	public void setMachineStatus(String machineStatus) {
		this.machineStatus = machineStatus;
	}

	public String getBatchCounter() {
		return batchCounter;
	}

	public void setBatchCounter(String batchCounter) {
		this.batchCounter = batchCounter;
	}

	public String getDoorLock() {
		return doorLock;
	}

	public void setDoorLock(String doorLock) {
		this.doorLock = doorLock;
	}

	public String getApplicatorPhotoUrl() {
		return applicatorPhotoUrl;
	}

	public void setApplicatorPhotoUrl(String applicatorPhotoUrl) {
		this.applicatorPhotoUrl = applicatorPhotoUrl;
	}

	public LocalDateTime getMaintenanceDate() {
		return maintenanceDate;
	}

	public void setMaintenanceDate(LocalDateTime maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
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

	public Long getMaintenanceTechnicianId() {
		return maintenanceTechnicianId;
	}

	public void setMaintenanceTechnicianId(Long maintenanceTechnicianId) {
		this.maintenanceTechnicianId = maintenanceTechnicianId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getInspectedById() {
		return inspectedById;
	}

	public void setInspectedById(Long inspectedById) {
		this.inspectedById = inspectedById;
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

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public Long getTechnicianUserId() {
		return technicianUserId;
	}

	public void setTechnicianUserId(Long technicianUserId) {
		this.technicianUserId = technicianUserId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getInspectedByUserId() {
		return inspectedByUserId;
	}

	public void setInspectedByUserId(Long inspectedByUserId) {
		this.inspectedByUserId = inspectedByUserId;
	}

	
	
	
}