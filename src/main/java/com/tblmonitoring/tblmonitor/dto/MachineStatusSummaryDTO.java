package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDateTime;

public class MachineStatusSummaryDTO {

	 private String machineSerialNo;
	    private String status;
	    private LocalDateTime qcInspectionDate;
	    
	    public MachineStatusSummaryDTO() {
			// TODO Auto-generated constructor stub
		}

	    public MachineStatusSummaryDTO(String machineSerialNo, String status, LocalDateTime qcInspectionDate) {
	        this.machineSerialNo = machineSerialNo;
	        this.status = status;
	        this.qcInspectionDate = qcInspectionDate;
	    }

		public String getMachineSerialNo() {
			return machineSerialNo;
		}

		public void setMachineSerialNo(String machineSerialNo) {
			this.machineSerialNo = machineSerialNo;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public LocalDateTime getQcInspectionDate() {
			return qcInspectionDate;
		}

		public void setQcInspectionDate(LocalDateTime qcInspectionDate) {
			this.qcInspectionDate = qcInspectionDate;
		}
	    
	    
}
