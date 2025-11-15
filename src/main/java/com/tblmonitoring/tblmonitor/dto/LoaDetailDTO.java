package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoaDetailDTO {

	 private String poNumber;
	    private int plannedQuantity;
	    private LocalDateTime startDate;
	    private LocalDateTime endDate;

	    public LoaDetailDTO() {}

	    public LoaDetailDTO(String poNumber, int plannedQuantity, LocalDateTime startDate, LocalDateTime endDate) {
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

	    public LocalDateTime getStartDate() {
	        return startDate;
	    }

	    public void setStartDate(LocalDateTime startDate) {
	        this.startDate = startDate;
	    }

	    public LocalDateTime getEndDate() {
	        return endDate;
	    }

	    public void setEndDate(LocalDateTime endDate) {
	        this.endDate = endDate;
	    }
}