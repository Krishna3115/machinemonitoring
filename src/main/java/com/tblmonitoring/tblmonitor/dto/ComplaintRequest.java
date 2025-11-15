package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;

public class ComplaintRequest {

	  private String complaintNo;
	    private LocalDate complaintDate;
	    private String machineSerial;
	    
	    public ComplaintRequest() {
			// TODO Auto-generated constructor stub
		}

		public ComplaintRequest(String complaintNo, LocalDate complaintDate, String machineSerial) {
			super();
			this.complaintNo = complaintNo;
			this.complaintDate = complaintDate;
			this.machineSerial = machineSerial;
		}

		public String getComplaintNo() {
			return complaintNo;
		}

		public void setComplaintNo(String complaintNo) {
			this.complaintNo = complaintNo;
		}

		public LocalDate getComplaintDate() {
			return complaintDate;
		}

		public void setComplaintDate(LocalDate complaintDate) {
			this.complaintDate = complaintDate;
		}

		public String getMachineSerial() {
			return machineSerial;
		}

		public void setMachineSerial(String machineSerial) {
			this.machineSerial = machineSerial;
		}
	    
	    
}
