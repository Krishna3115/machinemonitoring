package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDateTime;

public class MachineQRDTO {

	private String machineSerialNo;
    private String jobCardNo;
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
    private LocalDateTime finalQCDate;
    private String division;
    private String section;
    private String dispatchDate;
    private String deliveredDate;
    private LocalDateTime installationDate;
    private LocalDateTime maintenanceEnded;
   
    
    
    public MachineQRDTO() {
		// TODO Auto-generated constructor stub
	}



	public MachineQRDTO(String machineSerialNo, String jobCardNo, String motorNo, String sensorNo, String applicatorNo,
			String batteryNo, String solarChargeControllerNo, String solarPanelNo1, String solarPanelNo2,
			String cabinetNo, String batchCounterNo, String mcbNo, String gearPumpNo, LocalDateTime finalQCDate,
			String division, String section, String dispatchDate, String deliveredDate, LocalDateTime installationDate,
			LocalDateTime maintenanceEnded) {
		super();
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
		this.finalQCDate = finalQCDate;
		this.division = division;
		this.section = section;
		this.dispatchDate = dispatchDate;
		this.deliveredDate = deliveredDate;
		this.installationDate = installationDate;
		this.maintenanceEnded = maintenanceEnded;
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



	public LocalDateTime getFinalQCDate() {
		return finalQCDate;
	}



	public void setFinalQCDate(LocalDateTime finalQCDate) {
		this.finalQCDate = finalQCDate;
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



	public String getDispatchDate() {
		return dispatchDate;
	}



	public void setDispatchDate(String dispatchDate) {
		this.dispatchDate = dispatchDate;
	}



	public String getDeliveredDate() {
		return deliveredDate;
	}



	public void setDeliveredDate(String deliveredDate) {
		this.deliveredDate = deliveredDate;
	}



	public LocalDateTime getInstallationDate() {
		return installationDate;
	}



	public void setInstallationDate(LocalDateTime installationDate) {
		this.installationDate = installationDate;
	}



	public LocalDateTime getMaintenanceEnded() {
		return maintenanceEnded;
	}



	public void setMaintenanceEnded(LocalDateTime maintenanceEnded) {
		this.maintenanceEnded = maintenanceEnded;
	}

	
    
}
