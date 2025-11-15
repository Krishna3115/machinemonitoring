package com.tblmonitoring.tblmonitor.entity;

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
    private String greaseLevelPhotoUrl;
    private String greaseLevel;
    private String batteryReading;
    private String solarPanelReading;
    private Integer timeCount;
    private Integer wheelCount;
    private String machineInfoPlatePhotoUrl;
    private String sensor;
    private String applicator;
    private String machineStatus;
    private LocalDateTime maintenanceDate;
    @Column(name = "maintenance_started")
    private LocalDateTime maintenanceStarted;

    @Column(name = "maintenance_ended")
    private LocalDateTime maintenanceEnded;

    @Column(name = "maintenance_technician_id")
    private Long maintenanceTechnicianId;
    
    @Column(name = "solar_panel_reading_1")
    private String solarPanelReading1;

    @Column(name = "solar_panel_reading_2")
    private String solarPanelReading2;

    @Column(length = 1000) // or as needed
    private String remark;
    
    @ManyToOne
    @JoinColumn(name = "inspected_by")
    private Users inspectedBy;
    
    public MachineInspection() {
		// TODO Auto-generated constructor stub
	}

    

	public MachineInspection(Long id, Machine machine, String modelNo, String greaseLevelPhotoUrl, String greaseLevel,
			String batteryReading, String solarPanelReading, Integer timeCount, Integer wheelCount,
			String machineInfoPlatePhotoUrl, String sensor, String applicator, String machineStatus,
			LocalDateTime maintenanceDate, LocalDateTime maintenanceStarted, LocalDateTime maintenanceEnded,
			Long maintenanceTechnicianId, String solarPanelReading1, String solarPanelReading2, String remark,
			Users inspectedBy) {
		super();
		this.id = id;
		this.machine = machine;
		this.modelNo = modelNo;
		this.greaseLevelPhotoUrl = greaseLevelPhotoUrl;
		this.greaseLevel = greaseLevel;
		this.batteryReading = batteryReading;
		this.solarPanelReading = solarPanelReading;
		this.timeCount = timeCount;
		this.wheelCount = wheelCount;
		this.machineInfoPlatePhotoUrl = machineInfoPlatePhotoUrl;
		this.sensor = sensor;
		this.applicator = applicator;
		this.machineStatus = machineStatus;
		this.maintenanceDate = maintenanceDate;
		this.maintenanceStarted = maintenanceStarted;
		this.maintenanceEnded = maintenanceEnded;
		this.maintenanceTechnicianId = maintenanceTechnicianId;
		this.solarPanelReading1 = solarPanelReading1;
		this.solarPanelReading2 = solarPanelReading2;
		this.remark = remark;
		this.inspectedBy = inspectedBy;
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

	public Users getInspectedBy() {
		return inspectedBy;
	}

	public void setInspectedBy(Users inspectedBy) {
		this.inspectedBy = inspectedBy;
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