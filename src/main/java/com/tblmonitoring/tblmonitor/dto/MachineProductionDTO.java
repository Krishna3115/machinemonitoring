package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    private String qcFilePath;
    private LocalDateTime qcInspectionDate;
    
    private LocalDateTime createdAt;
   

    
    public MachineProductionDTO() {
    	
		// TODO Auto-generated constructor stub
	}


	public MachineProductionDTO(Long id, String machineSerialNo, String jobCardNo, String motorNo, String sensorNo,
			String applicatorNo, String batteryNo, String solarChargeControllerNo, String solarPanelNo1,
			String solarPanelNo2, String cabinetNo, String batchCounterNo, String mcbNo, String gearPumpNo,
			String qcFilePath, LocalDateTime qcInspectionDate, LocalDateTime createdAt) {
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
		this.qcFilePath = qcFilePath;
		this.qcInspectionDate = qcInspectionDate;
		this.createdAt = createdAt;
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


	public String getQcFilePath() {
		return qcFilePath;
	}


	public void setQcFilePath(String qcFilePath) {
		this.qcFilePath = qcFilePath;
	}


	public java.time.LocalDateTime getQcInspectionDate() {
		return qcInspectionDate;
	}


	public void setQcInspectionDate(java.time.LocalDateTime qcInspectionDate) {
		this.qcInspectionDate = qcInspectionDate;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

     
}
