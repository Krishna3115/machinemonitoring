package com.tblmonitoring.tblmonitor.dto;

import java.util.List;

public class PendingMachineSerielDTO {

	private String jobCardNo;
    private List<String> pendingSerialNumbers;
    
    public PendingMachineSerielDTO() {
		// TODO Auto-generated constructor stub
	}

	public PendingMachineSerielDTO(String jobCardNo, List<String> pendingSerialNumbers) {
		super();
		this.jobCardNo = jobCardNo;
		this.pendingSerialNumbers = pendingSerialNumbers;
	}

	public String getJobCardNo() {
		return jobCardNo;
	}

	public void setJobCardNo(String jobCardNo) {
		this.jobCardNo = jobCardNo;
	}

	public List<String> getPendingSerialNumbers() {
		return pendingSerialNumbers;
	}

	public void setPendingSerialNumbers(List<String> pendingSerialNumbers) {
		this.pendingSerialNumbers = pendingSerialNumbers;
	}
    
    
}
