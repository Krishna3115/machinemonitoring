package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "machine_inspection")
public class MachineInspection {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    private String modelNo;
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
    @Column(name = "maintenance_started")
    private LocalDateTime maintenanceStarted;

    @Column(name = "maintenance_ended")
    private LocalDateTime maintenanceEnded;

    @Column(name = "maintenance_technician_id")
    private Long maintenanceTechnicianId;
    
//    @Column(name = "solar_panel_reading_1")
//    private String solarPanelReading1;
//
//    @Column(name = "solar_panel_reading_2")
//    private String solarPanelReading2;

    @Column(length = 1000) // or as needed
    private String remark;
    
   // @ManyToOne
    @Column(name = "inspected_by", nullable = false)
    private Long inspectedByUserId;
    
    public MachineInspection() {
		// TODO Auto-generated constructor stub
	}

	public MachineInspection(Long id, Machine machine, String modelNo, LocalDate dateOfInspection,
			String greaseLevelPhotoUrl, String greaseLevel, String batteryVoltage, String solarPanelVoltage,
			Integer cycleTime, Integer wheelCount, String motorPumpStatus, String machineInfoPlatePhotoUrl,
			String solarChargeController, String sensorCondition, String applicatorStatus, String machineStatus,
			String batchCounter, String doorLock, String applicatorPhotoUrl, LocalDateTime maintenanceDate,
			LocalDateTime maintenanceStarted, LocalDateTime maintenanceEnded, Long maintenanceTechnicianId,
			String remark, Users inspectedBy) {
		super();
		this.id = id;
		this.machine = machine;
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
		this.inspectedByUserId = inspectedByUserId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
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

	public Long getInspectedByUserId() {
		return inspectedByUserId;
	}

	public void setInspectedByUserId(Long inspectedByUserId) {
		this.inspectedByUserId = inspectedByUserId;
	}

	

}