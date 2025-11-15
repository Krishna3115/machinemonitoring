package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "insurance_claims")
public class InsuranceClaimEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String complaintNo;
	private LocalDate complaintDate;
	private String machineSerial;

	
    @OneToOne
    @JoinColumn(name = "vandalism_report_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private VandalismReport vandalismReport;

    private String status; // ENUM: "started", "joint_report_uploaded", etc.

    private String jointReportPdf;

    private LocalDate jointReportDate;

    private LocalDateTime claimedToInsuranceAt;

    private LocalDateTime serverVisitedAt;

    private Boolean claimPassed;

    private String remark;

    private LocalDateTime closedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "insuranceClaim", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClaimPartEntity> parts;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    
    public InsuranceClaimEntity() {
		// TODO Auto-generated constructor stub
	}


	
	public InsuranceClaimEntity(Long id, String complaintNo, LocalDate complaintDate, String machineSerial,
			VandalismReport vandalismReport, String status, String jointReportPdf, LocalDate jointReportDate,
			LocalDateTime claimedToInsuranceAt, LocalDateTime serverVisitedAt, Boolean claimPassed, String remark,
			LocalDateTime closedAt, LocalDateTime createdAt, LocalDateTime updatedAt, List<ClaimPartEntity> parts) {
		super();
		this.id = id;
		this.complaintNo = complaintNo;
		this.complaintDate = complaintDate;
		this.machineSerial = machineSerial;
		this.vandalismReport = vandalismReport;
		this.status = status;
		this.jointReportPdf = jointReportPdf;
		this.jointReportDate = jointReportDate;
		this.claimedToInsuranceAt = claimedToInsuranceAt;
		this.serverVisitedAt = serverVisitedAt;
		this.claimPassed = claimPassed;
		this.remark = remark;
		this.closedAt = closedAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.parts = parts;
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VandalismReport getVandalismReport() {
		return vandalismReport;
	}

	public void setVandalismReport(VandalismReport vandalismReport) {
		this.vandalismReport = vandalismReport;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJointReportPdf() {
		return jointReportPdf;
	}

	public void setJointReportPdf(String jointReportPdf) {
		this.jointReportPdf = jointReportPdf;
	}

	public LocalDate getJointReportDate() {
		return jointReportDate;
	}

	public void setJointReportDate(LocalDate jointReportDate) {
		this.jointReportDate = jointReportDate;
	}

	public LocalDateTime getClaimedToInsuranceAt() {
		return claimedToInsuranceAt;
	}

	public void setClaimedToInsuranceAt(LocalDateTime claimedToInsuranceAt) {
		this.claimedToInsuranceAt = claimedToInsuranceAt;
	}

	public LocalDateTime getServerVisitedAt() {
		return serverVisitedAt;
	}

	public void setServerVisitedAt(LocalDateTime serverVisitedAt) {
		this.serverVisitedAt = serverVisitedAt;
	}

	public Boolean getClaimPassed() {
		return claimPassed;
	}

	public void setClaimPassed(Boolean claimPassed) {
		this.claimPassed = claimPassed;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LocalDateTime getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(LocalDateTime closedAt) {
		this.closedAt = closedAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<ClaimPartEntity> getParts() {
		return parts;
	}

	public void setParts(List<ClaimPartEntity> parts) {
		this.parts = parts;
	}

	public String getComplaintNo() {
		return complaintNo;
	}

	public void setComplaintNo(String complaintNo) {
		this.complaintNo = complaintNo;
	}

	public LocalDate getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(LocalDate complaintDate) {
		this.complaintDate = complaintDate;
	}

	public String getMachineSerial() {
		return machineSerial;
	}

	public void setMachineSerial(String machineSerial) {
		this.machineSerial = machineSerial;
	}
    

}
