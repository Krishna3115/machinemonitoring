package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tblmonitoring.tblmonitor.entity.InsuranceClaimEntity;
import com.tblmonitoring.tblmonitor.entity.Machine;

public class InsuranceClaimDTO {

	private Long id;
	private Long vandalismReportId;
    private String status;
    private String jointReportPdf;
    private LocalDate jointReportDate;
    private LocalDateTime claimedToInsuranceAt;
    private LocalDateTime serverVisitedAt;
    private LocalDateTime closedAt;
    private Boolean claimPassed;
    private String remark;
    private String complaintNo;
    private LocalDate complaintDate;
    private String machineSerial;
    private String division;
    private String section;

    
    public InsuranceClaimDTO() {
		// TODO Auto-generated constructor stub
	}


	public InsuranceClaimDTO(Long vandalismReportId, String status, String jointReportPdf, LocalDate jointReportDate,
			Boolean claimPassed, String remark) {
		super();
		this.vandalismReportId = vandalismReportId;
		this.status = status;
		this.jointReportPdf = jointReportPdf;
		this.jointReportDate = jointReportDate;
		this.claimPassed = claimPassed;
		this.remark = remark;
	}

	
	public static InsuranceClaimDTO fromEntity(InsuranceClaimEntity claim, Machine machine) {
        InsuranceClaimDTO dto = new InsuranceClaimDTO();
        dto.setId(claim.getId());
        dto.setVandalismReportId(claim.getVandalismReport().getId());
        dto.setStatus(claim.getStatus());
        dto.setJointReportPdf(claim.getJointReportPdf());
        dto.setJointReportDate(claim.getJointReportDate());
        dto.setClaimedToInsuranceAt(claim.getClaimedToInsuranceAt());
        dto.setServerVisitedAt(claim.getServerVisitedAt());
        dto.setClosedAt(claim.getClosedAt());
        dto.setClaimPassed(claim.getClaimPassed());
        dto.setRemark(claim.getRemark());
        dto.setComplaintNo(claim.getComplaintNo());
        dto.setComplaintDate(claim.getComplaintDate());
        dto.setMachineSerial(claim.getMachineSerial());

        if (machine != null) {
            dto.setDivision(machine.getDivision());
            dto.setSection(machine.getSection());
        } else {
            dto.setDivision("N/A");
            dto.setSection("N/A");
        }
        return dto;
    }


	public Long getVandalismReportId() {
		return vandalismReportId;
	}


	public void setVandalismReportId(Long vandalismReportId) {
		this.vandalismReportId = vandalismReportId;
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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public LocalDateTime getClosedAt() {
		return closedAt;
	}


	public void setClosedAt(LocalDateTime closedAt) {
		this.closedAt = closedAt;
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


	public String getDivision() {
		return division;
	}


	public void setDivision(String division) {
		this.division = division;
	}


	public String getSection() {
		return section;
	}


	public void setSection(String section) {
		this.section = section;
	}
    
	
}
