package com.tblmonitoring.tblmonitor.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

//import java.io.IOException;

//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tblmonitoring.tblmonitor.dto.VandalismReportDTO;
import com.tblmonitoring.tblmonitor.dto.VandalismReportwithUserDTO;
import com.tblmonitoring.tblmonitor.entity.VandalismReport;
import com.tblmonitoring.tblmonitor.service.VandalismService;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/vandalism")
public class VandalismController {

	private final VandalismService reportService;

    // ✅ Constructor injection to initialize final field
    public VandalismController(VandalismService reportService) {
        this.reportService = reportService;
    }

//	@PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ResponseEntity<?> submitVandalismReport(
//	    @RequestParam("inspectionId") Long inspectionId,
//	    @RequestParam("modelNo") String modelNo,
//	    @RequestParam("componentName") String componentName,
//	    @RequestParam("issueDescription") String issueDescription,
//	    @RequestParam("reportedByUserId") Long reportedByUserId,
//	    @RequestParam(value = "photo", required = false) MultipartFile photo
//	) throws IOException {
//
//	    VandalismReportDTO dto = new VandalismReportDTO(
//	        inspectionId,
//	        modelNo,
//	        componentName,
//	        issueDescription,
//	        photo,
//	        reportedByUserId
//	    );
//
//	    VandalismReport report = reportService.saveReport(dto);
//	    return ResponseEntity.ok(report);
//	}

	@PostMapping("/submit-direct")
    public ResponseEntity<VandalismReport> submitVandalismReportJson(@RequestBody VandalismReportDTO dto) {
        VandalismReport report = reportService.saveReport(dto);
        return ResponseEntity.ok(report);
    }

	
	@GetMapping("/all")
	public ResponseEntity<List<VandalismReport>> getAllReports() {
	    return ResponseEntity.ok(reportService.getAllReports());
	}
    
	@PostMapping("/submit-multiple")
	public ResponseEntity<?> submitMultipleVandalismReports(@RequestBody List<VandalismReportDTO> reportDTOs) {
	    try {
	        List<VandalismReport> savedReports = reportService.saveMultipleReports(reportDTOs);
	        return ResponseEntity.ok(savedReports);
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Error saving multiple vandalism reports: " + e.getMessage());
	    }
	}
	
	
	@GetMapping("/all-with-names")
	public ResponseEntity<List<VandalismReportwithUserDTO>> getAllReportsWithNames() {
	    return ResponseEntity.ok(reportService.getAllReportsWithUserNames());
	}



}
