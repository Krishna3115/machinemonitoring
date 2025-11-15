package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "job_cards")
public class JobCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobCardNumber;
    private String machineType;
    private Integer quantity;
    private LocalDate startDate;
    private LocalDate endDate;
    private String processLayout;
    
	@Column(name = "po_number")
	private String poNumber;
    
    private Integer producedCount = 0; 
    
    @ElementCollection
    private List<String> machineSerialNumbers;
    
    public JobCard() {
		// TODO Auto-generated constructor stub
	}

	public JobCard(Long id, String jobCardNumber, String machineType, Integer quantity, LocalDate startDate,
			LocalDate endDate, String processLayout, String poNumber, Integer producedCount,
			List<String> machineSerialNumbers) {
		super();
		this.id = id;
		this.jobCardNumber = jobCardNumber;
		this.machineType = machineType;
		this.quantity = quantity;
		this.startDate = startDate;
		this.endDate = endDate;
		this.processLayout = processLayout;
		this.poNumber = poNumber;
		this.producedCount = producedCount;
		this.machineSerialNumbers = machineSerialNumbers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Integer getProducedCount() {
		return producedCount;
	}

	public void setProducedCount(Integer producedCount) {
		this.producedCount = producedCount;
	}

	public List<String> getMachineSerialNumbers() {
		return machineSerialNumbers;
	}

	public void setMachineSerialNumbers(List<String> machineSerialNumbers) {
		this.machineSerialNumbers = machineSerialNumbers;
	}

	   
}
