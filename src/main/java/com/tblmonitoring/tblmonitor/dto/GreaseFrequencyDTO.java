package com.tblmonitoring.tblmonitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class GreaseFrequencyDTO {

	private String modelNo;
    private int wheelsPerDay;
    private double greaseReleaseRate;
    
    public GreaseFrequencyDTO() {
		// TODO Auto-generated constructor stub
	}

	public GreaseFrequencyDTO(String modelNo, int wheelsPerDay, double greaseReleaseRate) {
		super();
		this.modelNo = modelNo;
		this.wheelsPerDay = wheelsPerDay;
		this.greaseReleaseRate = greaseReleaseRate;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public int getWheelsPerDay() {
		return wheelsPerDay;
	}

	public void setWheelsPerDay(int wheelsPerDay) {
		this.wheelsPerDay = wheelsPerDay;
	}

	public double getGreaseReleaseRate() {
		return greaseReleaseRate;
	}

	public void setGreaseReleaseRate(double greaseReleaseRate) {
		this.greaseReleaseRate = greaseReleaseRate;
	}
    
    
}
