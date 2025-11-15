package com.tblmonitoring.tblmonitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tblmonitoring.tblmonitor.dto.InstallationFormDTO;
import com.tblmonitoring.tblmonitor.dto.InstallationProgressDTO;
//import com.tblmonitoring.tblmonitor.dto.InstallationStartRequest;
import com.tblmonitoring.tblmonitor.service.InstallationService;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/installations")
public class InstallationController {

	 	@Autowired
	    private InstallationService installationService;

	 @PostMapping("/start")
	 public ResponseEntity<String> startInstallation
	 (@RequestParam("modelNo") String modelNo, 
	  @RequestParam("technicianId") Long technicianId)
	 {
	     try {
	         installationService.startInstallation(modelNo, technicianId);
	         return ResponseEntity.ok("Installation started successfully.");
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " +e.getMessage());
	     }
	 }


	    @PostMapping("/installation/complete")
	    public ResponseEntity<String> completeInstallation(@RequestBody InstallationFormDTO request) {
	    	System.out.println("Received: " + request);
	        try {
	            String result = installationService.completeInstallation(request);
	            return ResponseEntity.ok(result);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        }
	    }
	    
	    @GetMapping("/installing-by-technician")
	    public ResponseEntity<List<InstallationProgressDTO>> getInstallingMachinesForTechnician( @RequestParam("technicianId") Long technicianId) {
	         // Assuming user ID is stored as principal name
	        List<InstallationProgressDTO> installations = installationService.getInstallationInProgressByTechnician(technicianId);
	        return ResponseEntity.ok(installations);
	    }



}

