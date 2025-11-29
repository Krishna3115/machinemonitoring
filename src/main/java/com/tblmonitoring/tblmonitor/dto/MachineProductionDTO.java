package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MachineProductionDTO {

    private Long id;

    @JsonProperty("machineSerialNo")
    private String machineSerialNo;

    @JsonProperty("jobCardNo")
    private String jobCardNo;

    @JsonProperty("motorNo")
    private String motorNo;

    @JsonProperty("sensorNo")
    private String sensorNo;

    @JsonProperty("applicatorNo")
    private String applicatorNo;

    @JsonProperty("batteryNo")
    private String batteryNo;

    @JsonProperty("solarChargeControllerNo")
    private String solarChargeControllerNo;

    @JsonProperty("solarPanelNo1")
    private String solarPanelNo1;

    @JsonProperty("solarPanelNo2")
    private String solarPanelNo2;

    @JsonProperty("cabinetNo")
    private String cabinetNo;

    @JsonProperty("batchCounterNo")
    private String batchCounterNo;

    @JsonProperty("mcbNo")
    private String mcbNo;

    @JsonProperty("gearPumpNo")
    private String gearPumpNo;
    
    @JsonProperty("productionStartDate")
    private LocalDate productionStartDate;
    
    @JsonProperty("productionEndDate")
    private LocalDate productionEndDate;
    
    @JsonProperty("junctionBoxBatchNo")
    private String junctionBoxBatchNo;

    @JsonProperty("junctionBoxBatchDate")
    private LocalDate junctionBoxBatchDate;

    @JsonProperty("sensorAssyBatchNo")
    private String sensorAssyBatchNo;

    @JsonProperty("sensorAssyBatchDate")
    private LocalDate sensorAssyBatchDate;

    @JsonProperty("tmpAssyBatchNo")
    private String tmpAssyBatchNo;

    @JsonProperty("tmpAssyBatchDate")
    private LocalDate tmpAssyBatchDate;

    @JsonProperty("applicatorAssyBatchNo")
    private String applicatorAssyBatchNo;

    @JsonProperty("applicatorAssyBatchDate")
    private LocalDate applicatorAssyBatchDate;
    
    @JsonProperty("solarPanelAssyBatchNo")
    private String solarPanelAssyBatchNo;
    
    @JsonProperty("solarPanelAssyBatchDate")
    private LocalDate solarPanelAssyBatchDate;

    private String qcFilePath;
    private LocalDateTime qcInspectionDate;
    
    private LocalDateTime createdAt;
    
    private Long submittedById;
    private String submittedByName;

   
    public MachineProductionDTO() {
    	
		// TODO Auto-generated constructor stub
	}


	public MachineProductionDTO(Long id, String machineSerialNo, String jobCardNo, String motorNo, String sensorNo,
			String applicatorNo, String batteryNo, String solarChargeControllerNo, String solarPanelNo1,
			String solarPanelNo2, String cabinetNo, String batchCounterNo, String mcbNo, String gearPumpNo,
			LocalDate productionStartDate, LocalDate productionEndDate, String junctionBoxBatchNo,
			LocalDate junctionBoxBatchDate, String sensorAssyBatchNo, LocalDate sensorAssyBatchDate,
			String tmpAssyBatchNo, LocalDate tmpAssyBatchDate, String applicatorAssyBatchNo,
			LocalDate applicatorAssyBatchDate, String solarPanelAssyBatchNo, LocalDate solarPanelAssyBatchDate,
			String qcFilePath, LocalDateTime qcInspectionDate, LocalDateTime createdAt, Long submittedById,
			String submittedByName) {
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
		this.productionStartDate = productionStartDate;
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
		this.createdAt = createdAt;
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


	public LocalDate getProductionStartDate() {
		return productionStartDate;
	}


	public void setProductionStartDate(LocalDate productionStartDate) {
		this.productionStartDate = productionStartDate;
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


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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