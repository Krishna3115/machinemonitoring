package com.tblmonitoring.tblmonitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tblmonitoring.tblmonitor.dto.AvailableLOADTO;
import com.tblmonitoring.tblmonitor.dto.LoaDetailDTO;
import com.tblmonitoring.tblmonitor.dto.ProductionPlanningDTO;
import com.tblmonitoring.tblmonitor.repository.ProductionPlanningRepository;
import com.tblmonitoring.tblmonitor.service.ProductionPlanningService;

@RestController
@RequestMapping("/api/production-planning")
//@CrossOrigin(origins = "*") 
public class ProductionPlanningController {

	
	 @Autowired
	    private ProductionPlanningService planningService;
	 
	 @Autowired
	 private ProductionPlanningRepository productionPlanRepo;

	    // Endpoint to create a new production plan
	 @PostMapping
	    public ResponseEntity<String> createPlan(@RequestBody ProductionPlanningDTO dto) {
	        try {
	            planningService.createPlan(dto);
	            return ResponseEntity.ok("Production plan created successfully.");
	        } catch (IllegalArgumentException ex) {
	            return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
	        } catch (Exception ex) {
	            return ResponseEntity.internalServerError().body("Unexpected error: " + ex.getMessage());
	        }
	    }

	    @GetMapping("/available-loas")
	    public ResponseEntity<List<AvailableLOADTO>> getAvailableLOAs() {
	        return ResponseEntity.ok(planningService.getAvailableLOAs());
	    }
	    

	    
	    @GetMapping("/loa-details")
	    public ResponseEntity<List<LoaDetailDTO>> getLoaDetails() {
	      return ResponseEntity.ok(productionPlanRepo.findAllLoaDetails());
	    }
}
