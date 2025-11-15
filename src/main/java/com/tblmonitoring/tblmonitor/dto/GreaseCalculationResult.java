package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;

public class GreaseCalculationResult {

    private String modelNo;
    private double greaseUsedPerDayKg;
    private int daysUntilEmpty;
    private LocalDate estimatedEmptyDate;
    private int tenKgSurvivalDays;
    private double greaseRemainingKg;
    private LocalDate tenKgReachedDate;
    private LocalDate emptyDate;

	    
    public GreaseCalculationResult() {
		// TODO Auto-generated constructor stub
	}

	public GreaseCalculationResult(String modelNo, double greaseUsedPerDayKg, int daysUntilEmpty,
			LocalDate estimatedEmptyDate, int tenKgSurvivalDays,double greaseRemainingKg, LocalDate tenKgReachedDate
			, LocalDate emptyDate) {
		super();
		this.modelNo = modelNo;
		this.greaseUsedPerDayKg = greaseUsedPerDayKg;
		this.daysUntilEmpty = daysUntilEmpty;
		this.estimatedEmptyDate = estimatedEmptyDate;
		this.tenKgSurvivalDays = tenKgSurvivalDays;
		this.greaseRemainingKg = greaseRemainingKg;
		this.tenKgReachedDate = tenKgReachedDate;
		this.emptyDate = emptyDate;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public double getGreaseUsedPerDayKg() {
		return greaseUsedPerDayKg;
	}

	public void setGreaseUsedPerDayKg(double greaseUsedPerDayKg) {
		this.greaseUsedPerDayKg = greaseUsedPerDayKg;
	}

	public int getDaysUntilEmpty() {
		return daysUntilEmpty;
	}

	public void setDaysUntilEmpty(int daysUntilEmpty) {
		this.daysUntilEmpty = daysUntilEmpty;
	}

	public LocalDate getEstimatedEmptyDate() {
		return estimatedEmptyDate;
	}

	public void setEstimatedEmptyDate(LocalDate estimatedEmptyDate) {
		this.estimatedEmptyDate = estimatedEmptyDate;
	}

	public int getTenKgSurvivalDays() {
		return tenKgSurvivalDays;
	}

	public void setTenKgSurvivalDays(int tenKgSurvivalDays) {
		this.tenKgSurvivalDays = tenKgSurvivalDays;
	}

	public double getGreaseRemainingKg() {
		return greaseRemainingKg;
	}

	public void setGreaseRemainingKg(double greaseRemainingKg) {
		this.greaseRemainingKg = greaseRemainingKg;
	}

	public LocalDate getTenKgReachedDate() {
		return tenKgReachedDate;
	}

	public void setTenKgReachedDate(LocalDate tenKgReachedDate) {
		this.tenKgReachedDate = tenKgReachedDate;
	}

	public LocalDate getEmptyDate() {
		return emptyDate;
	}

	public void setEmptyDate(LocalDate emptyDate) {
		this.emptyDate = emptyDate;
	}

	
	
}
