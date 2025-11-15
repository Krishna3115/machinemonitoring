package com.tblmonitoring.tblmonitor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "claim_parts")
public class ClaimPartEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partName;

    private Double actualValue;

    private Double claimedAmount;

    @ManyToOne
    @JoinColumn(name = "insurance_claim_id")
    private InsuranceClaimEntity insuranceClaim;
    
    
    public ClaimPartEntity() {
		// TODO Auto-generated constructor stub
	
    }


	public ClaimPartEntity(Long id, String partName, Double actualValue, Double claimedAmount,
			InsuranceClaimEntity insuranceClaim) {
		super();
		this.id = id;
		this.partName = partName;
		this.actualValue = actualValue;
		this.claimedAmount = claimedAmount;
		this.insuranceClaim = insuranceClaim;
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


	public InsuranceClaimEntity getInsuranceClaim() {
		return insuranceClaim;
	}


	public void setInsuranceClaim(InsuranceClaimEntity insuranceClaim) {
		this.insuranceClaim = insuranceClaim;
	}
    
    
	
}
