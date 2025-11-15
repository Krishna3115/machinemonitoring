package com.tblmonitoring.tblmonitor.dto;

public class JobCardStatusDTO {

	private String jobCardNumber;
    private int quantity;
    private int producedCount;
    private int pendingCount;
    
    public JobCardStatusDTO() {
		// TODO Auto-generated constructor stub
	}

	public JobCardStatusDTO(String jobCardNumber, int quantity, int producedCount, int pendingCount) {
		super();
		this.jobCardNumber = jobCardNumber;
		this.quantity = quantity;
		this.producedCount = producedCount;
		this.pendingCount = pendingCount;
	}

	public String getJobCardNumber() {
		return jobCardNumber;
	}

	public void setJobCardNumber(String jobCardNumber) {
		this.jobCardNumber = jobCardNumber;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getProducedCount() {
		return producedCount;
	}

	public void setProducedCount(int producedCount) {
		this.producedCount = producedCount;
	}

	public int getPendingCount() {
		return pendingCount;
	}

	public void setPendingCount(int pendingCount) {
		this.pendingCount = pendingCount;
	}
    
    
}
