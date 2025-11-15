package com.tblmonitoring.tblmonitor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tblmonitoring.tblmonitor.dto.GreaseCalculationResult;
import com.tblmonitoring.tblmonitor.service.GreaseCalculationService;

@RestController
@RequestMapping("/api/grease")
public class GreaseCalculationController {

	 private final GreaseCalculationService greaseCalculationService;

	    public GreaseCalculationController(GreaseCalculationService greaseCalculationService) {
	        this.greaseCalculationService = greaseCalculationService;
	    }

	    @GetMapping("/calculate")
	    public ResponseEntity<GreaseCalculationResult> calculateGrease(
	            @RequestParam String modelNo,
	            @RequestParam double greaseLeftKg) {
	        return ResponseEntity.ok(greaseCalculationService.calculateGreaseEstimate(modelNo, greaseLeftKg));
	    }
	    
	    @GetMapping("/expiring-soon")
	    public ResponseEntity<List<GreaseCalculationResult>> getMachinesWithLowGrease() {
	        List<GreaseCalculationResult> results = greaseCalculationService.getMachinesWithLowGrease();
	        return ResponseEntity.ok(results);
	    }

	    
	    @GetMapping("/low-level")
	    public ResponseEntity<List<GreaseCalculationResult>> getLowGreaseMachines() {
	        List<GreaseCalculationResult> results = greaseCalculationService.getMachinesWithLowGrease();
	        return ResponseEntity.ok(results);
	    }
}
