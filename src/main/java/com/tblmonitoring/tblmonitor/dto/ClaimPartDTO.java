package com.tblmonitoring.tblmonitor.dto;

public class ClaimPartDTO {


    private Long id;
	private String partName;
    private Double actualValue;
    private Double claimedAmount;
    
    private double profitOrLossAmount;
    private double profitOrLossPercent;

    public ClaimPartDTO() {
		// TODO Auto-generated constructor stub
	}

	public ClaimPartDTO(Long id, String partName, Double actualValue, Double claimedAmount, double profitOrLossAmount,
			double profitOrLossPercent) {
		super();
		this.id = id;
		this.partName = partName;
		this.actualValue = actualValue;
		this.claimedAmount = claimedAmount;
		this.profitOrLossAmount = profitOrLossAmount;
		this.profitOrLossPercent = profitOrLossPercent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Double getActualValue() {
		return actualValue;
	}

	public void setActualValue(Double actualValue) {
		this.actualValue = actualValue;
	}

	public Double getClaimedAmount() {
		return claimedAmount;
	}

	public void setClaimedAmount(Double claimedAmount) {
		this.claimedAmount = claimedAmount;
	}

	public double getProfitOrLossAmount() {
		return profitOrLossAmount;
	}

	public void setProfitOrLossAmount(double profitOrLossAmount) {
		this.profitOrLossAmount = profitOrLossAmount;
	}

	public double getProfitOrLossPercent() {
		return profitOrLossPercent;
	}

	public void setProfitOrLossPercent(double profitOrLossPercent) {
		this.profitOrLossPercent = profitOrLossPercent;
	}
    
    
    


    
}