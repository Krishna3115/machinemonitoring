package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "grease_filling_logs")
public class GreaseFillingRecord {

	@Id 
	@GeneratedValue 
	private Long id;
    private String modelNo;
    private LocalDate fillDate;
    private Double remainingGreaseKg;
    private String remainingPhotoUrl;
    private Double filledGreaseKg;
    private String filledPhotoUrl;
    private Boolean isFullTank;
    private Long technicianId;
    private String remarks;
    
    public GreaseFillingRecord() {
		// TODO Auto-generated constructor stub
	}

	public GreaseFillingRecord(Long id, String modelNo, LocalDate fillDate, Double remainingGreaseKg,
			String remainingPhotoUrl, Double filledGreaseKg, String filledPhotoUrl, Boolean isFullTank,
			Long technicianId, String remarks) {
		super();
		this.id = id;
		this.modelNo = modelNo;
		this.fillDate = fillDate;
		this.remainingGreaseKg = remainingGreaseKg;
		this.remainingPhotoUrl = remainingPhotoUrl;
		this.filledGreaseKg = filledGreaseKg;
		this.filledPhotoUrl = filledPhotoUrl;
		this.isFullTank = isFullTank;
		this.technicianId = technicianId;
		this.remarks = remarks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public LocalDate getFillDate() {
		return fillDate;
	}

	public void setFillDate(LocalDate fillDate) {
		this.fillDate = fillDate;
	}

	public Double getRemainingGreaseKg() {
		return remainingGreaseKg;
	}

	public void setRemainingGreaseKg(Double remainingGreaseKg) {
		this.remainingGreaseKg = remainingGreaseKg;
	}

	public String getRemainingPhotoUrl() {
		return remainingPhotoUrl;
	}

	public void setRemainingPhotoUrl(String remainingPhotoUrl) {
		this.remainingPhotoUrl = remainingPhotoUrl;
	}

	public Double getFilledGreaseKg() {
		return filledGreaseKg;
	}

	public void setFilledGreaseKg(Double filledGreaseKg) {
		this.filledGreaseKg = filledGreaseKg;
	}

	public String getFilledPhotoUrl() {
		return filledPhotoUrl;
	}

	public void setFilledPhotoUrl(String filledPhotoUrl) {
		this.filledPhotoUrl = filledPhotoUrl;
	}

	public Boolean getIsFullTank() {
		return isFullTank;
	}

	public void setIsFullTank(Boolean isFullTank) {
		this.isFullTank = isFullTank;
	}

	public Long getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(Long technicianId) {
		this.technicianId = technicianId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    
    
}
