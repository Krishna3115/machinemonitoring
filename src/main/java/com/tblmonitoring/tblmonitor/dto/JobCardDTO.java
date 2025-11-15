package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobCardDTO {

	
	@Column(name = "po_number")
	private String poNumber;
	private String jobCardNumber;
	private String machineType;
    private Integer quantity;

    private LocalDate startDate;
    private LocalDate endDate;

    private String processLayout;
    
    
    public JobCardDTO() {
		// TODO Auto-generated constructor stub
	}


	public JobCardDTO(String poNumber, String jobCardNumber, String machineType, Integer quantity, LocalDate startDate,
			LocalDate endDate, String processLayout) {
		super();
		this.poNumber = poNumber;
		this.jobCardNumber = jobCardNumber;
		this.machineType = machineType;
		this.quantity = quantity;
		this.startDate = startDate;
		this.endDate = endDate;
		this.processLayout = processLayout;
	}


	public String getPoNumber() {
		return poNumber;
	}


	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}


	public String getJobCardNumber() {
		return jobCardNumber;
	}


	public void setJobCardNumber(String jobCardNumber) {
		this.jobCardNumber = jobCardNumber;
	}


	public String getMachineType() {
		return machineType;
	}


	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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


	public String getProcessLayout() {
		return processLayout;
	}


	public void setProcessLayout(String processLayout) {
		this.processLayout = processLayout;
	}


    
}
