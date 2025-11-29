package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "machine_production")
public class MachineProduction {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String machineSerialNo;

	    @Column(nullable = false)
	    private String jobCardNo;

	    // Main components
	    private String motorNo;
	    private String sensorNo;
	    private String applicatorNo;
	    private String batteryNo;
	    private String solarChargeControllerNo;
	    private String solarPanelNo1;
	    private String solarPanelNo2;
	    private String cabinetNo;
	    private String batchCounterNo;
	    private String mcbNo;
	    private String gearPumpNo;
	    private LocalDate productionDate;
	    private LocalDate productionEndDate;

	    // Subassembly batch numbers & dates
//	    private String offlineSubAssyBatchNo;
//	    private LocalDateTime offlineSubAssyBatchDate;
//	    private String offlineAssyBatchNo;
//	    private LocalDate offlineAssyBatchDate;
	    private String junctionBoxBatchNo;
	    private LocalDate junctionBoxBatchDate;
	    private String sensorAssyBatchNo;
	    private LocalDate sensorAssyBatchDate;
	    private String tmpAssyBatchNo;
	    private LocalDate tmpAssyBatchDate;
	    private String applicatorAssyBatchNo;
	    private LocalDate applicatorAssyBatchDate;
	    
	    private String solarPanelAssyBatchNo;
	    private LocalDate solarPanelAssyBatchDate;

	    @Column(name = "qc_file_path")
	    private String qcFilePath;

	    @Column(name = "qc_inspection_date")
	    private LocalDateTime qcInspectionDate;

	    @Enumerated(EnumType.STRING)
	    @Column(length = 50)
	    private MachineStatus status = MachineStatus.AVAILABLE;

	    private LocalDateTime createdAt = LocalDateTime.now();
	    private LocalDateTime updatedAt = LocalDateTime.now();

	    @Column(name = "submitted_by_id")
	    private Long submittedById;

	    @Column(name = "submitted_by_name")
	    private String submittedByName;

	    
	    @PreUpdate
	    public void preUpdate() {
	        updatedAt = LocalDateTime.now();
	    }

	    public enum MachineStatus {
	        AVAILABLE,
	        DISPATCHED,
	        PENDING_QUALITY_CHECK,
	        READY_TO_DISPATCH
	    }
    
    public MachineProduction() {
		// TODO Auto-generated constructor stub
	}

