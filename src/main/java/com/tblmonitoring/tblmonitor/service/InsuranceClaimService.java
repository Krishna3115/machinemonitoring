package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDate;
import java.util.List;

import com.tblmonitoring.tblmonitor.dto.ClaimPartDTO;
import com.tblmonitoring.tblmonitor.dto.InsuranceClaimDTO;
import com.tblmonitoring.tblmonitor.entity.InsuranceClaimEntity;

public interface InsuranceClaimService {

	 // 	InsuranceClaimEntity startClaim(Long vandalismReportId);
	    InsuranceClaimEntity uploadJointReport(Long reportId, String pdfUrl, LocalDate date);
	    InsuranceClaimEntity markSubmittedToInsurance(Long reportId);
	    InsuranceClaimEntity markServerVisitDone(Long reportId);
	    InsuranceClaimEntity updateClaimResult(Long reportId, boolean passed, String remark);
	    InsuranceClaimEntity closeClaim(Long reportId);
	    InsuranceClaimEntity getClaimByReportId(Long reportId);
	    
	    void saveClaimParts(Long claimId, List<ClaimPartDTO> parts);
	    List<ClaimPartDTO> getPartsByClaimId(Long claimId);
	    
	    List<ClaimPartDTO> getClaimParts(Long claimId);
		Object getClaimSummary(Long claimId);
		Object getAllClaims(String status);
		InsuranceClaimEntity startClaim(Long reportId, String complaintNo, LocalDate complaintDate,
				String machineSerial);

		 InsuranceClaimDTO convertToDto(InsuranceClaimEntity claim);
		
}
