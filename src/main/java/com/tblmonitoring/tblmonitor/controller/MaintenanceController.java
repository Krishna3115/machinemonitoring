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
import org.springframework.web.bind.annotation.RestController;

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
	 
	 	@PostMapping("/submit")
	    public ResponseEntity<String> submitMaintenance(@RequestBody MaintenanceFormDTO form) {
	        try {
	            String result = maintenanceService.submitMaintenanceForm(form);
	            return ResponseEntity.ok(result);
	        } catch (Exception e) {
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


	 }