	public MachineProduction(Long id, String machineSerialNo, String jobCardNo, String motorNo, String sensorNo,
			String applicatorNo, String batteryNo, String solarChargeControllerNo, String solarPanelNo1,
			String solarPanelNo2, String cabinetNo, String batchCounterNo, String mcbNo, String gearPumpNo,
			LocalDate productionDate, LocalDate productionEndDate, String junctionBoxBatchNo,
			LocalDate junctionBoxBatchDate, String sensorAssyBatchNo, LocalDate sensorAssyBatchDate,
			String tmpAssyBatchNo, LocalDate tmpAssyBatchDate, String applicatorAssyBatchNo,
			LocalDate applicatorAssyBatchDate, String solarPanelAssyBatchNo, LocalDate solarPanelAssyBatchDate,
			String qcFilePath, LocalDateTime qcInspectionDate, MachineStatus status, LocalDateTime createdAt,
			LocalDateTime updatedAt, Long submittedById, String submittedByName) {
		super();
		this.id = id;
		this.machineSerialNo = machineSerialNo;
		this.jobCardNo = jobCardNo;
		this.motorNo = motorNo;
		this.sensorNo = sensorNo;
		this.applicatorNo = applicatorNo;
		this.batteryNo = batteryNo;
		this.solarChargeControllerNo = solarChargeControllerNo;
		this.solarPanelNo1 = solarPanelNo1;
		this.solarPanelNo2 = solarPanelNo2;
		this.cabinetNo = cabinetNo;
		this.batchCounterNo = batchCounterNo;
		this.mcbNo = mcbNo;
		this.gearPumpNo = gearPumpNo;
		this.productionDate = productionDate;
		this.productionEndDate = productionEndDate;
		this.junctionBoxBatchNo = junctionBoxBatchNo;
		this.junctionBoxBatchDate = junctionBoxBatchDate;
		this.sensorAssyBatchNo = sensorAssyBatchNo;
		this.sensorAssyBatchDate = sensorAssyBatchDate;
		this.tmpAssyBatchNo = tmpAssyBatchNo;
		this.tmpAssyBatchDate = tmpAssyBatchDate;
		this.applicatorAssyBatchNo = applicatorAssyBatchNo;
		this.applicatorAssyBatchDate = applicatorAssyBatchDate;
		this.solarPanelAssyBatchNo = solarPanelAssyBatchNo;
		this.solarPanelAssyBatchDate = solarPanelAssyBatchDate;
		this.qcFilePath = qcFilePath;
		this.qcInspectionDate = qcInspectionDate;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.submittedById = submittedById;
		this.submittedByName = submittedByName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMachineSerialNo() {
		return machineSerialNo;
	}

	public void setMachineSerialNo(String machineSerialNo) {
		this.machineSerialNo = machineSerialNo;
	}

	public String getJobCardNo() {
		return jobCardNo;
	}

	public void setJobCardNo(String jobCardNo) {
		this.jobCardNo = jobCardNo;
	}

	public String getMotorNo() {
		return motorNo;
	}

	public void setMotorNo(String motorNo) {
		this.motorNo = motorNo;
	}

	public String getSensorNo() {
		return sensorNo;
	}

	public void setSensorNo(String sensorNo) {
		this.sensorNo = sensorNo;
	}

	public String getApplicatorNo() {
		return applicatorNo;
	}

	public void setApplicatorNo(String applicatorNo) {
		this.applicatorNo = applicatorNo;
	}

	public String getBatteryNo() {
		return batteryNo;
	}

	public void setBatteryNo(String batteryNo) {
		this.batteryNo = batteryNo;
	}

	public String getSolarChargeControllerNo() {
		return solarChargeControllerNo;
	}

	public void setSolarChargeControllerNo(String solarChargeControllerNo) {
		this.solarChargeControllerNo = solarChargeControllerNo;
	}

	public String getSolarPanelNo1() {
		return solarPanelNo1;
	}

	public void setSolarPanelNo1(String solarPanelNo1) {
		this.solarPanelNo1 = solarPanelNo1;
	}

	public String getSolarPanelNo2() {
		return solarPanelNo2;
	}

	public void setSolarPanelNo2(String solarPanelNo2) {
		this.solarPanelNo2 = solarPanelNo2;
	}

	public String getCabinetNo() {
		return cabinetNo;
	}

	public void setCabinetNo(String cabinetNo) {
		this.cabinetNo = cabinetNo;
	}

	public String getBatchCounterNo() {
		return batchCounterNo;
	}

	public void setBatchCounterNo(String batchCounterNo) {
		this.batchCounterNo = batchCounterNo;
	}

	public String getMcbNo() {
		return mcbNo;
	}

	public void setMcbNo(String mcbNo) {
		this.mcbNo = mcbNo;
	}

	public String getGearPumpNo() {
		return gearPumpNo;
	}

	public void setGearPumpNo(String gearPumpNo) {
		this.gearPumpNo = gearPumpNo;
	}

	public LocalDate getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(LocalDate productionDate) {
		this.productionDate = productionDate;
	}

	public LocalDate getProductionEndDate() {
		return productionEndDate;
	}

	public void setProductionEndDate(LocalDate productionEndDate) {
		this.productionEndDate = productionEndDate;
	}

	public String getJunctionBoxBatchNo() {
		return junctionBoxBatchNo;
	}

	public void setJunctionBoxBatchNo(String junctionBoxBatchNo) {
		this.junctionBoxBatchNo = junctionBoxBatchNo;
	}

	public LocalDate getJunctionBoxBatchDate() {
		return junctionBoxBatchDate;
	}

	public void setJunctionBoxBatchDate(LocalDate junctionBoxBatchDate) {
		this.junctionBoxBatchDate = junctionBoxBatchDate;
	}

	public String getSensorAssyBatchNo() {
		return sensorAssyBatchNo;
	}

	public void setSensorAssyBatchNo(String sensorAssyBatchNo) {
		this.sensorAssyBatchNo = sensorAssyBatchNo;
	}

	public LocalDate getSensorAssyBatchDate() {
		return sensorAssyBatchDate;
	}

	public void setSensorAssyBatchDate(LocalDate sensorAssyBatchDate) {
		this.sensorAssyBatchDate = sensorAssyBatchDate;
	}

	public String getTmpAssyBatchNo() {
		return tmpAssyBatchNo;
	}

	public void setTmpAssyBatchNo(String tmpAssyBatchNo) {
		this.tmpAssyBatchNo = tmpAssyBatchNo;
	}

	public LocalDate getTmpAssyBatchDate() {
		return tmpAssyBatchDate;
	}

	public void setTmpAssyBatchDate(LocalDate tmpAssyBatchDate) {
		this.tmpAssyBatchDate = tmpAssyBatchDate;
	}

	public String getApplicatorAssyBatchNo() {
		return applicatorAssyBatchNo;
	}

	public void setApplicatorAssyBatchNo(String applicatorAssyBatchNo) {
		this.applicatorAssyBatchNo = applicatorAssyBatchNo;
	}

	public LocalDate getApplicatorAssyBatchDate() {
		return applicatorAssyBatchDate;
	}

	public void setApplicatorAssyBatchDate(LocalDate applicatorAssyBatchDate) {
		this.applicatorAssyBatchDate = applicatorAssyBatchDate;
	}

	public String getSolarPanelAssyBatchNo() {
		return solarPanelAssyBatchNo;
	}

	public void setSolarPanelAssyBatchNo(String solarPanelAssyBatchNo) {
		this.solarPanelAssyBatchNo = solarPanelAssyBatchNo;
	}

	public LocalDate getSolarPanelAssyBatchDate() {
		return solarPanelAssyBatchDate;
	}

	public void setSolarPanelAssyBatchDate(LocalDate solarPanelAssyBatchDate) {
		this.solarPanelAssyBatchDate = solarPanelAssyBatchDate;
	}

	public String getQcFilePath() {
		return qcFilePath;
	}

	public void setQcFilePath(String qcFilePath) {
		this.qcFilePath = qcFilePath;
	}

	public LocalDateTime getQcInspectionDate() {
		return qcInspectionDate;
	}

	public void setQcInspectionDate(LocalDateTime qcInspectionDate) {
		this.qcInspectionDate = qcInspectionDate;
	}

	public MachineStatus getStatus() {
		return status;
	}

	public void setStatus(MachineStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getSubmittedById() {
		return submittedById;
	}

	public void setSubmittedById(Long submittedById) {
		this.submittedById = submittedById;
	}

	public String getSubmittedByName() {
		return submittedByName;
	}

	public void setSubmittedByName(String submittedByName) {
		this.submittedByName = submittedByName;
	}

	
    
}
