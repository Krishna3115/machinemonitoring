package com.tblmonitoring.tblmonitor.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tblmonitoring.tblmonitor.dto.ClaimPartDTO;
import com.tblmonitoring.tblmonitor.dto.ComplaintRequest;
import com.tblmonitoring.tblmonitor.dto.InsuranceClaimDTO;
import com.tblmonitoring.tblmonitor.entity.InsuranceClaimEntity;
import com.tblmonitoring.tblmonitor.service.FileStorageService;
import com.tblmonitoring.tblmonitor.service.InsuranceClaimService;

@RestController
@RequestMapping("/api/insurance-claims")
public class InsuranceClaimController {

	 @Autowired
	    private InsuranceClaimService claimService;

	 
	    @Autowired
	    private FileStorageService fileStorageService;
	 
	 
//	    @PostMapping("/{id}/start")
//	    public ResponseEntity<?> startClaim(@PathVariable("id") Long reportId) {
//	        return ResponseEntity.ok(claimService.startClaim(reportId));
//	    }
	    
	    

	    @PostMapping("/{reportId}/upload-joint-report")
	    public ResponseEntity<?> uploadJointReport(
	        @PathVariable("reportId") Long reportId,
	        @RequestParam("file") MultipartFile file,
	        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
	    ) {
	        String storedFilePath = fileStorageService.storeFile(file);

	        claimService.uploadJointReport(reportId, storedFilePath, date);
	        return ResponseEntity.ok("Joint report uploaded successfully.");
	    }

	    @PostMapping("/{reportId}/submit")
	    public ResponseEntity<?> submitClaim(@PathVariable("reportId") Long reportId) {
	        return ResponseEntity.ok(claimService.markSubmittedToInsurance(reportId));
	    }

	    @PostMapping("/{reportId}/server-visit")
	    public ResponseEntity<?> markServerVisit(@PathVariable("reportId") Long reportId) {
	        return ResponseEntity.ok(claimService.markServerVisitDone(reportId));
	    }

	    @PostMapping("/{reportId}/result")
	    public ResponseEntity<?> updateResult(@PathVariable("reportId") Long reportId,
	                                          @RequestParam("passed") boolean passed,
	                                          @RequestParam(value = "remark", required = false) String remark) {
	        return ResponseEntity.ok(claimService.updateClaimResult(reportId, passed, remark));
	    }

	    @PostMapping("/{reportId}/close")
	    public ResponseEntity<?> closeClaim(@PathVariable("reportId") Long reportId) {
	        return ResponseEntity.ok(claimService.closeClaim(reportId));
	    }

	    @GetMapping("/{reportId}")
	    public ResponseEntity<?> getClaim(@PathVariable("reportId") Long reportId) {
	        InsuranceClaimEntity claim = claimService.getClaimByReportId(reportId);
	        return ResponseEntity.ok(claimService.convertToDto(claim));
	    }



	    
	    
	    @PostMapping("/{claimId}/parts")
	    public ResponseEntity<?> saveClaimParts(
	            @PathVariable("claimId") Long claimId,
	            @RequestBody List<ClaimPartDTO> parts) {
	    	claimService.saveClaimParts(claimId, parts);
	        return ResponseEntity.ok("Claim parts saved successfully.");
	    }

	    
	    @GetMapping("/{claimId}/parts")
	    public ResponseEntity<List<ClaimPartDTO>> getPartsByClaimId(@PathVariable("claimId") Long claimId) {
	        List<ClaimPartDTO> parts = claimService.getClaimParts(claimId);
	        return ResponseEntity.ok(parts);
	    }

	    @GetMapping("/{claimId}/summary")
	    public ResponseEntity<?> getClaimSummary(@PathVariable("claimId") Long claimId) {
	        return ResponseEntity.ok(claimService.getClaimSummary(claimId));
	    }
	    
	    
	    @GetMapping("/all")
	    public ResponseEntity<?> getAllClaims(
	            @RequestParam(value = "status", required = false) String status) {
	        return ResponseEntity.ok(claimService.getAllClaims(status));
	    }
  
	    @PostMapping("/{reportId}/start")
	    public ResponseEntity<?> startClaim(
	        @PathVariable("reportId") Long reportId,
	        @RequestBody ComplaintRequest request
	    ) {
	        InsuranceClaimEntity claim = claimService.startClaim(
	            reportId,
	            request.getComplaintNo(),
	            request.getComplaintDate(),
	            request.getMachineSerial()
	        );

	        return ResponseEntity.ok(claimService.convertToDto(claim));
	    }


}
