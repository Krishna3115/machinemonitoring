package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;

public class InProgressJobCardDTO {

	private String jobCardNumber;
    private Integer quantity;
    private Integer producedCount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String firstMachineSerial;
    private String lastMachineSerial;
    private int qcDoneCount;
    private int dispatchedCount;

    
    public InProgressJobCardDTO() {
		// TODO Auto-generated constructor stub
	}

	

	public InProgressJobCardDTO(String jobCardNumber, Integer quantity, Integer producedCount, LocalDate startDate,
			LocalDate endDate, String firstMachineSerial, String lastMachineSerial, int qcDoneCount,
			int dispatchedCount) {
		super();
		this.jobCardNumber = jobCardNumber;
		this.quantity = quantity;
		this.producedCount = producedCount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.firstMachineSerial = firstMachineSerial;
		this.lastMachineSerial = lastMachineSerial;
		this.qcDoneCount = qcDoneCount;
		this.dispatchedCount = dispatchedCount;
	}



	public String getJobCardNumber() {
		return jobCardNumber;
	}

	public void setJobCardNumber(String jobCardNumber) {
		this.jobCardNumber = jobCardNumber;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getProducedCount() {
		return producedCount;
	}

	public void setProducedCount(Integer producedCount) {
		this.producedCount = producedCount;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getFirstMachineSerial() {
		return firstMachineSerial;
	}

	public void setFirstMachineSerial(String firstMachineSerial) {
		this.firstMachineSerial = firstMachineSerial;
	}

	public String getLastMachineSerial() {
		return lastMachineSerial;
	}

	public void setLastMachineSerial(String lastMachineSerial) {
		this.lastMachineSerial = lastMachineSerial;
	}



	public int getQcDoneCount() {
		return qcDoneCount;
	}



	public void setQcDoneCount(int qcDoneCount) {
		this.qcDoneCount = qcDoneCount;
	}

	public int getDispatchedCount() {
		return dispatchedCount;
	}

	public void setDispatchedCount(int dispatchedCount) {
		this.dispatchedCount = dispatchedCount;
	}
    
	
    
}
