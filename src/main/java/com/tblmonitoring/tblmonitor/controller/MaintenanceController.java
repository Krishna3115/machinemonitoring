package com.tblmonitoring.tblmonitor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tblmonitoring.tblmonitor.dto.MaintenanceFormDTO;
import com.tblmonitoring.tblmonitor.service.MachineInspectionService;
import com.tblmonitoring.tblmonitor.service.MachineService;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

	
	 	@Autowired
	    private MachineService maintenanceService;
	 	
	 	@Autowired
	 	private MachineInspectionService inspectionService;
	 	
	 	@Autowired
	 	private ObjectMapper objectMapper;
	 	
	 
	 	 @PostMapping("/submit")
	     public ResponseEntity<String> submitMaintenance(
	             @RequestParam("form") String formJson,
	             @RequestParam(value = "greaseLevelPhoto", required = false) MultipartFile greaseLevelPhoto,
	             @RequestParam(value = "machineInfoPlatePhoto", required = false) MultipartFile machineInfoPlatePhoto,
	             @RequestParam(value = "applicatorPhoto", required = false) MultipartFile applicatorPhoto
	     ) {
	         try {
	             // Ensure ObjectMapper can handle LocalDate/Time
	             objectMapper.registerModule(new JavaTimeModule());
	             objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	             // Convert JSON string to DTO
	             MaintenanceFormDTO form = objectMapper.readValue(formJson, MaintenanceFormDTO.class);

	             // Save images if they exist
	             if (greaseLevelPhoto != null && !greaseLevelPhoto.isEmpty()) {
	                 String greasePhotoUrl = maintenanceService.saveImage(greaseLevelPhoto);
	                 form.setGreaseLevelPhotoUrl(greasePhotoUrl);
	             }
	             if (machineInfoPlatePhoto != null && !machineInfoPlatePhoto.isEmpty()) {
	                 String platePhotoUrl = maintenanceService.saveImage(machineInfoPlatePhoto);
	                 form.setMachineInfoPlatePhotoUrl(platePhotoUrl);
	             }
	             if (applicatorPhoto != null && !applicatorPhoto.isEmpty()) {
	                 String applicatorPhotoUrl = maintenanceService.saveImage(applicatorPhoto);
	                 form.setApplicatorPhotoUrl(applicatorPhotoUrl);
	             }

	             // Validate essential fields
	             if (form.getModelNo() == null || form.getInspectedByUserId() == null || form.getId() == null) {
	                 return ResponseEntity.badRequest().body("Missing essential fields (modelNo, inspectedByUserId, id)");
	             }

	             // Submit form
	             String result = maintenanceService.submitMaintenanceForm(form);
	             return ResponseEntity.ok(result);

	         } catch (Exception e) {
	             e.printStackTrace();
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
	         }
	     }

	 	
	 	@PostMapping("/list")
	 	public ResponseEntity<List<MaintenanceFormDTO>> getMaintenanceReport(@RequestBody Map<String, String> filters) {
	 	    try {
	 	        String modelNo = filters.get("modelNo");
	 	        String fromDateStr = filters.get("fromDate");
	 	        String toDateStr = filters.get("toDate");

	 	        List<MaintenanceFormDTO> result = inspectionService.getFilteredMaintenanceRecords(modelNo, fromDateStr, toDateStr);
	 	        return ResponseEntity.ok(result);
	 	    } catch (Exception e) {
	 	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 	                .body(new ArrayList<>());
	 	    }
	 	}
	 	
	 	@PostMapping("/monthly-summary")
	 	public ResponseEntity<List<Map<String, Object>>> getMonthlySummary(@RequestBody Map<String, String> filters) {
	 	    try {
	 	        String modelNo = filters.get("modelNo");
	 	        List<Map<String, Object>> result = inspectionService                                                                                     .getMonthlyVisitCounts(modelNo);
	 	        return ResponseEntity.ok(result);
	 	    } catch (Exception e) {
	 	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
	 	    }
	 	}
	 	
	 


	     // ------------------------------------------------------------
	     // 4. COMPLETE MAINTENANCE (Final Form Submit)
	     // ------------------------------------------------------------
	     @PostMapping("/complete-maintenance")
	     public ResponseEntity<String> completeMaintenance(@RequestBody MaintenanceFormDTO dto) {
	         try {
	             String result = inspectionService.completeMaintenance(dto);
	             return ResponseEntity.ok(result);
	         } catch (Exception e) {
	             return ResponseEntity.badRequest().body("Error: " + e.getMessage());
	         }
	     }


	 }
