package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ProductionPlanningDTO {

	 @JsonProperty("poNumber")
	    private String poNumber;

	    @JsonProperty("plannedQuantity")
	    private int plannedQuantity;

	    @JsonProperty("startDate")
	    private LocalDate startDate;

	    @JsonProperty("endDate")
	    private LocalDate endDate;
    
    public ProductionPlanningDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductionPlanningDTO(String poNumber, int plannedQuantity, LocalDate startDate, LocalDate endDate) {
		super();
		this.poNumber = poNumber;
		this.plannedQuantity = plannedQuantity;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public int getPlannedQuantity() {
		return plannedQuantity;
	}

	public void setPlannedQuantity(int plannedQuantity) {
		this.plannedQuantity = plannedQuantity;
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
    
    
}
